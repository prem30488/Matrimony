package com.matrimony.demo.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.matrimony.demo.model.Profile;
import com.matrimony.demo.model.User;
import com.matrimony.demo.payload.Shortlist;
import com.matrimony.demo.security.CurrentUser;
import com.matrimony.demo.security.UserPrincipal;
import com.matrimony.demo.service.ProfileService;
import com.matrimony.demo.service.ShortlistService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ShortlistController {

	private static final Logger logger = LoggerFactory.getLogger(ShortlistController.class);

	@Autowired
	private ShortlistService shortlistService;
	
	@PostMapping("/user/profile/shortlist")
	public Boolean shortlist(@CurrentUser UserPrincipal userPrincipal,@RequestBody Shortlist shortlist) {
		return shortlistService.shortlistUser(userPrincipal,shortlist.getId());
	}
	
	@PostMapping("/user/profile/unshortlist")
	public Boolean unshortlist(@CurrentUser UserPrincipal userPrincipal,@RequestBody Shortlist shortlist) {
		return shortlistService.unshortlistUser(userPrincipal,shortlist.getId());
	}
	
	@GetMapping("/user/profile/getShortlistedProfiles")
	public Page<User> getProfiles(@PageableDefault(size=1) Pageable pageable,@CurrentUser UserPrincipal userPrincipal) {
		return shortlistService.getShortlistedProfiles(pageable,userPrincipal);
	}

	
	@RequestMapping(value = "/user/profile/isShortlisted", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public com.matrimony.demo.payload.isShortlisted isShortlisted(@CurrentUser UserPrincipal userPrincipal,@RequestBody Shortlist shortlist)
	{
		com.matrimony.demo.payload.isShortlisted obj = new com.matrimony.demo.payload.isShortlisted();
		obj.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, shortlist.getId()));
		return obj;
	}
}
