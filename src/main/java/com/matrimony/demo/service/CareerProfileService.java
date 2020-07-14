package com.matrimony.demo.service;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.matrimony.demo.exception.ResourceNotFoundException;
import com.matrimony.demo.model.CareerProfile;
import com.matrimony.demo.model.User;
import com.matrimony.demo.repository.CareerProfileRepository;
import com.matrimony.demo.repository.UserRepository;

@Service
public class CareerProfileService {

	@Autowired
	private CareerProfileRepository careerProfileRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    public Page<CareerProfile> getCareerProfiles(Pageable pageable) {
        return careerProfileRepository.findAll(pageable);
    }

    public CareerProfile createCareerProfile(@Valid @RequestBody CareerProfile careerProfile) {
        return careerProfileRepository.save(careerProfile);
    }

    public CareerProfile updateCareerProfile(@PathVariable Long id,
                                   @Valid @RequestBody CareerProfile careerProfileRequest) {
        return careerProfileRepository.findById(id)
                .map(careerProfile -> {
                	careerProfile.setId(id);
                	careerProfile.setEducation(careerProfileRequest.getEducation());
                	careerProfile.setEducationDetails(careerProfileRequest.getEducationDetails());
                	careerProfile.setOccupation(careerProfileRequest.getOccupation());
                	careerProfile.setAnnualIncome(careerProfileRequest.getAnnualIncome());
                	if(userRepository.existsById(id)) {
                		Optional<User> user = userRepository.findById(id);
                		if(user.isPresent()) {
                			careerProfile.setUser(user.get());
                		}
                	}
                	return careerProfileRepository.save(careerProfile);
                }).orElseThrow(() -> new ResourceNotFoundException("Career Profile not found with", "id " , id));
    }

    public ResponseEntity<?> deleteCareerProfile(@PathVariable Long id) {
        return careerProfileRepository.findById(id)
                .map(CareerProfile -> {
                    careerProfileRepository.delete(CareerProfile);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Career Profile not found with", "id " , id));
    }
    
	public CareerProfile fetchCareerProfileById(@PathVariable Long id) {
		return careerProfileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Career Profile not found with ", "id " , id));
	}

	public List<Object> fetchDistinctOccupation() {
		List<Object> list = careerProfileRepository.getDistinctOccupation();
		return list;
	}
	
	
}
