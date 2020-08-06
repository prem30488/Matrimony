package com.matrimony.demo.controller;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matrimony.demo.model.User;
import com.matrimony.demo.payload.MaritalStatusPayload;
import com.matrimony.demo.repository.solr.SolrSearchEntityRepository;
import com.matrimony.demo.security.CurrentUser;
import com.matrimony.demo.security.UserPrincipal;
import com.matrimony.demo.service.ShortlistService;
import com.matrimony.demo.service.ViewService;
import com.matrimony.demo.solrmodel.SolrSearchEntity;
import com.matrimony.demo.util.Wrapper;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class SolrSearchEntityController {

	@Autowired
	SolrSearchEntityRepository solrSearchEntityRepository;

	@Autowired
	private ShortlistService shortlistService;
	
	@Autowired
	private ViewService viewService;

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
				.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
				
		tempList.stream()
				.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));
		
		return tempList;

	}
	
	@RequestMapping("/solrSearchEntity/getAllViewedMe")
	public Page<SolrSearchEntity> getAllViewedDocs(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
		List<SolrSearchEntity> tempList = this.solrSearchEntityRepository.findAllDocs();
		List<SolrSearchEntity> tempList2 = new ArrayList<SolrSearchEntity>();
		for(int i=0 ; i<tempList.size();i++) {
			if(this.viewService.isViewedMe(userPrincipal, tempList.get(i).getId())) {
				tempList2.add(tempList.get(i));
			}
		}
				
		tempList2.stream()
				.forEach((item) -> item.setIsViewed(this.viewService.isViewed(userPrincipal, item.getId())));
				
		tempList2.stream()
				.forEach((item2) -> item2.setIsShortlisted(this.shortlistService.isShortlisted(userPrincipal, item2.getId())));
		
		tempList2.stream()
				.forEach((item) -> item.setIsViewedMe(this.viewService.isViewedMe(userPrincipal, item.getId())));

		int start = (int)pageable.getOffset();
		int end = (start + pageable.getPageSize()) > tempList2.size() ? tempList2.size() : (start + pageable.getPageSize());
		List<SolrSearchEntity> list = tempList2.subList(start, end);
		Page<SolrSearchEntity> page = new PageImpl<SolrSearchEntity>(list,pageable,tempList2.size());
		
		return page;

	}

	@RequestMapping("/solrSearchEntity/getAllViewedMeNotShortlisted")
	public Page<SolrSearchEntity> getAllViewedMeNotShortlisted(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
		List<SolrSearchEntity> tempList = this.solrSearchEntityRepository.findAllDocs();
		List<SolrSearchEntity> tempList2 = new ArrayList<SolrSearchEntity>();
		for(int i=0 ; i<tempList.size();i++) {
			if(this.viewService.isViewedMe(userPrincipal, tempList.get(i).getId()) && !this.shortlistService.isShortlistedMe(userPrincipal, tempList.get(i).getId())) {
				tempList2.add(tempList.get(i));
			}
		}
				
		tempList2.stream()
				.forEach((item) -> item.setIsViewed(this.viewService.isViewed(userPrincipal, item.getId())));
				
		tempList2.stream()
				.forEach((item2) -> item2.setIsShortlisted(this.shortlistService.isShortlisted(userPrincipal, item2.getId())));
		
		tempList2.stream()
				.forEach((item) -> item.setIsViewedMe(this.viewService.isViewedMe(userPrincipal, item.getId())));

		int start = (int)pageable.getOffset();
		int end = (start + pageable.getPageSize()) > tempList2.size() ? tempList2.size() : (start + pageable.getPageSize());
		List<SolrSearchEntity> list = tempList2.subList(start, end);
		Page<SolrSearchEntity> page = new PageImpl<SolrSearchEntity>(list,pageable,tempList2.size());
		
		return page;

	}

	@RequestMapping("/solrSearchEntity/getAllShortlisted")
	public Page<SolrSearchEntity> getAllShortlisted(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
		List<SolrSearchEntity> tempList = this.solrSearchEntityRepository.findAllDocs();
		List<SolrSearchEntity> tempList2 = new ArrayList<SolrSearchEntity>();
		for(int i=0 ; i<tempList.size();i++) {
			if(this.shortlistService.isShortlisted(userPrincipal, tempList.get(i).getId())) {
				tempList2.add(tempList.get(i));
			}
		}
				
		tempList2.stream()
				.forEach((item) -> item.setIsViewed(this.viewService.isViewed(userPrincipal, item.getId())));
				
		tempList2.stream()
				.forEach((item2) -> item2.setIsShortlisted(this.shortlistService.isShortlisted(userPrincipal, item2.getId())));
		
		tempList2.stream()
				.forEach((item) -> item.setIsViewedMe(this.viewService.isViewedMe(userPrincipal, item.getId())));

		int start = (int)pageable.getOffset();
		int end = (start + pageable.getPageSize()) > tempList2.size() ? tempList2.size() : (start + pageable.getPageSize());
		List<SolrSearchEntity> list = tempList2.subList(start, end);
		Page<SolrSearchEntity> page = new PageImpl<SolrSearchEntity>(list,pageable,tempList2.size());
		
		return page;

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
	public List<SolrSearchEntity> getImageExistssolrSearchEntity(@CurrentUser UserPrincipal userPrincipal, Pageable pageable) {
		List<SolrSearchEntity> tempList= solrSearchEntityRepository.findByImageUrlIsNotNullOrderByIdDesc(pageable);
		tempList.stream()
		.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
		tempList.stream()
		.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));		
		return tempList;
	}
	
	@PostMapping("/solrSearchEntity/findByMaritalStatusIn")
	public Page<SolrSearchEntity> findByMaritalStatusIn(@CurrentUser UserPrincipal userPrincipal,@RequestBody Wrapper<MatrimonyCollection> wrappedResources,Pageable pageable) {
	  List<MatrimonyCollection> resources = wrappedResources.getData();
	  Collection<String> passValues = new HashSet<String>();
	  for(MatrimonyCollection m : resources) {
		  passValues.add(m.getMaritalStatus());
	  }
	  Page<SolrSearchEntity> result = solrSearchEntityRepository.findByMaritalStatusIn(passValues,pageable);
	  result.stream()
		.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
		
	  result.stream()
		.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));

	  return result;
	  
	}
	
	@PostMapping("/solrSearchEntity/findByMotherToungeIn")
	public Page<SolrSearchEntity> findByMotherToungeIn(@CurrentUser UserPrincipal userPrincipal,@RequestBody Wrapper<MotherToungeCollection> wrappedResources,Pageable pageable) {
	  List<MotherToungeCollection> resources = wrappedResources.getData();
	  Collection<String> passValuesMotherTounge = new HashSet<String>();
	  for(MotherToungeCollection mT : resources) {
		  passValuesMotherTounge.add(mT.getMotherTounge());
	  }
	  Page<SolrSearchEntity> result = solrSearchEntityRepository.findByMotherToungeIn(passValuesMotherTounge,pageable);
	  result.stream()
		.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
		
	  result.stream()
		.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));

	  return result;
	}
	
	@PostMapping("/solrSearchEntity/findByEducationIn")
	public Page<SolrSearchEntity> findByEducationIn(@CurrentUser UserPrincipal userPrincipal,@RequestBody Wrapper<EducationCollection> wrappedResources,Pageable pageable) {
	  List<EducationCollection> resources = wrappedResources.getData();
	  Collection<String> educations = new HashSet<String>();
	  for(EducationCollection m : resources) {
		  educations.add(m.getEducation());
	  }
	  Page<SolrSearchEntity> result = solrSearchEntityRepository.findByEducationIn(educations,pageable);
	  result.stream()
		.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
		
	  result.stream()
		.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));

	  return result;
	}
	
	@PostMapping("/solrSearchEntity/findByOccupationIn")
	public Page<SolrSearchEntity> findByOccupationIn(@CurrentUser UserPrincipal userPrincipal,@RequestBody Wrapper<OccupationCollection> wrappedResources,Pageable pageable) {
	  List<OccupationCollection> resources = wrappedResources.getData();
	  Collection<String> occupations = new HashSet<String>();
	  for(OccupationCollection m : resources) {
		  occupations.add(m.getOccupation());
	  }
	  Page<SolrSearchEntity> result = solrSearchEntityRepository.findByOccupationIn(occupations,pageable);
	  result.stream()
		.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
		
	  result.stream()
		.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));

	  return result;
	}
	
	@PostMapping("/solrSearchEntity/findByPhysicalStatusIn")
	public Page<SolrSearchEntity> findByPhysicalStatusIn(@CurrentUser UserPrincipal userPrincipal,@RequestBody Wrapper<PhysicalStatusCollection> wrappedResources,Pageable pageable) {
	  List<PhysicalStatusCollection> resources = wrappedResources.getData();
	  Collection<String> physicalStatuses = new HashSet<String>();
	  for(PhysicalStatusCollection p : resources) {
		  physicalStatuses.add(p.getPhysicalStatus());
	  }
	  Page<SolrSearchEntity> result = solrSearchEntityRepository.findByPhysicalStatusIn(physicalStatuses,pageable);
	  result.stream()
		.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
		
	  result.stream()
		.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));

	  return result;
	}
	
	@PostMapping("/solrSearchEntity/findByDietIn")
	public Page<SolrSearchEntity> findByDietStatusIn(@CurrentUser UserPrincipal userPrincipal,@RequestBody Wrapper<DietCollection> wrappedResources,Pageable pageable) {
	  List<DietCollection> resources = wrappedResources.getData();
	  Collection<String> dietes = new HashSet<String>();
	  for(DietCollection d : resources) {
		  dietes.add(d.getDiet());
	  }
	  Page<SolrSearchEntity> result = solrSearchEntityRepository.findByDietIn(dietes,pageable);
	  result.stream()
		.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
		
	  result.stream()
		.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));

	  return result;
	}
	
	@PostMapping("/solrSearchEntity/findBySmokeIn")
	public Page<SolrSearchEntity> findByDietSmokeIn(@CurrentUser UserPrincipal userPrincipal,@RequestBody Wrapper<SmokeCollection> wrappedResources,Pageable pageable) {
	  List<SmokeCollection> resources = wrappedResources.getData();
	  Collection<String> smokers = new HashSet<String>();
	  for(SmokeCollection smoker : resources) {
		  smokers.add(smoker.getSmoke());
	  }
	  Page<SolrSearchEntity> result = solrSearchEntityRepository.findBySmokeIn(smokers,pageable);
	  result.stream()
		.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
		
	  result.stream()
		.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));

	  return result;
	}
	
	@PostMapping("/solrSearchEntity/findByDrinkIn")
	public Page<SolrSearchEntity> findByDietDrinkIn(@CurrentUser UserPrincipal userPrincipal,@RequestBody Wrapper<DrinkCollection> wrappedResources,Pageable pageable) {
	  List<DrinkCollection> resources = wrappedResources.getData();
	  Collection<String> drinkers = new HashSet<String>();
	  for(DrinkCollection drinker : resources) {
		  drinkers.add(drinker.getDrink());
	  }
	  Page<SolrSearchEntity> result = solrSearchEntityRepository.findByDrinkIn(drinkers,pageable);
	  result.stream()
		.forEach((item) -> item.setIsViewed(viewService.isViewed(userPrincipal, item.getId())));
		
	  result.stream()
		.forEach((item2) -> item2.setIsShortlisted(shortlistService.isShortlisted(userPrincipal, item2.getId())));

	  return result;
	}
	
	public static class MatrimonyCollection{
		String maritalStatus;

		public MatrimonyCollection() {
			super();
		}

		public MatrimonyCollection(String maritalStatus) {
			super();
			this.maritalStatus = maritalStatus;
		}

		public String getMaritalStatus() {
			return maritalStatus;
		}

		public void setMaritalStatus(String maritalStatus) {
			this.maritalStatus = maritalStatus;
		}
		
	}
	
	public static class MotherToungeCollection{
		String motherTounge;

		public MotherToungeCollection() {
			super();
		}

		public MotherToungeCollection(String motherTounge) {
			super();
			this.motherTounge = motherTounge;
		}

		public String getMotherTounge() {
			return motherTounge;
		}

		public void setMotherTounge(String motherTounge) {
			this.motherTounge = motherTounge;
		}
		
	}
	
	public static class EducationCollection{
		String education;

		public EducationCollection() {
			super();
		}

		public EducationCollection(String education) {
			super();
			this.education = education;
		}

		public String getEducation() {
			return education;
		}

		public void setEducation(String education) {
			this.education = education;
		}
		
	}
	
	public static class OccupationCollection{
		String occupation;

		public OccupationCollection() {
			super();
		}

		public OccupationCollection(String occupation) {
			super();
			this.occupation = occupation;
		}

		public String getOccupation() {
			return occupation;
		}

		public void setOccupation(String occupation) {
			this.occupation = occupation;
		}
		
	}
	
	public static class PhysicalStatusCollection{
		String physicalStatus;

		public PhysicalStatusCollection() {
			super();
		}

		public PhysicalStatusCollection(String physicalStatus) {
			super();
			this.physicalStatus = physicalStatus;
		}

		public String getPhysicalStatus() {
			return physicalStatus;
		}

		public void setPhysicalStatus(String physicalStatus) {
			this.physicalStatus = physicalStatus;
		}
		
	}

	public static class DietCollection{
		String diet;

		public DietCollection() {
			super();
		}

		public DietCollection(String diet) {
			super();
			this.diet = diet;
		}

		public String getDiet() {
			return diet;
		}

		public void setDiet(String diet) {
			this.diet = diet;
		}
		
	}
	
	public static class SmokeCollection{
		String smoke;

		public SmokeCollection() {
			super();
		}

		public SmokeCollection(String smoke) {
			super();
			this.smoke = smoke;
		}

		public String getSmoke() {
			return smoke;
		}

		public void setSmoke(String smoke) {
			this.smoke = smoke;
		}
		
	}
	
	public static class DrinkCollection{
		String drink;

		public DrinkCollection() {
			super();
		}

		public DrinkCollection(String drink) {
			super();
			this.drink = drink;
		}

		public String getDrink() {
			return drink;
		}

		public void setDrink(String drink) {
			this.drink = drink;
		}
		
	}
	
}