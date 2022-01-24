package com.accenture.assignment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.accenture.assignment.dto.JwtRequestDTO;
import com.accenture.assignment.dto.JwtResponseDTO;
import com.accenture.assignment.impl.JwtTokenUtil;
import com.accenture.assignment.model.User;
import com.accenture.assignment.repository.UserRepository;

@RestController
@CrossOrigin
@RequestMapping("/authentication")
public class JwtAuthenticationController {
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	@Autowired
	private UserRepository userRepository;

	@Autowired
	private UserDetailsService userDetailsService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping(value = "/authenticate")
	public ResponseEntity<?> createAuthenticationToken(@RequestBody JwtRequestDTO authenticationRequest)
			throws Exception {
		User user = userRepository.findByName(authenticationRequest.getUsername());
		if (passwordEncoder.matches(authenticationRequest.getPassword(), user.getPassword())) {
			throw new Exception();
		}
		final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());
		final String token = jwtTokenUtil.generateToken(userDetails);

		return ResponseEntity.ok(new JwtResponseDTO(token));
	}

}
