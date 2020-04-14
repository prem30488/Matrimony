package com.matrimony.demo.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.matrimony.demo.model.PartnerPreference;

	@Repository
	public interface PartnerPreferenceSolrRepository extends SolrCrudRepository<PartnerPreference,Integer>{
		public List<PartnerPreference> findByAge(String age);
		 
	    @Query("id:*?0* OR age:*?0*")
	    public Page<PartnerPreference> findByCustomQuery(String searchTerm, Pageable pageable);
	 
	    @Query(name = "PartnerPreference.findByAgedQuery")
	    public Page<PartnerPreference> findByNamedQuery(String searchTerm, Pageable pageable);
}
