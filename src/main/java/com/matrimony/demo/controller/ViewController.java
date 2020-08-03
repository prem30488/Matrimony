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
import com.matrimony.demo.payload.View;
import com.matrimony.demo.security.CurrentUser;
import com.matrimony.demo.security.UserPrincipal;
import com.matrimony.demo.service.ProfileService;
import com.matrimony.demo.service.ShortlistService;
import com.matrimony.demo.service.ViewService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class ViewController {

	private static final Logger logger = LoggerFactory.getLogger(ViewController.class);

	@Autowired
	private ViewService viewService;
	
	@PostMapping("/user/profile/view")
	public Boolean view(@CurrentUser UserPrincipal userPrincipal,@RequestBody View view) {
		return viewService.viewUser(userPrincipal,view.getId());
	}
	
	@PostMapping("/user/profile/unview")
	public Boolean unview(@CurrentUser UserPrincipal userPrincipal,@RequestBody View view) {
		return viewService.unviewUser(userPrincipal,view.getId());
	}
	
	@GetMapping("/user/profile/getViewedProfiles")
	public Page<User> getProfiles(@PageableDefault(size=1) Pageable pageable,@CurrentUser UserPrincipal userPrincipal) {
		return viewService.getViewedProfiles(pageable,userPrincipal);
	}

	
	@RequestMapping(value = "/user/profile/isViewed", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public com.matrimony.demo.payload.isViewed isViewed(@CurrentUser UserPrincipal userPrincipal,@RequestBody View view)
	{
		com.matrimony.demo.payload.isViewed obj = new com.matrimony.demo.payload.isViewed();
		obj.setIsViewed(viewService.isViewed(userPrincipal, view.getId()));
		return obj;
	}
}
