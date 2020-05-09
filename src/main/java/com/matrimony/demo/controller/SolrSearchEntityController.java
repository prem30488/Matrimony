package com.matrimony.demo.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
import com.matrimony.demo.solrmodel.SolrSearchEntity;

@RestController
@CrossOrigin("*")
@RequestMapping("/api")
public class SolrSearchEntityController {
	
	@Autowired
	SolrSearchEntityRepository solrSearchEntityRepository;
	
	@PostMapping("/solrSearchEntity")
	public String createsolrSearchEntity(@RequestBody SolrSearchEntity solrSearchEntity){
	String description = "SolrSearchEntity Created";
	solrSearchEntityRepository.save(solrSearchEntity);
	return description;
	}

	@GetMapping("/solrSearchEntity/{id}")
	public SolrSearchEntity readSolrSearchEntity(@PathVariable String id){
	return solrSearchEntityRepository.findById(id).get();
	}

	@PutMapping("/solrSearchEntity")
	public String updateSolrSearchEntity(@RequestBody SolrSearchEntity solrSearchEntity){
	String description = "SolrSearchEntity Updated";
	solrSearchEntityRepository.save(solrSearchEntity);
	return description;
	}

	@DeleteMapping("/solrSearchEntity/{id}")
	public String deleteSolrSearchEntity(@PathVariable String id){
	String description = "SolrSearchEntity Deleted";
	solrSearchEntityRepository.delete(solrSearchEntityRepository.findById(id).get());
	return description;
	}

	@GetMapping("/solrSearchEntity/age/{solrSearchEntityState}/{page}")
	public List<SolrSearchEntity> findSolrSearchEntity(@PathVariable String solrSearchEntityAge, @PathVariable int page){
	return solrSearchEntityRepository.findBySolrSearchEntityAge(solrSearchEntityAge, PageRequest.of(page, 2)).getContent();
	}

	@GetMapping("/solrSearchEntity/search/{searchTerm}/{page}")
	public List<SolrSearchEntity> findSolrSearchEntityBySearchTerm(@PathVariable String searchTerm, @PathVariable int page){
	return solrSearchEntityRepository.findByCustomerQuery(searchTerm, PageRequest.of(page, 5)).getContent();
	}
	
	@RequestMapping("/solrSearchEntity/getAll")
	 public Page<SolrSearchEntity> getAllDocs(Pageable pageable) {
	       return this.solrSearchEntityRepository.findAll(pageable);
	 
	 }
}
