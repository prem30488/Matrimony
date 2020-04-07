package com.matrimony.demo.repository;

import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import com.matrimony.demo.model.User;
import com.matrimony.demo.profile.UserProfile;

@Repository
public class FakeUserProfileDataStore {

	private List<UserProfile> USER_PROFILES = new ArrayList<UserProfile>();
	
	@Autowired
	private UserRepository userRepository;
	
	static {
		
	}
	
	public List<UserProfile> getUserProfiles(){
		USER_PROFILES = new ArrayList<UserProfile>();
		List<User> users =userRepository.findAll();
		
		users.forEach(user -> USER_PROFILES.add(
				new UserProfile(user.getId(), user.getUsername(), user.getImageUrl())			
				));
		
		return USER_PROFILES;
	}
}
