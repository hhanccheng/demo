package emall.usc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import emall.usc.beans.User;
import emall.usc.http.Response;
import emall.usc.jwt.JwtGeneratorInterface;
import emall.usc.service.UserService;

@RestController()
@RequestMapping("/users")
public class UserController {
	@Autowired
	UserService userService;
	private JwtGeneratorInterface jwtGenerator;
	
	@PreAuthorize("hasAuthority('ROLE_ADMIN')")
	@GetMapping
	public List<User> getusers(){
		return userService.getusers();
	}
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_SELLER)")
	@GetMapping("/token")
	public ResponseEntity<?> gettoken(Authentication authentication){
		return userService.gettoken(authentication);
	}


	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) {
		try {
			if(user.getUsername() == null || user.getPassword() == null) {
				throw new Exception("UserName or Password is Empty");
			}
			User userData = userService.getUserByNameAndPassword(user.getUsername(), user.getPassword());
			if(userData == null){
				throw new Exception("UserName or Password is Invalid");
			}
			return new ResponseEntity<>(jwtGenerator.generateToken(user), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@PostMapping
	public Response addUser(@RequestBody User user) {
		return userService.register(user);
	}
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_SELLER)")
	@PutMapping
	public Response changeUser(@RequestBody User user,Authentication authentication) {
		return userService.changePassword(user, authentication);
	}
	
	@PreAuthorize("hasAnyAuthority('ROLE_ADMIN','ROLE_USER','ROLE_SELLER')")
	@PutMapping("/authorization")
	public Response beSeller(@RequestBody User user,Authentication authentication) {
		return userService.beSeller(user, authentication);
	}
	
	@DeleteMapping("/{id}")
	public Response deleteUser(@PathVariable int id) {
		return userService.deleteUser(id);
	}
	
	

}

