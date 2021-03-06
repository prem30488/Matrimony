package com.matrimony.demo.controller;

import java.net.URI;
import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;

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

import com.matrimony.demo.exception.AppException;
import com.matrimony.demo.exception.BadRequestException;
import com.matrimony.demo.exception.ResourceNotFoundException;
import com.matrimony.demo.model.AstroProfile;
import com.matrimony.demo.model.AuthProvider;
import com.matrimony.demo.model.CareerProfile;
import com.matrimony.demo.model.FamilyProfile;
import com.matrimony.demo.model.PartnerPreference;
import com.matrimony.demo.model.Profile;
import com.matrimony.demo.model.Role;
import com.matrimony.demo.model.RoleName;
import com.matrimony.demo.model.User;
import com.matrimony.demo.payload.ApiResponse;
import com.matrimony.demo.payload.SignUpRequest;
import com.matrimony.demo.payload.UserIdentityAvailability;
import com.matrimony.demo.payload.UserProfile;
import com.matrimony.demo.repository.RoleRepository;
import com.matrimony.demo.repository.UserRepository;
import com.matrimony.demo.security.CurrentUser;
import com.matrimony.demo.security.UserPrincipal;
import static java.time.temporal.TemporalAdjusters.previousOrSame;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class UserController {

	@Autowired
	private PasswordEncoder passwordEncoder;

	private static final Logger logger = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private RoleRepository roleRepository;

	@GetMapping("/user/me")
	@PreAuthorize("hasRole('USER')")
	public User getCurrentUser(@CurrentUser UserPrincipal userPrincipal) {
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
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException("Email address already in use.");
		}
		if (userRepository.existsByUsername(signUpRequest.getUsername())) {
			throw new BadRequestException("Username already in use.");
		}
		if (userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
			throw new BadRequestException("Phone number already in use.");
		}
		// Creating user's account
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setUsername(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
				.orElseThrow(() -> new AppException("User Role not set."));
		user.setRoles(Collections.singleton(userRole));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setSex(signUpRequest.getSex());
		user.setPhoneNumber(signUpRequest.getPhoneNumber());
		
		Profile profile = new Profile();
		user.setProfile(profile);
		profile.setUser(user);
		
		AstroProfile astroProfile = new AstroProfile();
		user.setAstroProfile(astroProfile);
		astroProfile.setUser(user);
		
		CareerProfile careerProfile = new CareerProfile();
		user.setCareerProfile(careerProfile);
		careerProfile.setUser(user);
		
		FamilyProfile familyProfile = new FamilyProfile();
		user.setFamilyProfile(familyProfile);
		familyProfile.setUser(user);
		
		PartnerPreference partnerPreference = new PartnerPreference();
		user.setPartnerPreference(partnerPreference);
		partnerPreference.setUser(user);
		
		User result = userRepository.save(user);

		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully"));
	}

	@PutMapping("/user/users/{userId}")
	public User updateUser(@PathVariable Long userId, @Valid @RequestBody User userRequest) {
		return userRepository.findById(userId).map(user -> {
			if (userRepository.existsUserLikeCustomQueryEmail(userRequest.getEmail())) {
				throw new BadRequestException("Email address already in use.");
			}
			if (userRepository.existsUserLikeCustomQueryUsername(userRequest.getUsername())) {
				throw new BadRequestException("Username already in use.");
			}
			if (userRepository.existsUserLikeCustomQueryPhoneNumber(userRequest.getPhoneNumber())) {
				throw new BadRequestException("Phone number already in use.");
			}
			user.setName(userRequest.getName());
			user.setUsername(userRequest.getUsername());
			user.setEmail(userRequest.getEmail());
			user.setImageUrl(userRequest.getImageUrl());
			user.setCreatedAt(userRequest.getCreatedAt());
			user.setUpdatedAt(userRequest.getUpdatedAt());
			user.setSex(userRequest.getSex());
			user.setPhoneNumber(userRequest.getPhoneNumber());
			
			return userRepository.save(user);
		}).orElseThrow(() -> new ResourceNotFoundException("User not found with ", "id", userId));
	}

	@DeleteMapping("/user/users/{userId}")
	public ResponseEntity<?> deleteOverview(@PathVariable Long userId) {
		return userRepository.findById(userId).map(user -> {
			userRepository.delete(user);
			return ResponseEntity.ok().build();
		}).orElseThrow(() -> new ResourceNotFoundException("User not found with ", "id", userId));
	}

	@GetMapping("/user/users/{userId}")
	public User fetchUserById(@PathVariable Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User not found with ", "id", userId));
	}
	
	@GetMapping("/user/users/weekly")
	public Integer fetchUserCountWeekly() {
	    Integer count = userRepository.weeklyCount().get(0);
		return count>0?count:0;
	}
	
	@GetMapping("/user/users/monthly")
	public Integer fetchUserCountMonthly() {
	    Integer count = userRepository.monthlyCount().get(0);
		return count>0?count:0;
	}
	
	@GetMapping("/user/users/countwithImage")
	public Integer fetchUserwithImage() {
	    Integer count = userRepository.getCountWithImage().get(0);
		return count>0?count:0;
	}

}
