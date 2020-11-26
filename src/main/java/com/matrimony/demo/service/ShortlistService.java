package com.matrimony.demo.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.matrimony.demo.model.User;
import com.matrimony.demo.repository.UserRepository;
import com.matrimony.demo.security.UserPrincipal;

@Service
public class ShortlistService {

	@Autowired
	private UserRepository userRepository;

	public boolean shortlistUser(UserPrincipal userPrincipal,Long id) {
		if (userRepository.existsById(id)) {
			Optional<User> u = userRepository.findById(userPrincipal.getId());
			if (u.isPresent()) {
				if (u.get().getShortlisted().contains(userRepository.findById(id).get()) || u.get().getId() == id) {
					return false;
				}
				u.get().getShortlisted().add(userRepository.findById(id).get());
				userRepository.save(u.get());
				return true;
			}
		}
		return false;
	}

	public Boolean unshortlistUser(UserPrincipal userPrincipal, Long id) {
		if (userRepository.existsById(id)) {
			Optional<User> u = userRepository.findById(userPrincipal.getId());
			if (u.isPresent()) {
				List<User> shortlistedUsers = u.get().getShortlisted();
				if (u.get().getShortlisted().contains(userRepository.findById(id).get()) || u.get().getId() == id) {					
					for(int i=0;i<shortlistedUsers.size();i++) {
						if(shortlistedUsers.get(i).getId() == id) {
							u.get().getShortlisted().remove(i);
						}
					}
					userRepository.save(u.get());
					return true;
				}
			}
		}
		return false;
	}
	
	public Page<User> getShortlistedProfiles(Pageable pageable,UserPrincipal userPrincipal) {
		Optional<User> u = userRepository.findById(userPrincipal.getId());
		if (u.isPresent()) {
			List<User> users = u.get().getShortlisted();
			int start = (int)pageable.getOffset();
			int end = (start + pageable.getPageSize()) > users.size() ? users.size() : (start + pageable.getPageSize());
			List<User> list = users.subList(start, end);
		    Collections.sort(list,Collections.reverseOrder());
			Page<User> page = new PageImpl<>(list,pageable,users.size());
			return page;
		}
		return null;
	}
	
	public Boolean isShortlisted(UserPrincipal userPrincipal, Long id) {
		Optional<User> u = userRepository.findById(userPrincipal.getId());
		Boolean isShortlisted = false;
		if(u.isPresent()) {
			if (u.get().getShortlisted().contains(userRepository.findById(id).get())) {
				isShortlisted= true;
			}
		}
		return isShortlisted;
	}
	
	public Boolean isShortlistedMe(UserPrincipal userPrincipal, Long id) {
		Optional<User> u = userRepository.findById(userPrincipal.getId());
		Boolean isShortlisted = false;
		if(u.isPresent()) {
			if (u.get().getShortlistedOf().contains(userRepository.findById(id).get())) { //|| u.get().getId() == id) {
				isShortlisted= true;
			}
		}
		return isShortlisted;
	}

}
