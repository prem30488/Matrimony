package com.matrimony.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matrimony.demo.model.CareerProfile;

@Repository
public interface CareerProfileRepository extends JpaRepository<CareerProfile,Long>{

}
