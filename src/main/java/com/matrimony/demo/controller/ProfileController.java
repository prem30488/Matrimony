package com.matrimony.demo.controller;

import java.net.URI;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
import com.matrimony.demo.service.ProfileService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ProfileController {

	private static final Logger logger = LoggerFactory.getLogger(ProfileController.class);

	@Autowired
	private ProfileService profileService;

	@GetMapping("/user/profile/getProfiles")
	public Page<Profile> getProfiles(Pageable pageable) {
		return profileService.getProfiles(pageable);
	}

	@PostMapping("/user/profile/createProfile")
	public ResponseEntity<?> createProfile(@Valid @RequestBody Profile profile) {
		Profile result = profileService.createProfile(profile);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "Profile created successfully"));
	}

	@PutMapping("/user/profile/{id}")
	public Profile updateProfile(@PathVariable Long id, @Valid @RequestBody Profile profileRequest) {
		return profileService.updateProfile(id, profileRequest);
	}

	@DeleteMapping("/user/profile/{id}")
	public ResponseEntity<?> deleteProfile(@PathVariable Long id) {
		profileService.deleteProfile(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/profile/{id}")
	public Profile fetchProfileById(@PathVariable Long id) {
		return profileService.fetchProfileById(id);
	}

}
