package models;

import java.util.Date;

import javax.persistence.*;

import io.ebean.Finder;
import io.ebean.Model;
import play.data.validation.Constraints;

@Entity
public class Domain  extends Model{
	
	@Id
	@GeneratedValue
	@Column(name="domain_id")
	private int domain_id;
	
	
	@Constraints.Email
	@Column(name="registered_email")
	private String registered_email;
	
	@Column(name="domain_name",nullable = false)
	private String domain_name;
	
	@Column(name="country")
	private String country;
	
	
	@Column(name="created_date",nullable = false)
	private Date created_date;
	
	@Column(name="website_url",nullable = false)
	private String website_url ;
	
	@Column(name="updated_date",nullable = false)
	private Date updated_date;
	
	@Column(name="expiry_date",nullable = false)
	private Date expiry_date;
	
	@Column(name="registrar_name",nullable = false)
	private String registrar_name;
	
	@Transient
	private int year = 0;
	
	@Transient
	private int month = 0;
	
	@Transient
	private int day =0 ;
	
	public int getYear() {
		return year;
	}
	
	public void setYear(int year) {
		this.year = year;
	}
	
	public int getMonth() {
		return month;
	}
	@Transient
	public void setMonth(int month) {
		this.month = month;
	}
	@Transient
	public int getDay() {
		return day;
	}
	@Transient
	public void setDay(int day) {
		this.day = day;
	}

	public String getWebsite_url() {
		return website_url;
	}
	
	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}
	
	public String getCountry() {
		return country;
	}
	
	public void setCountry(String country) {
		this.country = country;
	}
	
	public Date getCreated_date() {
		return created_date;
	}
	
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}
	
	public Date getUpdated_date() {
		return updated_date;
	}
	
	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}
	
	public Date getExpiry_date() {
		return expiry_date;
	}
	
	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}
	
	
	public String getRegistrar_name() {
		return registrar_name;
	}
	
	public void setRegistrar_name(String registrar_name) {
		this.registrar_name = registrar_name;
	}
	
	public int getDomain_id() {
		return domain_id;
	}
	
	public void setDomain_id(int domain_id) {
		this.domain_id = domain_id;
	}
	
	public String getRegistered_email() {
		return registered_email;
	}
	
	public void setRegistered_email(String registered_email) {
		this.registered_email = registered_email;
	}
	
	public String getDomain_name() {
		return domain_name;
	}
	
	public void setDomain_name(String domain_name) {
		this.domain_name = domain_name;
	}
	
	public static final Finder<Long, Domain> find = new Finder<>(Domain.class);
}


