package com.matrimony.demo.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.matrimony.demo.repository.solr.SolrSearchEntityRepository;
import com.matrimony.demo.security.CurrentUser;
import com.matrimony.demo.security.UserPrincipal;
import com.matrimony.demo.service.ShortlistService;
import com.matrimony.demo.solrmodel.SolrSearchEntity;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class SolrSearchEntityController {

	@Autowired
	SolrSearchEntityRepository solrSearchEntityRepository;

	@Autowired
	private ShortlistService shortlistService;

	@PostMapping("/solrSearchEntity")
	public String createsolrSearchEntity(@RequestBody SolrSearchEntity solrSearchEntity) {
		String description = "SolrSearchEntity Created";
		solrSearchEntityRepository.save(solrSearchEntity);
		return description;
	}

	@GetMapping("/solrSearchEntity/{id}")
	public SolrSearchEntity readSolrSearchEntity(@PathVariable String id) {
		return solrSearchEntityRepository.findById(id).get();
	}

	@PutMapping("/solrSearchEntity")
	public String updateSolrSearchEntity(@RequestBody SolrSearchEntity solrSearchEntity) {
		String description = "SolrSearchEntity Updated";
		solrSearchEntityRepository.save(solrSearchEntity);
		return description;
	}

	@DeleteMapping("/solrSearchEntity/{id}")
	public String deleteSolrSearchEntity(@PathVariable String id) {
		String description = "SolrSearchEntity Deleted";
		solrSearchEntityRepository.delete(solrSearchEntityRepository.findById(id).get());
		return description;
	}

	@GetMapping("/solrSearchEntity/age/{solrSearchEntityState}/{page}")
	public List<SolrSearchEntity> findSolrSearchEntity(@PathVariable String solrSearchEntityAge,
			@PathVariable int page) {
		return solrSearchEntityRepository.findBySolrSearchEntityAge(solrSearchEntityAge, PageRequest.of(page, 5))
				.getContent();
	}

	@GetMapping("/solrSearchEntity/search/{searchTerm}/{page}")
	public List<SolrSearchEntity> findSolrSearchEntityBySearchTerm(@PathVariable String searchTerm,
			@PathVariable int page) {
		return solrSearchEntityRepository.findByCustomerQuery(searchTerm, PageRequest.of(page, 5)).getContent();
	}

	@RequestMapping("/solrSearchEntity/getAll")
	public Page<SolrSearchEntity> getAllDocs(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
		Page<SolrSearchEntity> tempList = this.solrSearchEntityRepository.findAll(pageable);
		tempList.stream()
				.forEach((item) -> item.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item.getId())));
		return tempList;

	}

	@GetMapping("/solrSearchEntity/getWeeklyEntities")
	public List<SolrSearchEntity> getWeeklysolrSearchEntity() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate todayLocaleDate = LocalDate.now();

		LocalDate weekStartLocaleDate = getWeekStart(todayLocaleDate).minusDays(1);
		LocalDate weekEndLocaleDate = weekStartLocaleDate.plusDays(7);
		
		Date weekStartDate = Date.from(weekStartLocaleDate.atStartOfDay(defaultZoneId).toInstant());
		Date weekEndDate = Date.from(weekEndLocaleDate.atStartOfDay(defaultZoneId).toInstant());
		//System.out.println("Week start date is :" + weekStartDate);
		//System.out.println("Week end date is :" + weekEndDate);
		return solrSearchEntityRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(weekStartDate, weekEndDate);
	}
	
	@GetMapping("/solrSearchEntity/getMonthlyEntities")
	public List<SolrSearchEntity> getMonthlysolrSearchEntity() {
		ZoneId defaultZoneId = ZoneId.systemDefault();
		LocalDate initial = LocalDate.now();

		LocalDate start = initial.withDayOfMonth(1).minusDays(1);
		LocalDate end = initial.withDayOfMonth(initial.lengthOfMonth()).plusDays(1);
		
		Date monthStartDate = Date.from(start.atStartOfDay(defaultZoneId).toInstant());
		Date monthEndDate = Date.from(end.atStartOfDay(defaultZoneId).toInstant());
		System.out.println("Month start date is :" + monthStartDate);
		System.out.println("Month end date is :" + monthEndDate);
		return solrSearchEntityRepository.findByCreatedAtBetweenOrderByCreatedAtDesc(monthStartDate, monthEndDate);
	}

	public static LocalDate getWeekStart(final LocalDate date) {
		return date.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
	}

	@GetMapping("/solrSearchEntity/findByImageUrlIsNotNullOrderByIdDesc")
	public List<SolrSearchEntity> getImageExistssolrSearchEntity() {
		return solrSearchEntityRepository.findByImageUrlIsNotNullOrderByIdDesc();
	}
	
	@GetMapping("/solrSearchEntity/findByMaritalStatusIn")
	public Page<SolrSearchEntity> findByMaritalStatusIn(@RequestBody Collection<String> maritalStatus, Pageable pagebale) {
		return solrSearchEntityRepository.findByMaritalStatusIn(maritalStatus, pagebale);
	}
	
}
