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
import com.matrimony.demo.service.PartnerPreferenceService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class PartnerPreferenceController {

	private static final Logger logger = LoggerFactory.getLogger(PartnerPreferenceController.class);

	@Autowired
	private PartnerPreferenceService partnerPreferenceService;

	@GetMapping("/user/partnerPreference/getPartnerPreferences")
	public Page<PartnerPreference> getPartnerPreferences(Pageable pageable) {
		return partnerPreferenceService.getPartnerPreferenceProfiles(pageable);
	}

	@PostMapping("/user/partnerPreference/createPartnerPreference")
	public ResponseEntity<?> createPartnerPreference(@Valid @RequestBody PartnerPreference partnerPreference) {
		PartnerPreference result = partnerPreferenceService.createPartnerPreference(partnerPreference);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "PartnerPreference created successfully"));
	}

	@PutMapping("/user/partnerPreference/{id}")
	public PartnerPreference updatePartnerPreference(@PathVariable Long id, @Valid @RequestBody PartnerPreference partnerPreferenceRequest) {
		return partnerPreferenceService.updatePartnerPreference(id, partnerPreferenceRequest);
	}

	@DeleteMapping("/user/partnerPreference/{id}")
	public ResponseEntity<?> deletePartnerPreference(@PathVariable Long id) {
		partnerPreferenceService.deletePartnerPreference(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/partnerPreference/{id}")
	public PartnerPreference fetchPartnerPreferenceById(@PathVariable Long id) {
		return partnerPreferenceService.fetchPartnerPreferenceById(id);
	}

}
