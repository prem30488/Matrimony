package com.matrimony.demo.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.matrimony.demo.exception.ResourceNotFoundException;
import com.matrimony.demo.model.Profile;
import com.matrimony.demo.model.User;
import com.matrimony.demo.payload.MaritalStatusPayload;
import com.matrimony.demo.repository.GeneralProfileRepository;
import com.matrimony.demo.repository.UserRepository;

@Service
public class ProfileService {

	@Autowired
	private GeneralProfileRepository profileRepository;
	
	@Autowired
	private UserRepository userRepository;
	
    public Page<Profile> getProfiles(Pageable pageable) {
        return profileRepository.findAll(pageable);
    }

    public Profile createProfile(Profile profile) {
        return profileRepository.save(profile);
    }

    public Profile updateProfile(Long id, Profile profileRequest) {
        return profileRepository.findById(id)
                .map(profile -> {
                	profile.setId(id);
                	profile.setAge(profileRequest.getAge());
                	profile.setHeight(profileRequest.getHeight());
                	profile.setReligion(profileRequest.getReligion());
                	profile.setMaritalStatus(profileRequest.getMaritalStatus());
                	profile.setLocation(profileRequest.getLocation());
                	profile.setEducation(profileRequest.getEducation());
                	profile.setAbout(profileRequest.getAbout());
                	profile.setName(profileRequest.getName());
                	profile.setBodyType(profileRequest.getBodyType());
                	profile.setPhysicalStatus(profileRequest.getPhysicalStatus());
                	profile.setMotherToung(profileRequest.getMotherToung());
                	profile.setComplexion(profileRequest.getComplexion());
                	profile.setWeight(profileRequest.getWeight());
                	profile.setBloodGroup(profileRequest.getBloodGroup());
                	profile.setDiet(profileRequest.getDiet());
                	profile.setSmoke(profileRequest.isSmoke());
                	profile.setDrink(profileRequest.isDrink());
                	profile.setState(profileRequest.getState());
                	if(userRepository.existsById(id)) {
                		Optional<User> user = userRepository.findById(id);
                		if(user.isPresent()) {
                			profile.setUser(user.get());
                		}
                	}
                	return profileRepository.save(profile);
                }).orElseThrow(() -> new ResourceNotFoundException("Profile not found with", "id " , id));
    }

    public ResponseEntity<?> deleteProfile(Long id) {
        return profileRepository.findById(id)
                .map(Profile -> {
                    profileRepository.delete(Profile);
                    return ResponseEntity.ok().build();
                }).orElseThrow(() -> new ResourceNotFoundException("Profile not found with", "id " , id));
    }
    
	public Profile fetchProfileById(Long id) {
		return profileRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Profile not found with ", "id " , id));
	}
	
	public List<Object> fetchDistinctmaritalStatus(){
		List<Object> list = profileRepository.getDistinctMaritalStatus();
		//List<MaritalStatusPayload> result = new ArrayList<MaritalStatusPayload>();
		//list.forEach(profile -> {
		  //  result.add(new MaritalStatusPayload(profile.getMaritalStatus(), profile.getCount()));
		//});
		return list;
	}

	public List<Object> fetchDistinctmotherTounge() {
		List<Object> list = profileRepository.getDistinctMotherToung();
		return list;
	}

	public List<Object> fetchDistincteducation() {
		List<Object> list = profileRepository.getDistinctEducation();
		return list;
	}

	public List<Object> fetchDistinctPhysicalStatuses() {
		List<Object> list = profileRepository.getDistinctPhysicalStatus();
		return list;
	}

	public List<Object> fetchDistinctDietes() {
		List<Object> list = profileRepository.getDistinctDiet();
		return list;
	}

	public List<Object> fetchDistinctSmoke() {
		List<Object> list = profileRepository.getDistinctSmoke();
		return list;
	}

	public List<Object> fetchDistinctDrink() {
		List<Object> list = profileRepository.getDistinctDrink();
		return list;
	}

}
