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
public class ViewService {

	@Autowired
	private UserRepository userRepository;

	public boolean viewUser(UserPrincipal userPrincipal,Long id) {
		if (userRepository.existsById(id)) {
			Optional<User> u = userRepository.findById(userPrincipal.getId());
			if (u.isPresent()) {
				if (u.get().getViewed().contains(userRepository.findById(id).get()) || u.get().getId() == id) {
					return false;
				}
				u.get().getViewed().add(userRepository.findById(id).get());
				userRepository.save(u.get());
				return true;
			}
		}
		return false;
	}

	public Boolean unviewUser(UserPrincipal userPrincipal, Long id) {
		if (userRepository.existsById(id)) {
			Optional<User> u = userRepository.findById(userPrincipal.getId());
			if (u.isPresent()) {
				List<User> viewedUsers = u.get().getViewed();
				if (u.get().getViewed().contains(userRepository.findById(id).get()) || u.get().getId() == id) {					
					for(int i=0;i<viewedUsers.size();i++) {
						if(viewedUsers.get(i).getId() == id) {
							u.get().getViewed().remove(i);
						}
					}
					userRepository.save(u.get());
					return true;
				}
			}
		}
		return false;
	}
	
	public Page<User> getViewedProfiles(Pageable pageable,UserPrincipal userPrincipal) {
		Optional<User> u = userRepository.findById(userPrincipal.getId());
		if (u.isPresent()) {
			List<User> users = u.get().getViewed();
			int start = (int)pageable.getOffset();
			int end = (start + pageable.getPageSize()) > users.size() ? users.size() : (start + pageable.getPageSize());
			List<User> list = users.subList(start, end);
		    Collections.sort(list,Collections.reverseOrder());
			Page<User> page = new PageImpl<>(list,pageable,users.size());
			return page;
		}
		return null;
	}
	
	public Boolean isViewed(UserPrincipal userPrincipal, Long id) {
		Optional<User> u = userRepository.findById(userPrincipal.getId());
		Boolean isViewed = false;
		if(u.isPresent()) {
			if (u.get().getViewed().contains(userRepository.findById(id).get()) || u.get().getId() == id) {
				isViewed= true;
			}
		}
		return isViewed;
	}
	
	public Boolean isViewedMe(UserPrincipal userPrincipal, Long id) {
		Optional<User> u = this.userRepository.findById(userPrincipal.getId());
		Boolean isViewed = false;
		if(u.isPresent()) {
			if (u.get().getViewedOf().contains(userRepository.findById(id).get())) {
				isViewed= true;
				//System.out.println(u.get().getViewedOf().get(0).getName());	
			}
		}
		return isViewed;
	}

}
