package com.thecodereveal.app.controllers;

import java.security.NoSuchAlgorithmException;
import java.security.Principal;
import java.security.spec.InvalidKeySpecException;

import org.apache.catalina.startup.UserConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;

import com.thecodereveal.app.config.JWTTokenHelper;
import com.thecodereveal.app.entities.User;
import com.thecodereveal.app.requests.AuthenticationRequest;
import com.thecodereveal.app.response.LoginResponse;
import com.thecodereveal.app.response.UserInfo;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin
public class AuthenticationController {
	
	@Autowired
	AuthenticationManager authenticationManager;
	
	@Autowired
	JWTTokenHelper jWTTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@PostMapping("/auth/login")
	public ResponseEntity<?> login(@RequestBody AuthenticationRequest authenticationRequest) throws InvalidKeySpecException, NoSuchAlgorithmException{
		
		final Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
				authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		User user  = (User)authentication.getPrincipal();
		
		String jwtToken = jWTTokenHelper.generateToken(user.getUsername());
		
		
		LoginResponse response = new LoginResponse();
		response.setToken(jwtToken);
		
		return ResponseEntity.ok(response);
	}
	
	@GetMapping("/auth/userinfo")
	public ResponseEntity<?> getUserInfo(Principal user){
		User userObj = (User) userDetailsService.loadUserByUsername(user.getName());
		UserInfo userInfo = new UserInfo();
		
		userInfo.setFirstName(userObj.getFirstName());
		userInfo.setLastName(userObj.getLastName());
		userInfo.setRoles(userObj.getAuthorities().toArray());
	
		
		return ResponseEntity.ok(userInfo);
	}
		
}
