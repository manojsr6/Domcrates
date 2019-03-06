package models;

import javax.persistence.*;
import javax.validation.Constraint;


import play.data.validation.*;

import java.util.*;

import io.ebean.*;

@Entity
public class User extends Model {
	
	@Id
	@GeneratedValue
	@Column(unique = true)
	private  int user_id;
	
	@Column(nullable = false)
	private  String name;
	
	@Column(unique = true)
	@Constraints.Email
	@Constraints.Required
	@OneToMany
	private  String primary_email;
	
	@Constraints.Required
	private  String password;
	
	@Column(nullable = false)
	private  Boolean active= false;
	
	@Column(nullable = false)
	private  Boolean verified= false;
	
	@Column(nullable = true)
	private  String forgotPasswordToken;
	
	@Column(nullable = true)
	private  String emailVerificationToken;
	
	
	public String getEmailVerificationToken() {
		return emailVerificationToken;
	}

	public void setEmailVerificationToken(String emailVerificationToken) {
		this.emailVerificationToken = emailVerificationToken;
	}

	public 	int getUser_id() {
		return user_id;
	}
	
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getPrimary_email() {
		return primary_email;
	}
	
	public void setPrimary_email(String primary_email) {
		this.primary_email = primary_email;
	}
	
	public String getPassword() {
		return password;
	}
	
	public void setPassword(String password) {
		this.password = password;
	}
	
	public Boolean getActive() {
		return active;
	}
	
	public void setActive(Boolean active) {
		this.active = active;
	}
	
	public Boolean getVerified() {
		return verified;
	}
	
	public void setVerified(Boolean verified) {
		this.verified = verified;
	}
	
	/**
	 * @return the forgotPasswordToken
	 */
	public String getForgotPasswordToken() {
		return forgotPasswordToken;
	}

	/**
	 * @param forgotPasswordToken the forgotPasswordToken to set
	 */
	public void setForgotPasswordToken(String forgotPasswordToken) {
		this.forgotPasswordToken = forgotPasswordToken;
	}

	public static final Finder<Long, User> find = new Finder<>(User.class);
}
