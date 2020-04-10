package com.matrimony.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matrimony.demo.model.FamilyProfile;

@Repository
public interface FamilyProfileRepository extends JpaRepository<FamilyProfile,Long>{

}
