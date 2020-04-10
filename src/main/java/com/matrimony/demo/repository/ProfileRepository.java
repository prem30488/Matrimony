package com.matrimony.demo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.matrimony.demo.model.Profile;

@Repository
public interface ProfileRepository extends JpaRepository<Profile,Long>{

}
