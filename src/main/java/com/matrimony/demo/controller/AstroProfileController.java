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
import com.matrimony.demo.model.AuthProvider;
import com.matrimony.demo.model.PartnerPreference;
import com.matrimony.demo.model.AstroProfile;
import com.matrimony.demo.model.Role;
import com.matrimony.demo.model.RoleName;
import com.matrimony.demo.model.User;
import com.matrimony.demo.payload.ApiResponse;
import com.matrimony.demo.payload.SignUpRequest;
import com.matrimony.demo.payload.UserIdentityAvailability;
import com.matrimony.demo.repository.RoleRepository;
import com.matrimony.demo.repository.UserRepository;
import com.matrimony.demo.security.CurrentUser;
import com.matrimony.demo.security.UserPrincipal;
import com.matrimony.demo.service.AstroProfileService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class AstroProfileController {

	private static final Logger logger = LoggerFactory.getLogger(AstroProfileController.class);

	@Autowired
	private AstroProfileService astroProfileService;

	@GetMapping("/user/astroProfile/getAstroProfiles")
	public Page<AstroProfile> getAstroProfiles(Pageable pageable) {
		return astroProfileService.getAstroProfiles(pageable);
	}

	@PostMapping("/user/astroProfile/createAstroProfile")
	public ResponseEntity<?> createAstroProfile(@Valid @RequestBody AstroProfile astroProfile) {
		AstroProfile result = astroProfileService.createAstroProfile(astroProfile);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "AstroProfile created successfully"));
	}

	@PutMapping("/user/astroProfile/{id}")
	public AstroProfile updateAstroProfile(@PathVariable Long id, @Valid @RequestBody AstroProfile astroProfileRequest) {
		return astroProfileService.updateAstroProfile(id, astroProfileRequest);
	}

	@DeleteMapping("/user/astroProfile/{id}")
	public ResponseEntity<?> deleteAstroProfile(@PathVariable Long id) {
		astroProfileService.deleteAstroProfile(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/astroProfile/{id}")
	public AstroProfile fetchAstroProfileById(@PathVariable Long id) {
		return astroProfileService.fetchAstroProfileById(id);
	}

}
