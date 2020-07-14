package com.matrimony.demo.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.matrimony.demo.model.CareerProfile;

@Repository
public interface CareerProfileRepository extends JpaRepository<CareerProfile,Long>{

	
	@Query("SELECT distinct occupation , count(*) as Count" + 
    		"  FROM CareerProfile WHERE occupation IS NOT NULL group by occupation" + 
    		"")
	public List<Object> getDistinctOccupation();

}
