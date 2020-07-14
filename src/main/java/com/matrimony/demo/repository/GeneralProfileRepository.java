package com.matrimony.demo.repository;

import java.util.List;
import java.util.Map;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.matrimony.demo.model.Profile;
import com.matrimony.demo.payload.MaritalStatusPayload;

@Repository
public interface GeneralProfileRepository extends JpaRepository<Profile,Long>{

	@Query("SELECT distinct maritalStatus , count(*) as Count" + 
    		"  FROM Profile WHERE maritalStatus IS NOT NULL group by maritalStatus" + 
    		"")
	public List<Object> getDistinctMaritalStatus();

	@Query("SELECT distinct motherToung , count(*) as Count" + 
    		"  FROM Profile WHERE motherToung IS NOT NULL group by motherToung" + 
    		"")
	public List<Object> getDistinctMotherToung();

	@Query("SELECT distinct education , count(*) as Count" + 
    		"  FROM Profile WHERE education IS NOT NULL group by education" + 
    		"")
	public List<Object> getDistinctEducation();

	@Query("SELECT distinct physicalStatus , count(*) as Count" + 
    		"  FROM Profile WHERE physicalStatus IS NOT NULL group by physicalStatus" + 
    		"")
	public List<Object> getDistinctPhysicalStatus();

	@Query("SELECT distinct diet , count(*) as Count" + 
    		"  FROM Profile WHERE diet IS NOT NULL group by diet" + 
    		"")
	public List<Object> getDistinctDiet();

	@Query("SELECT distinct smoke , count(*) as Count" + 
    		"  FROM Profile group by smoke" + 
    		"")
	public List<Object> getDistinctSmoke();

	@Query("SELECT distinct drink , count(*) as Count" + 
    		"  FROM Profile group by drink" + 
    		"")
	public List<Object> getDistinctDrink();
	
}
