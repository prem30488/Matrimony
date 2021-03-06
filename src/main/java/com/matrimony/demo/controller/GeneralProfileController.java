package com.matrimony.demo.controller;

import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.matrimony.demo.model.Profile;
import com.matrimony.demo.payload.ApiResponse;
import com.matrimony.demo.payload.Shortlist;
import com.matrimony.demo.repository.UserRepository;
import com.matrimony.demo.security.CurrentUser;
import com.matrimony.demo.security.UserPrincipal;
import com.matrimony.demo.service.EmailService;
import com.matrimony.demo.service.ProfileService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class GeneralProfileController {

	private static final Logger logger = LoggerFactory.getLogger(GeneralProfileController.class);

	@Autowired
	private ProfileService profileService;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private EmailService emailService;

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
	
	@GetMapping("/user/profile/getDistinctMaritalStatus")
	public List<Object> getDistinctMaritalStatus() {
		return profileService.fetchDistinctmaritalStatus();
	}
	
	@GetMapping("/user/profile/getDistinctMotherTounge")
	public List<Object> getDistinctMotherTounge() {
		return profileService.fetchDistinctmotherTounge();
	}

	@GetMapping("/user/profile/getDistinctEducation")
	public List<Object> getDistinctEducation() {
		return profileService.fetchDistincteducation();
	}
	
	@GetMapping("/user/profile/getDistinctPhysicalStatus")
	public List<Object> getDistinctPhysicalStatus() {
		return profileService.fetchDistinctPhysicalStatuses();
	}
	
	@GetMapping("/user/profile/getDistinctDiet")
	public List<Object> getDistinctDiet() {
		return profileService.fetchDistinctDietes();
	}
	
	@GetMapping("/user/profile/getDistinctSmoke")
	public List<Object> getDistinctSmoke() {
		return profileService.fetchDistinctSmoke();
	}
	
	@GetMapping("/user/profile/getDistinctDrink")
	public List<Object> getDistinctDrink() {
		return profileService.fetchDistinctDrink();
	}
	
	@PostMapping("/user/profile/sendInterest")
	public Boolean shortlist(@CurrentUser UserPrincipal userPrincipal,@RequestBody Shortlist shortlist) {
		try {
			//sendEmailInterest(userPrincipal.getEmail(),userRepository.getOne(shortlist.getId()).getEmail());
			emailService.sendMail("prem30488@gmail.com","Test subject","Test mail");
			return true;
		}
		catch(Exception ex) {
			ex.printStackTrace();
			return false;
		}		
	}
}
