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
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matrimony.demo.model.audit.DateAudit;

@Entity
@Table(name = "partnerPreference", uniqueConstraints = { @UniqueConstraint(columnNames = { "id" }) })
@SolrDocument(solrCoreName = "matrimony")
public class PartnerPreference extends DateAudit {

	@Id
	@NaturalId
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@Indexed(name = "id", type = "int")
	private Long id;
	
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "id")
	@JsonIgnore
	private User user;
	
	@Indexed(name = "age", type = "string")
	private String age;
	
	@Indexed(name = "maritalStatus", type = "string")
	private String maritalStatus;
	
	@Indexed(name = "bodyType", type = "string")
	private String bodyType;
	
	@Indexed(name = "complexion", type = "string")
	private String complexion;
	
	@Indexed(name = "height", type = "string")
	private String height;
	
	@Indexed(name = "diet", type = "string")
	private String diet;
	
	@Indexed(name = "manglik", type = "string")
	private boolean manglik;
	
	@Indexed(name = "mangal", type = "string")
	private boolean mangal;
	
	@Indexed(name = "religion", type = "string")
	private String religion;
	
	@Indexed(name = "caste", type = "string")
	private String caste;
	
	@Indexed(name = "motherTounge", type = "string")
	private String motherTounge;
	
	@Indexed(name = "education", type = "string")
	private String education;
	
	@Indexed(name = "occupation", type = "string")
	private String occupation;
	
	@Indexed(name = "locationOfHome", type = "string")
	private String locationOfHome;
	
	@Indexed(name = "state", type = "string")
	private String state;
	
	@Indexed(name = "residencyStatus", type = "string")
	private String residencyStatus;

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

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getMaritalStatus() {
		return maritalStatus;
	}

	public void setMaritalStatus(String maritalStatus) {
		this.maritalStatus = maritalStatus;
	}

	public String getBodyType() {
		return bodyType;
	}

	public void setBodyType(String bodyType) {
		this.bodyType = bodyType;
	}

	public String getComplexion() {
		return complexion;
	}

	public void setComplexion(String complexion) {
		this.complexion = complexion;
	}

	public String getHeight() {
		return height;
	}

	public void setHeight(String height) {
		this.height = height;
	}

	public String getDiet() {
		return diet;
	}

	public void setDiet(String diet) {
		this.diet = diet;
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

	public String getMotherTounge() {
		return motherTounge;
	}

	public void setMotherTounge(String motherTounge) {
		this.motherTounge = motherTounge;
	}

	public String getEducation() {
		return education;
	}

	public void setEducation(String education) {
		this.education = education;
	}

	public String getOccupation() {
		return occupation;
	}

	public void setOccupation(String occupation) {
		this.occupation = occupation;
	}

	public String getLocationOfHome() {
		return locationOfHome;
	}

	public void setLocationOfHome(String locationOfHome) {
		this.locationOfHome = locationOfHome;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getResidencyStatus() {
		return residencyStatus;
	}

	public void setResidencyStatus(String residencyStatus) {
		this.residencyStatus = residencyStatus;
	}
	
	
}
