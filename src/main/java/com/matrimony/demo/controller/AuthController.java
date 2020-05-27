package com.matrimony.demo.controller;

import com.matrimony.demo.exception.AppException;
import com.matrimony.demo.exception.BadRequestException;
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
import com.matrimony.demo.payload.AuthResponse;
import com.matrimony.demo.payload.LoginRequest;
import com.matrimony.demo.payload.SignUpRequest;
import com.matrimony.demo.repository.RoleRepository;
import com.matrimony.demo.repository.UserRepository;
import com.matrimony.demo.security.TokenProvider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collections;

@RestController
@RequestMapping("/auth")
public class AuthController {

	private static final Logger logger = LoggerFactory.getLogger(AuthController.class);
	
	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private TokenProvider tokenProvider;
	
	@Autowired
	private RoleRepository roleRepository;

	@PostMapping("/login")
	public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

		Authentication authentication = authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = tokenProvider.createToken(authentication);
		return ResponseEntity.ok(new AuthResponse(token));
	}

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
		if (userRepository.existsByEmail(signUpRequest.getEmail())) {
			throw new BadRequestException("Email address already in use.");
		}
		if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new BadRequestException("Username already in use.");
        }
		if(userRepository.existsByPhoneNumber(signUpRequest.getPhoneNumber())) {
            throw new BadRequestException("Phone number already in use.");
        }
		// Creating user's account
		User user = new User();
		user.setName(signUpRequest.getName());
		user.setUsername(signUpRequest.getUsername());
		user.setEmail(signUpRequest.getEmail());
		user.setPassword(signUpRequest.getPassword());
		user.setProvider(AuthProvider.local);
		Role userRole = roleRepository.findByName(RoleName.ROLE_USER).orElseThrow(() -> new AppException("User Role not set."));
		user.setRoles(Collections.singleton(userRole));
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		user.setSex(signUpRequest.getSex());
		user.setPhoneNumber(signUpRequest.getPhoneNumber());

		Profile profile = new Profile();
		user.setProfile(profile);
		profile.setCreatedBy("Self");
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

		return ResponseEntity.created(location).body(new ApiResponse(true, "User registered successfully@"));
	}

}
/*
 * import com.matrimony.demo.exception.AppException; import
 * com.matrimony.demo.model.Role; import com.matrimony.demo.model.RoleName;
 * import com.matrimony.demo.model.User; import
 * com.matrimony.demo.payload.ApiResponse; import
 * com.matrimony.demo.payload.JwtAuthenticationResponse; import
 * com.matrimony.demo.payload.LoginRequest; import
 * com.matrimony.demo.payload.SignUpRequest; import
 * com.matrimony.demo.repository.RoleRepository; import
 * com.matrimony.demo.repository.UserRepository; import
 * com.matrimony.demo.security.JwtTokenProvider; import
 * org.springframework.beans.factory.annotation.Autowired; import
 * org.springframework.http.HttpStatus; import
 * org.springframework.http.ResponseEntity; import
 * org.springframework.security.authentication.AuthenticationManager; import
 * org.springframework.security.authentication.
 * UsernamePasswordAuthenticationToken; import
 * org.springframework.security.core.Authentication; import
 * org.springframework.security.core.context.SecurityContextHolder; import
 * org.springframework.security.crypto.password.PasswordEncoder; import
 * org.springframework.web.bind.annotation.PostMapping; import
 * org.springframework.web.bind.annotation.RequestBody; import
 * org.springframework.web.bind.annotation.RequestMapping; import
 * org.springframework.web.bind.annotation.RestController; import
 * org.springframework.web.servlet.support.ServletUriComponentsBuilder;
 * 
 * import javax.validation.Valid; import java.net.URI; import
 * java.util.Collections;
 * 
 * @RestController
 * 
 * @RequestMapping("/api/auth") public class AuthController {
 * 
 * @Autowired AuthenticationManager authenticationManager;
 * 
 * @Autowired UserRepository userRepository;
 * 
 * @Autowired RoleRepository roleRepository;
 * 
 * @Autowired PasswordEncoder passwordEncoder;
 * 
 * @Autowired JwtTokenProvider tokenProvider;
 * 
 * @PostMapping("/signin") public ResponseEntity<?>
 * authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {
 * 
 * Authentication authentication = authenticationManager.authenticate( new
 * UsernamePasswordAuthenticationToken( loginRequest.getUsernameOrEmail(),
 * loginRequest.getPassword() ) );
 * 
 * SecurityContextHolder.getContext().setAuthentication(authentication);
 * 
 * String jwt = tokenProvider.generateToken(authentication); return
 * ResponseEntity.ok(new JwtAuthenticationResponse(jwt)); }
 * 
 * @PostMapping("/signup") public ResponseEntity<?>
 * registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
 * if(userRepository.existsByUsername(signUpRequest.getUsername())) { return new
 * ResponseEntity(new ApiResponse(false, "Username is already taken!"),
 * HttpStatus.BAD_REQUEST); }
 * 
 * if(userRepository.existsByEmail(signUpRequest.getEmail())) { return new
 * ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
 * HttpStatus.BAD_REQUEST); }
 * 
 * // Creating user's account User user = new User(signUpRequest.getName(),
 * signUpRequest.getUsername(), signUpRequest.getEmail(),
 * signUpRequest.getPassword());
 * 
 * user.setPassword(passwordEncoder.encode(user.getPassword()));
 * 
 * Role userRole = roleRepository.findByName(RoleName.ROLE_USER) .orElseThrow(()
 * -> new AppException("User Role not set."));
 * 
 * user.setRoles(Collections.singleton(userRole));
 * 
 * User result = userRepository.save(user);
 * 
 * URI location = ServletUriComponentsBuilder
 * .fromCurrentContextPath().path("/api/users/{username}")
 * .buildAndExpand(result.getUsername()).toUri();
 * 
 * return ResponseEntity.created(location).body(new ApiResponse(true,
 * "User registered successfully")); } }
 */