package com.matrimony.demo.solrmodel;

import org.springframework.data.annotation.Id;
import org.springframework.data.solr.core.mapping.Indexed;
import org.springframework.data.solr.core.mapping.SolrDocument;

import com.matrimony.demo.model.audit.DateAudit;


@SolrDocument(collection = "UserProfile", solrCoreName = "UserProfile")
public class SolrSearchEntity {

	@Id
	@Indexed(name = "id", type = "String")
	private Long id;
	
	@Indexed(name = "age", type = "string")
	private String age;
	
	@Indexed(name = "marital_status", type = "string")
	private String maritalStatus;
	
	@Indexed(name = "body_type", type = "string")
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
	
	@Indexed(name = "mother_tounge", type = "string")
	private String motherTounge;
	
	@Indexed(name = "education", type = "string")
	private String education;
	
	@Indexed(name = "occupation", type = "string")
	private String occupation;
	
	@Indexed(name = "location", type = "string")
	private String locationOfHome;
	
	@Indexed(name = "state", type = "string")
	private String state;
	
	@Indexed(name = "residency_status", type = "string")
	private String residencyStatus;
	
	@Indexed(name = "sub_caste", type = "string")
	private String subCaste;
	
	@Indexed(name = "date_of_birth", type = "string")
	private String dateOfBirth;

	@Indexed(name = "time_of_birth", type = "string")
	private String timeOfBirth;

	@Indexed(name = "about", type = "string")
	private String about;

	@Indexed(name = "created_at", type = "string")
	private String createdAt;
	
	@Indexed(name = "updated_at", type = "string")
	private String updatedAt;
	
	@Indexed(name = "education_details", type = "string")
	private String educationDetails;
	
	@Indexed(name = "raasi", type = "string")
	private String raasi;
	
	@Indexed(name = "email", type = "string")
	private String email;
	
	@Indexed(name = "annual_income", type = "string")
	private String annualIncome;
	
	@Indexed(name = "sex", type = "string")
	private String sex;
	
	@Indexed(name = "smoke", type = "string")
	private String smoke;
	
	@Indexed(name = "drink", type = "string")
	private String drink;
	
	@Indexed(name = "physical_status", type = "string")
	private String physicalStatus;
	
	@Indexed(name = "num_of_brother", type = "string")
	private String noOfBrother;
	
	@Indexed(name = "num_of_sister", type = "string")
	private String noOfSister;
	
	@Indexed(name = "weight", type = "string")
	private String weight;
	
	@Indexed(name = "occupation_father", type = "string")
	private String occupationFather;
	
	@Indexed(name = "occupation_mother", type = "string")
	private String occupationMother;

	@Indexed(name = "blood_group", type = "string")
	private String bloodGroup;
	
	@Indexed(name = "phone_number", type = "string")
	private String phoneNumber;

	@Indexed(name = "username", type = "string")
	private String username;
	
	@Indexed(name = "profile_created_by", type = "string")
	private String profileCreatedBy;
	
	private Boolean isShortlisted;
	
	private String name;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
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

	public String getSubCaste() {
		return subCaste;
	}

	public void setSubCaste(String subCaste) {
		this.subCaste = subCaste;
	}

	public String getDateOfBirth() {
		return dateOfBirth;
	}

	public void setDateOfBirth(String dateOfBirth) {
		this.dateOfBirth = dateOfBirth;
	}

	public String getTimeOfBirth() {
		return timeOfBirth;
	}

	public void setTimeOfBirth(String timeOfBirth) {
		this.timeOfBirth = timeOfBirth;
	}

	public String getAbout() {
		return about;
	}

	public void setAbout(String about) {
		this.about = about;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(String updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getEducationDetails() {
		return educationDetails;
	}

	public void setEducationDetails(String educationDetails) {
		this.educationDetails = educationDetails;
	}

	public String getRaasi() {
		return raasi;
	}

	public void setRaasi(String raasi) {
		this.raasi = raasi;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAnnualIncome() {
		return annualIncome;
	}

	public void setAnnualIncome(String annualIncome) {
		this.annualIncome = annualIncome;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getSmoke() {
		return smoke;
	}

	public void setSmoke(String smoke) {
		this.smoke = smoke;
	}

	public String getDrink() {
		return drink;
	}

	public void setDrink(String drink) {
		this.drink = drink;
	}

	public String getPhysicalStatus() {
		return physicalStatus;
	}

	public void setPhysicalStatus(String physicalStatus) {
		this.physicalStatus = physicalStatus;
	}

	public String getNoOfBrother() {
		return noOfBrother;
	}

	public void setNoOfBrother(String noOfBrother) {
		this.noOfBrother = noOfBrother;
	}

	public String getNoOfSister() {
		return noOfSister;
	}

	public void setNoOfSister(String noOfSister) {
		this.noOfSister = noOfSister;
	}

	public String getWeight() {
		return weight;
	}

	public void setWeight(String weight) {
		this.weight = weight;
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

	public String getBloodGroup() {
		return bloodGroup;
	}

	public void setBloodGroup(String bloodGroup) {
		this.bloodGroup = bloodGroup;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProfileCreatedBy() {
		return profileCreatedBy;
	}

	public void setProfileCreatedBy(String profileCreatedBy) {
		this.profileCreatedBy = profileCreatedBy;
	}

	public Boolean getIsShortlisted() {
		return isShortlisted;
	}

	public void setIsShortlisted(Boolean isShortlisted) {
		this.isShortlisted = isShortlisted;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	
	
}
