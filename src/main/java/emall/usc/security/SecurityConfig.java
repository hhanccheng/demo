package emall.usc.security;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import emall.usc.handler.AccessDeniedHandlerImpl;
import emall.usc.handler.AuthenticationEntryPointImpl;
import emall.usc.handler.AuthenticationFailureHandlerImpl;
import emall.usc.handler.AuthenticationSuccessHandlerImpl;
import emall.usc.handler.LogoutSuccessHandlerImpl;

@Configuration
public class SecurityConfig{
	@Autowired
	AuthenticationEntryPointImpl authenticationEntryPointImpl;
	
	@Autowired
	AccessDeniedHandlerImpl accessDeniedHandlerImpl;
	
	@Autowired
	AuthenticationSuccessHandlerImpl authenticationSuccessHandlerImpl;
	
	@Autowired
	AuthenticationFailureHandlerImpl authenticationFailureHandlerImpl;
	
	@Autowired
	LogoutSuccessHandlerImpl logoutSuccessHandlerImpl; 
	
	@Autowired
	UserDetailsService userDetailsService;

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		
		authProvider.setUserDetailsService(userDetailsService);
		authProvider.setPasswordEncoder(passwordEncoder());
	
		return authProvider;
	}

	@Bean
  	public AuthenticationManager authenticationManager(AuthenticationConfiguration authConfiguration) throws Exception {
    	return authConfiguration.getAuthenticationManager();
  	}
	
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf().disable().cors()
	        .and().authorizeRequests().antMatchers("/index.html", "/products", "products/*").permitAll().and()
	        .exceptionHandling()
	        .authenticationEntryPoint(authenticationEntryPointImpl).and().exceptionHandling()
	        .accessDeniedHandler(accessDeniedHandlerImpl).and().formLogin().permitAll()
	        .loginProcessingUrl("/login")
	        .successHandler(authenticationSuccessHandlerImpl)
	        .failureHandler(authenticationFailureHandlerImpl)
	        .usernameParameter("username")
	        .passwordParameter("password")
	        .and().logout().permitAll()
	        .logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandlerImpl).and().rememberMe();

		http.authenticationProvider(authenticationProvider());

        return http.build();
    }

	@Bean
	CorsConfigurationSource corsConfiguration() {
		CorsConfiguration configuration = new CorsConfiguration();
		configuration.applyPermitDefaultValues();
		configuration.setAllowedOrigins(Arrays.asList("http://127.0.0.1:3000", "*"));
		configuration.setAllowedMethods(Arrays.asList("GET","POST","PUT","DELETE","HEAD","OPTION"));
		configuration.addAllowedHeader("/*");
		configuration.setAllowCredentials(true);
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		PasswordEncoder encoder = new BCryptPasswordEncoder(11);
		
		return encoder;
	}

}

