package com.matrimony.demo.controller;

import java.net.URI;
import java.time.Instant;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matrimony.demo.exception.BadRequestException;
import com.matrimony.demo.exception.ResourceNotFoundException;
import com.matrimony.demo.model.AuthProvider;
import com.matrimony.demo.model.Role;
import com.matrimony.demo.model.User;
import com.matrimony.demo.payload.ApiResponse;
import com.matrimony.demo.payload.SignUpRequest;
import com.matrimony.demo.payload.UserIdentityAvailability;
import com.matrimony.demo.payload.UserProfile;
import com.matrimony.demo.repository.UserRepository;
import com.matrimony.demo.security.CurrentUser;
import com.matrimony.demo.security.UserPrincipal;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserController {

	@Autowired
    private PasswordEncoder passwordEncoder;
	
	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
		// UserSummary userSummary = new UserSummary(currentUser.getId(),
		// currentUser.getUsername(), currentUser.getName());
		// return userSummary;
		return userRepository.findById(userPrincipal.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userPrincipal.getId()));
	}

	@GetMapping("/user/checkUsernameAvailability")
	public UserIdentityAvailability checkUsernameAvailability(@RequestParam(value = "username") String username) {
		Boolean isAvailable = !userRepository.existsByUsername(username);
		return new UserIdentityAvailability(isAvailable);
	}

	@GetMapping("/user/checkEmailAvailability")
	public UserIdentityAvailability checkEmailAvailability(@RequestParam(value = "email") String email) {
		Boolean isAvailable = !userRepository.existsByEmail(email);
		return new UserIdentityAvailability(isAvailable);
	}

	@GetMapping("/users/{username}")
	public User getUserProfile(@PathVariable(value = "username") String username) {
		User user = userRepository.findByUsername(username)
				.orElseThrow(() -> new ResourceNotFoundException("User", "username", username));
		return user;
	}

	@GetMapping("/user/users")
	public Page<User> getUsers(Pageable pageable) {
		return userRepository.findAll(pageable);
	}

	@PostMapping("/user/addUser")
	public ResponseEntity<?> createUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		 if(userRepository.existsByEmail(signUpRequest.getEmail())) {
	            throw new BadRequestException("Email address already in use.");
	        }
		 if(userRepository.existsByUsername(signUpRequest.getUsername())) {
	            throw new BadRequestException("Username already in use.");
	        }

	        // Creating user's account
	        User user = new User();
	        user.setName(signUpRequest.getName());
	        user.setUsername(signUpRequest.getUsername());
	        user.setEmail(signUpRequest.getEmail());
	        user.setPassword(signUpRequest.getPassword());
	        user.setProvider(AuthProvider.local);

	        user.setPassword(passwordEncoder.encode(user.getPassword()));

	        User result = userRepository.save(user);

	        URI location = ServletUriComponentsBuilder
	                .fromCurrentContextPath().path("/user/me")
	                .buildAndExpand(result.getId()).toUri();

	        return ResponseEntity.created(location)
	                .body(new ApiResponse(true, "User registered successfully@"));
	}

	@PutMapping("/user/users/{userId}")
	public User updateUser(@PathVariable Long userId, @Valid @RequestBody User userRequest) {
		return userRepository.findById(userId).map(user -> {
			user.setName(userRequest.getName());
			user.setUsername(userRequest.getUsername());
			user.setEmail(userRequest.getEmail());
			user.setImageUrl(userRequest.getImageUrl());
			user.setCreatedAt(userRequest.getCreatedAt());
			user.setUpdatedAt(userRequest.getUpdatedAt());
			// user.setRoles(userRequest.getRoles());
			// user.setPassword(user.getPassword());
			return userRepository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("User not found with " ,"id",	 userId));
	}

	@DeleteMapping("/user/users/{userId}")
	public ResponseEntity<?> deleteOverview(@PathVariable Long userId) {
		return userRepository.findById(userId).map(user -> {
			userRepository.delete(user);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("User not found with ","id" , userId));
	}

	@GetMapping("/user/users/{userId}")
	public User fetchUserById(@PathVariable Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ","id", userId));
	}

}
