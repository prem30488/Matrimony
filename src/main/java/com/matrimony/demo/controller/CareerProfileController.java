package com.matrimony.demo.controller;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
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

import com.matrimony.demo.model.CareerProfile;
import com.matrimony.demo.payload.ApiResponse;
import com.matrimony.demo.service.CareerProfileService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class CareerProfileController {

	private static final Logger logger = LoggerFactory.getLogger(CareerProfileController.class);

	@Autowired
	private CareerProfileService careerProfileService;

	@GetMapping("/user/careerProfile/getCareerProfiles")
	public Page<CareerProfile> getCareerProfiles(Pageable pageable) {
		return careerProfileService.getCareerProfiles(pageable);
	}

	@PostMapping("/user/careerProfile/createCareerProfile")
	public ResponseEntity<?> createCareerProfile(@Valid @RequestBody CareerProfile careerProfile) {
		CareerProfile result = careerProfileService.createCareerProfile(careerProfile);
		URI location = ServletUriComponentsBuilder.fromCurrentContextPath().path("/user/me")
				.buildAndExpand(result.getId()).toUri();
		return ResponseEntity.created(location).body(new ApiResponse(true, "CareerProfile created successfully"));
	}

	@PutMapping("/user/careerProfile/{id}")
	public CareerProfile updateCareerProfile(@PathVariable Long id, @Valid @RequestBody CareerProfile careerProfileRequest) {
		return careerProfileService.updateCareerProfile(id, careerProfileRequest);
	}

	@DeleteMapping("/user/careerProfile/{id}")
	public ResponseEntity<?> deleteCareerProfile(@PathVariable Long id) {
		careerProfileService.deleteCareerProfile(id);
		return ResponseEntity.ok().build();
	}

	@GetMapping("/user/careerProfile/{id}")
	public CareerProfile fetchCareerProfileById(@PathVariable Long id) {
		return careerProfileService.fetchCareerProfileById(id);
	}

	@GetMapping("/user/profile/getDistinctOccupation")
	public List<Object> getDistinctOccupation() {
		return careerProfileService.fetchDistinctOccupation();
	}
	
}
