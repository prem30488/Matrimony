package com.matrimony.demo.service;

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

	public Page<User> getShortlistedProfiles(Pageable pageable,UserPrincipal userPrincipal) {
		Optional<User> u = userRepository.findById(userPrincipal.getId());
		if (u.isPresent()) {
			List<User> list = u.get().getShortlisted();
			long pageSize = pageable.getPageSize();
			long pageOffset = pageable.getOffset();
			long total = pageOffset + list.size() + (list.size() == pageSize ? pageSize : 0);
			List<User> list2 = list.subList((int)pageable.getOffset(),(int) total);
			Page<User> page = new PageImpl<>(list2,pageable,list.size());
			return userRepository.findAll(pageable);
		}
		return null;
	}
}
