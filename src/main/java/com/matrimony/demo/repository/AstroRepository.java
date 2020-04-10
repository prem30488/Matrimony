package com.matrimony.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matrimony.demo.model.AstroProfile;

@Repository
public interface AstroRepository extends JpaRepository<AstroProfile,Long>{

}
