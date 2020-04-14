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
import com.matrimony.demo.model.PartnerPreference;
import com.matrimony.demo.model.User;
import com.matrimony.demo.repository.PartnerPreferenceRepository;
import com.matrimony.demo.repository.UserRepository;

@Service
public class PartnerPreferenceService {

	@Autowired
	private PartnerPreferenceRepository partnerPreferenceRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    public Page<PartnerPreference> getPartnerPreferenceProfiles(Pageable pageable) {
        return partnerPreferenceRepository.findAll(pageable);
    }

    public PartnerPreference createPartnerPreference(@Valid @RequestBody PartnerPreference partnerPreference) {
        return partnerPreferenceRepository.save(partnerPreference);
    }

    public PartnerPreference updatePartnerPreference(@PathVariable Long id,
                                   @Valid @RequestBody PartnerPreference partnerPreferenceRequest) {
        return partnerPreferenceRepository.findById(id)
                .map(partnerPreference -> {
                	partnerPreference.setId(id);
                	partnerPreference.setAge(partnerPreferenceRequest.getAge());
                	partnerPreference.setMaritalStatus(partnerPreferenceRequest.getMaritalStatus());
                	partnerPreference.setBodyType(partnerPreferenceRequest.getBodyType());
                	partnerPreference.setComplexion(partnerPreferenceRequest.getComplexion());
                	partnerPreference.setHeight(partnerPreferenceRequest.getHeight());
                	partnerPreference.setDiet(partnerPreferenceRequest.getDiet());
                	partnerPreference.setManglik(partnerPreferenceRequest.isManglik());
                	partnerPreference.setMangal(partnerPreferenceRequest.isMangal());
                	partnerPreference.setReligion(partnerPreferenceRequest.getReligion());
                	partnerPreference.setCaste(partnerPreferenceRequest.getCaste());
                	partnerPreference.setMotherTounge(partnerPreferenceRequest.getMotherTounge());
                	partnerPreference.setEducation(partnerPreferenceRequest.getEducation());
                	partnerPreference.setOccupation(partnerPreferenceRequest.getOccupation());
                	partnerPreference.setLocationOfHome(partnerPreferenceRequest.getLocationOfHome());
                	partnerPreference.setState(partnerPreferenceRequest.getState());
                	partnerPreference.setResidencyStatus(partnerPreferenceRequest.getResidencyStatus());
                	if(userRepository.existsById(id)) {
                		Optional<User> user = userRepository.findById(id);
                		if(user.isPresent()) {
                			partnerPreference.setUser(user.get());
                		}
                	}
                	return partnerPreferenceRepository.save(partnerPreference);
                }).orElseThrow(() -> new ResourceNotFoundException("Partner Preference not found with", "id " , id));
    }

    public ResponseEntity<?> deletePartnerPreference(@PathVariable Long id) {
        return partnerPreferenceRepository.findById(id)
                .map(astroProfile -> {
                    partnerPreferenceRepository.delete(astroProfile);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Partner Preference not found with", "id " , id));
    }
    
	public PartnerPreference fetchPartnerPreferenceById(@PathVariable Long id) {
		return partnerPreferenceRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Partner Preference not found with ", "id " , id));
	}
	
}
