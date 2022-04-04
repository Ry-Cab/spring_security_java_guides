package com.thecodereveal.app;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.thecodereveal.app.entities.Authority;
import com.thecodereveal.app.entities.User;
import com.thecodereveal.app.repo.UserDetailsRepository;

@SpringBootApplication
public class SpringSecurityDemoAppApplication {
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private UserDetailsRepository userDetailsRepo;

	public static void main(String[] args) {
		SpringApplication.run(SpringSecurityDemoAppApplication.class, args);
	}
	
	@PostConstruct
	protected void init() {
		List<Authority> authorityList = new ArrayList<>();
		
		authorityList.add(createAuthority("USER", "User role"));
		authorityList.add(createAuthority("ADMIN", "Admin role"));
		
		User user  = new User();
		
		user.setUserName("pardeep161");
		user.setFirstName("Pardeep");
		user.setLastName("K");
		user.setPassword(passwordEncoder.encode("parrdeep@123"));
		user.setEnabled(true);
		user.setAuthorities(authorityList);
		
		userDetailsRepo.save(user);
	}
	
	private Authority createAuthority(String roleCode, String roleDescription) {
		Authority authority = new Authority();
		authority.setRoleCode(roleCode);
		authority.setRoleDescription(roleDescription);
		return authority;
	}
}
