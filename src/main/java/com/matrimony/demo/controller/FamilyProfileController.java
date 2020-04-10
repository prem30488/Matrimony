package com.matrimony.demo.controller;

import java.net.URI;

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

import com.matrimony.demo.model.FamilyProfile;
import com.matrimony.demo.payload.ApiResponse;
import com.matrimony.demo.service.FamilyProfileService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class FamilyProfileController {

	private static final Logger logger = LoggerFactory.getLogger(FamilyProfileController.class);

	@Autowired
	private FamilyProfileService familyProfileService;

	@GetMapping("/user/familyProfile/getFamilyProfiles")
	public Page<FamilyProfile> getFamilyProfiles(Pageable pageable) {
		return familyProfileService.getFamilyProfiles(pageable);
	}

	@PostMapping("/user/familyProfile/createFamilyProfile")
	public ResponseEntity<?> createFamilyProfile(@Valid @RequestBody FamilyProfile familyProfile) {
		FamilyProfile result = familyProfileService.createFamilyProfile(familyProfile);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "FamilyProfile created successfully"));
	}

	@PutMapping("/user/familyProfile/{id}")
	public FamilyProfile updateFamilyProfile(@PathVariable Long id, @Valid @RequestBody FamilyProfile familyProfileRequest) {
		return familyProfileService.updateFamilyProfile(id, familyProfileRequest);
	}

	@DeleteMapping("/user/familyProfile/{id}")
	public ResponseEntity<?> deleteFamilyProfile(@PathVariable Long id) {
		familyProfileService.deleteFamilyProfile(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/familyProfile/{id}")
	public FamilyProfile fetchFamilyProfileById(@PathVariable Long id) {
		return familyProfileService.fetchFamilyProfileById(id);
	}

}
