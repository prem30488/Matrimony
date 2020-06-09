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
	
}
