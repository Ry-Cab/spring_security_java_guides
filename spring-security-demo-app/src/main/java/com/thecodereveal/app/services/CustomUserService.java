package com.thecodereveal.app.services;


import com.thecodereveal.app.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.thecodereveal.app.repo.UserDetailsRepository;

@Service
public class CustomUserService implements UserDetailsService {

	
	@Autowired
	UserDetailsRepository userDetailsRepo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		User user = userDetailsRepo.findByUserName(username);
		
		if(null == user) {
			throw new UsernameNotFoundException("User not found with Username: " + username);
		}
		
		return  user;
	}
	
}
