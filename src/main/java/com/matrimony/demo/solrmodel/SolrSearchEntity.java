package com.matrimony.demo.solrmodel;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.matrimony.demo.model.audit.DateAudit;


@SolrDocument(solrCoreName = "matrimony")
public class SolrSearchEntity {

	@Id
	@Indexed(name = "id", type = "String")
	private String id;
	
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
	private String manglik;
	
	@Indexed(name = "mangal", type = "string")
	private String mangal;
	
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	

	public String getManglik() {
		return manglik;
	}

	public void setManglik(String manglik) {
		this.manglik = manglik;
	}

	public String getMangal() {
		return mangal;
	}

	public void setMangal(String mangal) {
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
