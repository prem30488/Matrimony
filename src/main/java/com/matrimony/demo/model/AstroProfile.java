package com.matrimony.demo.model;

import java.time.Instant;

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
@Table(name = "astroprofile", uniqueConstraints = {
		@UniqueConstraint(columnNames = {
	            "id"
	        })
})
public class AstroProfile extends DateAudit {

	@Id
    @NaturalId
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
    private Long id;
	
	private Instant dateOfBirth;
	
	private Instant timeOfBirth;
	
	private String religion;
	
	private String caste;
	
	private String subCaste;
	
	private String Raasi;
	
	private boolean manglik;
	
	private boolean mangal;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@JsonIgnore
	private User user;

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Instant getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(Instant dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public Instant getTimeOfBirth() {
		return timeOfBirth;
	}

	public void setTimeOfBirth(Instant timeOfBirth) {
		this.timeOfBirth = timeOfBirth;
	}

	public String getReligion() {
		return religion;
	}

	public void setReligion(String religion) {
		this.religion = religion;
	}

	public String getCaste() {
		return caste;
	}

	public void setCaste(String caste) {
		this.caste = caste;
	}

	public String getSubCaste() {
		return subCaste;
	}

	public void setSubCaste(String subCaste) {
		this.subCaste = subCaste;
	}

	public String getRaasi() {
		return Raasi;
	}

	public void setRaasi(String raasi) {
		Raasi = raasi;
	}

	public boolean isManglik() {
		return manglik;
	}

	public void setManglik(boolean manglik) {
		this.manglik = manglik;
	}

	public boolean isMangal() {
		return mangal;
	}

	public void setMangal(boolean mangal) {
		this.mangal = mangal;
	}
	
	
}
