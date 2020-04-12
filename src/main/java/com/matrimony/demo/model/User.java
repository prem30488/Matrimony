package com.matrimony.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.matrimony.demo.model.audit.DateAudit;

import java.time.Instant;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.NaturalId;
import org.springframework.validation.annotation.Validated;

@Entity
@Table(name = "users", uniqueConstraints = { @UniqueConstraint(columnNames = { "username" }),
		@UniqueConstraint(columnNames = "email"), @UniqueConstraint(columnNames = "phoneNumber") })
public class User extends DateAudit implements Comparable{

	@Id
	@NaturalId
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Column(nullable = false)
	private String username;

	@NotBlank
	@Column(nullable = false)
	private String name;

	// @NaturalId
	@NotBlank
	@Size(max = 40)
	@Email
	@Column(nullable = false)
	private String email;

	private String imageUrl;

	@Column(nullable = false)
	private Boolean emailVerified = false;

	// @NotBlank(message = "Password is mandatory")
	@Size(min = 6, max = 100)
	@JsonIgnore
	@Valid
	private String password;

	@NotNull
	@Enumerated(EnumType.STRING)
	private AuthProvider provider;

	private String providerId;

	@NotBlank
	@Column(nullable = false)
	@Size(min = 4, max = 10)
	private String sex;

	@NotBlank
	@Column(nullable = false)
	@Size(min = 10, max = 40)
	private String phoneNumber;

	@ManyToMany(fetch = FetchType.EAGER)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	public User() {
		super();
	}

	public User(String name, String username, String email, String password, String sex, String phoneNumber) {
		this.name = name;
		this.username = username;
		this.email = email;
		this.password = password;
		this.sex = sex;
		this.phoneNumber = phoneNumber;
	}

	// Getters and Setters (Omitted for brevity)

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public AuthProvider getProvider() {
		return provider;
	}

	public void setProvider(AuthProvider provider) {
		this.provider = provider;
	}

	public String getProviderId() {
		return providerId;
	}

	public void setProviderId(String providerId) {
		this.providerId = providerId;
	}

	public String getImageUrl() {
		return imageUrl;
	}

	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	public Boolean getEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(Boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private Profile profile;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private AstroProfile astroProfile;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private CareerProfile careerProfile;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private FamilyProfile familyProfile;

	@OneToOne(cascade = CascadeType.ALL, mappedBy = "user", fetch = FetchType.LAZY)
	private PartnerPreference partnerPreference;

	public Profile getProfile() {
		return profile;
	}

	public void setProfile(Profile profile) {
		this.profile = profile;
	}

	public AstroProfile getAstroProfile() {
		return astroProfile;
	}

	public void setAstroProfile(AstroProfile astroProfile) {
		this.astroProfile = astroProfile;
	}

	public CareerProfile getCareerProfile() {
		return careerProfile;
	}

	public void setCareerProfile(CareerProfile careerProfile) {
		this.careerProfile = careerProfile;
	}

	public FamilyProfile getFamilyProfile() {
		return familyProfile;
	}

	public void setFamilyProfile(FamilyProfile familyProfile) {
		this.familyProfile = familyProfile;
	}

	public PartnerPreference getPartnerPreference() {
		return partnerPreference;
	}

	public void setPartnerPreference(PartnerPreference partnerPreference) {
		this.partnerPreference = partnerPreference;
	}

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_shortlisted", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "shortlisted_id"))
	@JsonIgnore
	private List<User> shortlisted;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_shortlisted", joinColumns = @JoinColumn(name = "shortlisted_id"), inverseJoinColumns = @JoinColumn(name = "user_id"))
	@JsonIgnore
	private List<User> shortlistedOf;

	public List<User> getShortlisted() {
		return shortlisted;
	}

	public void setShortlisted(List<User> shortlisted) {
		this.shortlisted = shortlisted;
	}

	public List<User> getShortlistedOf() {
		return shortlistedOf;
	}

	public void setShortlistedOf(List<User> shortlistedOf) {
		this.shortlistedOf = shortlistedOf;
	}

	@Override
	public boolean equals(Object obj) {
		return ((User) obj).getId().equals(getId());
	}

	@Override
	public int compareTo(Object o) {
		User u = (User) o;
		return getId().compareTo(u.getId());
	}

}