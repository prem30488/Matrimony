package com.matrimony.demo.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.NaturalId;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matrimony.demo.model.audit.DateAudit;

@Entity
@Table(name = "familyprofile", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
public class FamilyProfile extends DateAudit {

	@Id
	@NaturalId
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@JsonIgnore
	private User user;
	
	private String occupationFather;
	
	private String occupationMother;
	
	private int numOfBrother;
	
	private int numOfSister;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getOccupationFather() {
		return occupationFather;
	}

	public void setOccupationFather(String occupationFather) {
		this.occupationFather = occupationFather;
	}

	public String getOccupationMother() {
		return occupationMother;
	}

	public void setOccupationMother(String occupationMother) {
		this.occupationMother = occupationMother;
	}

	public int getNumOfBrother() {
		return numOfBrother;
	}

	public void setNumOfBrother(int numOfBrother) {
		this.numOfBrother = numOfBrother;
	}

	public int getNumOfSister() {
		return numOfSister;
	}

	public void setNumOfSister(int numOfSister) {
		this.numOfSister = numOfSister;
	}
	
	
}
