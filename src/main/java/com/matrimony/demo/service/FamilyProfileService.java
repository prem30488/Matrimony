package com.matrimony.demo.service;

import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.matrimony.demo.exception.ResourceNotFoundException;
import com.matrimony.demo.model.AstroProfile;
import com.matrimony.demo.model.FamilyProfile;
import com.matrimony.demo.model.User;
import com.matrimony.demo.repository.FamilyProfileRepository;
import com.matrimony.demo.repository.UserRepository;

@Service
public class FamilyProfileService {

	@Autowired
	private FamilyProfileRepository familyProfileRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    public Page<FamilyProfile> getFamilyProfiles(Pageable pageable) {
        return familyProfileRepository.findAll(pageable);
    }

    public FamilyProfile createFamilyProfile(@Valid @RequestBody FamilyProfile familyProfile) {
        return familyProfileRepository.save(familyProfile);
    }

    public FamilyProfile updateFamilyProfile(@PathVariable Long id,
                                   @Valid @RequestBody FamilyProfile familyProfileRequest) {
        return familyProfileRepository.findById(id)
                .map(familyProfile -> {
                	familyProfile.setId(id);
                	familyProfile.setOccupationFather(familyProfileRequest.getOccupationFather());
                	familyProfile.setOccupationMother(familyProfileRequest.getOccupationMother());
                	familyProfile.setNumOfBrother(familyProfileRequest.getNumOfBrother());
                	familyProfile.setNumOfSister(familyProfileRequest.getNumOfSister());
                	if(userRepository.existsById(id)) {
                		Optional<User> user = userRepository.findById(id);
                		if(user.isPresent()) {
                			familyProfile.setUser(user.get());
                		}
                	}
                	return familyProfileRepository.save(familyProfile);
                }).orElseThrow(() -> new ResourceNotFoundException("Family Profile not found with", "id " , id));
    }

    public ResponseEntity<?> deleteFamilyProfile(@PathVariable Long id) {
        return familyProfileRepository.findById(id)
                .map(astroProfile -> {
                    familyProfileRepository.delete(astroProfile);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Family Profile not found with", "id " , id));
    }
    
	public FamilyProfile fetchFamilyProfileById(@PathVariable Long id) {
		return familyProfileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Family Profile not found with ", "id " , id));
	}
	
}
