package com.matrimony.demo.repository.solr;

import java.util.Collection;
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

import com.matrimony.demo.solrmodel.SolrSearchEntity;

@Repository
public interface SolrSearchEntityRepository extends SolrCrudRepository<SolrSearchEntity, String> {
	Optional<SolrSearchEntity> findById(String id);

	@Query("age:*?0*")
	Page<SolrSearchEntity> findBySolrSearchEntityAge(String searchTerm, Pageable pageable);

	@Query("id:*?0* OR age:*?0* OR maritalStatus:*?0*")
	Page<SolrSearchEntity> findByCustomerQuery(String searchTerm, Pageable pageable);

	@Highlight(prefix = "<b>", postfix = "</b>")
	@Query(fields = { "id", "age", "maritalStatus", "bodyType", "complexion", "height", "diet", "manglik", "mangal",
			"religion", "caste", "motherTounge", "education", "occupation", "locationOfHome", "state",
			"residencyStatus" }, defaultOperator = Operator.AND)
	HighlightPage<SolrSearchEntity> findByStateIn(Collection<String> states, Pageable page);

	@Facet(fields = { "maritalStatus" })
	FacetPage<SolrSearchEntity> findByMaritalStatusStartsWith(Collection<String> maritalStatuses, Pageable pagebale);
}
