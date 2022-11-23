package emall.usc.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import emall.usc.beans.User;
import emall.usc.beans.UserProfile;
import emall.usc.dao.UserDao;
import emall.usc.http.Response;
import emall.usc.jwt.JwtGeneratorInterface;

@Service
@Transactional
public class UserService {
	@Autowired
	UserDao userDao;
	
	@Autowired
	PasswordEncoder passwordEncoder;

	private JwtGeneratorInterface jwtGenerator;
	
	public List<User> getusers(){
		return userDao.findAll();
	}

	public User getUserBynameAndpassword(String name, String password) throws Exception {
		User user = userDao.findByUsername(name);
		if(user == null){
			throw new Exception("Invalid id and password");
		}
		if(password.equals(user.getPassword())){
			return user;
		}else{
			throw new Exception("Invalid password");
		}
	}
	
	public Response register(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		List<UserProfile> profiles = new ArrayList<UserProfile>();
		profiles.add(new UserProfile(2));
		user.setProfiles(profiles);
		System.out.println(user);
		userDao.save(user);
		return new Response(true);
	}
	public Response registerAdm(User user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		List<UserProfile> profiles = new ArrayList<UserProfile>();
		profiles.add(new UserProfile(1));
		user.setProfiles(profiles);
		System.out.println(user);
		userDao.save(user);
		return new Response(true);
	}
	
	public Response changePassword(User user, Authentication authentication) {
		if(user.getUsername().equals(authentication.getName()) || isAdmin(authentication.getAuthorities())){
			User u = userDao.findByUsername(user.getUsername());
			u.setPassword(passwordEncoder.encode(user.getPassword()));
			userDao.save(u);
		}else{
			return new Response(false);
		}
		return new Response(true);
	}
	//get the token
	public ResponseEntity<?> gettoken(Authentication authentication){
		User u = userDao.findByUsername(authentication.getName());
		return new ResponseEntity<>(jwtGenerator.generateToken(u), HttpStatus.OK);
	}
	
	public Response beSeller(User user, Authentication authentication) {
		if(user.getUsername().equals(authentication.getName())){
			User u = userDao.findByUsername(user.getUsername());
			u.getProfiles().add(new UserProfile(3));
			userDao.save(u);
		}else{
			return new Response(false);
		}
		return new Response(true);
	}
	
	
	public boolean isAdmin(Collection<? extends GrantedAuthority> profiles) {
		boolean isAdmin = false;
		for(GrantedAuthority profile : profiles) {
			if(profile.getAuthority().equals("ROLE_ADMIN")) {
				isAdmin = true;
			}
		}
		return isAdmin;
	}
	
	public Response deleteUser(int id) {
		if(userDao.findById(id)!=null) {
			userDao.deleteById(id);
			return new Response(true);
		}else {
			return new Response(false,"user is not found");
		}
	}

}

