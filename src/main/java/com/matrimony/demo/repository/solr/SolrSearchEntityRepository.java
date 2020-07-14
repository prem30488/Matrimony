package com.matrimony.demo.repository.solr;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.solr.core.query.Query.Operator;
import org.springframework.data.solr.core.query.result.FacetPage;
import org.springframework.data.solr.core.query.result.HighlightPage;
import org.springframework.data.solr.repository.Facet;
import org.springframework.data.solr.repository.Highlight;
import org.springframework.data.solr.repository.Query;
import org.springframework.data.solr.repository.SolrCrudRepository;
import org.springframework.stereotype.Repository;

import com.matrimony.demo.model.User;
import com.matrimony.demo.solrmodel.SolrSearchEntity;

@Repository
public interface SolrSearchEntityRepository extends SolrCrudRepository<SolrSearchEntity, String> {
	Optional<SolrSearchEntity> findById(String id);

	@Query("age:*?0*")
	Page<SolrSearchEntity> findBySolrSearchEntityAge(String searchTerm, Pageable pageable);

	@Query("id:*?0* OR age:*?0* OR maritalStatus:*?0* OR email:*?0*")
	Page<SolrSearchEntity> findByCustomerQuery(String searchTerm, Pageable pageable);

	@Highlight(prefix = "<b>", postfix = "</b>")
	@Query(fields = { "id", "age", "maritalStatus", "bodyType", "complexion", "height", "diet", "manglik", "mangal",
			"religion", "caste", "motherTounge", "education", "occupation", "locationOfHome", "state",
			"residencyStatus" }, defaultOperator = Operator.AND)
	HighlightPage<SolrSearchEntity> findByStateIn(Collection<String> states, Pageable page);

	
	Page<SolrSearchEntity> findByMaritalStatusStartingWith(String maritalStatuses, Pageable pagebale);
	
	
//	@Query(fields = { "id", "age", "maritalStatus", "bodyType", "complexion", "height", "diet", "manglik", "mangal",
//			"religion", "caste", "motherTounge", "education", "occupation", "locationOfHome", "state",
//			"residencyStatus" },defaultOperator =  Operator.NONE)
//			"where u.created_date between \':weekStartDate\' and \'weekEndDate\' " + 
//			"order by date_trunc('week', u.created_date) desc ")
	//@Query("createdAt:[?0 TO ?1]")
    List<SolrSearchEntity> findByCreatedAtBetweenOrderByCreatedAtDesc(Date weekStartDate, Date weekEndDate);
    
    List<SolrSearchEntity> findByImageUrlIsNotNullOrderByIdDesc(Pageable pagebale);

	Page<SolrSearchEntity> findByMaritalStatusIn(Collection<String> maritalStatuses, Pageable pagebale);
	
	Page<SolrSearchEntity> findByMotherToungeIn(Collection<String> motherToungs, Pageable pagebale);

	Page<SolrSearchEntity> findByEducationIn(Collection<String> educations, Pageable pageable);

	Page<SolrSearchEntity> findByOccupationIn(Collection<String> occupations, Pageable pageable);

	Page<SolrSearchEntity> findByPhysicalStatusIn(Collection<String> physicalStatuses, Pageable pageable);

	Page<SolrSearchEntity> findByDietIn(Collection<String> dietes, Pageable pageable);

	Page<SolrSearchEntity> findBySmokeIn(Collection<String> smokers, Pageable pageable);

	Page<SolrSearchEntity> findByDrinkIn(Collection<String> drinkers, Pageable pageable);

}
