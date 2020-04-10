package com.matrimony.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matrimony.demo.model.PartnerPreference;

@Repository
public interface PartnerPreferenceRepository extends JpaRepository<PartnerPreference,Long>{

}
