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
import com.matrimony.demo.model.User;
import com.matrimony.demo.repository.AstroRepository;
import com.matrimony.demo.repository.UserRepository;

@Service
public class AstroProfileService {

	@Autowired
	private AstroRepository astroRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    public Page<AstroProfile> getAstroProfiles(Pageable pageable) {
        return astroRepository.findAll(pageable);
    }

    public AstroProfile createAstroProfile(@Valid @RequestBody AstroProfile astroProfile) {
        return astroRepository.save(astroProfile);
    }

    public AstroProfile updateAstroProfile(@PathVariable Long id,
                                   @Valid @RequestBody AstroProfile astroProfileRequest) {
        return astroRepository.findById(id)
                .map(astroProfile -> {
                	astroProfile.setId(id);
                	astroProfile.setDateOfBirth(astroProfileRequest.getDateOfBirth());
                	astroProfile.setTimeOfBirth(astroProfileRequest.getTimeOfBirth());
                	astroProfile.setReligion(astroProfileRequest.getReligion());
                	astroProfile.setCaste(astroProfileRequest.getCaste());
                	astroProfile.setSubCaste(astroProfileRequest.getSubCaste());
                	astroProfile.setRaasi(astroProfileRequest.getRaasi());
                	astroProfile.setMangal(astroProfileRequest.isMangal());
                	astroProfile.setManglik(astroProfileRequest.isManglik());
                	if(userRepository.existsById(id)) {
                		Optional<User> user = userRepository.findById(id);
                		if(user.isPresent()) {
                			astroProfile.setUser(user.get());
                		}
                	}
                	return astroRepository.save(astroProfile);
                }).orElseThrow(() -> new ResourceNotFoundException("AstroProfile not found with", "id " , id));
    }

    public ResponseEntity<?> deleteAstroProfile(@PathVariable Long id) {
        return astroRepository.findById(id)
                .map(astroProfile -> {
                    astroRepository.delete(astroProfile);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("AstroProfile not found with", "id " , id));
    }
    
	public AstroProfile fetchAstroProfileById(@PathVariable Long id) {
		return astroRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("AstroProfile not found with ", "id " , id));
	}
	
}
