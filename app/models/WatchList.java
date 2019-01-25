package models;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import io.ebean.Finder;
import play.data.validation.Constraints;

@Entity
public class WatchList {
	
	@Id
	@GeneratedValue
	private int watch_id;
	
	
	private int user_id;
	
	@Column(nullable = false)
	private String domain_name;
	
	private String country;
	
	@Column(nullable = false)
	private Date created_date;
	
	@Column(nullable = false)
	private String website_url ;
	
	@Column(nullable = false)
	private Date updated_date;
	
	@Column(nullable = false)
	private Date expiry_date;
	
	@Column(nullable = false)
	private String registrar_name;
	
	@Transient
	private int year = 0;
	
	@Transient
	private int month = 0;
	
	@Transient
	private int day =0 ;

	/**
	 * @return the watch_id
	 */
	public int getWatch_id() {
		return watch_id;
	}

	/**
	 * @param watch_id the watch_id to set
	 */
	public void setWatch_id(int watch_id) {
		this.watch_id = watch_id;
	}

	/**
	 * @return the user_id
	 */
	public int getUser_id() {
		return user_id;
	}

	/**
	 * @param user_id the user_id to set
	 */
	public void setUser_id(int user_id) {
		this.user_id = user_id;
	}

	/**
	 * @return the domain_name
	 */
	public String getDomain_name() {
		return domain_name;
	}

	/**
	 * @param domain_name the domain_name to set
	 */
	public void setDomain_name(String domain_name) {
		this.domain_name = domain_name;
	}

	/**
	 * @return the country
	 */
	public String getCountry() {
		return country;
	}

	/**
	 * @param country the country to set
	 */
	public void setCountry(String country) {
		this.country = country;
	}

	/**
	 * @return the created_date
	 */
	public Date getCreated_date() {
		return created_date;
	}

	/**
	 * @param created_date the created_date to set
	 */
	public void setCreated_date(Date created_date) {
		this.created_date = created_date;
	}

	/**
	 * @return the website_url
	 */
	public String getWebsite_url() {
		return website_url;
	}

	/**
	 * @param website_url the website_url to set
	 */
	public void setWebsite_url(String website_url) {
		this.website_url = website_url;
	}

	/**
	 * @return the updated_date
	 */
	public Date getUpdated_date() {
		return updated_date;
	}

	/**
	 * @param updated_date the updated_date to set
	 */
	public void setUpdated_date(Date updated_date) {
		this.updated_date = updated_date;
	}

	/**
	 * @return the expiry_date
	 */
	public Date getExpiry_date() {
		return expiry_date;
	}

	/**
	 * @param expiry_date the expiry_date to set
	 */
	public void setExpiry_date(Date expiry_date) {
		this.expiry_date = expiry_date;
	}

	/**
	 * @return the registrar_name
	 */
	public String getRegistrar_name() {
		return registrar_name;
	}

	/**
	 * @param registrar_name the registrar_name to set
	 */
	public void setRegistrar_name(String registrar_name) {
		this.registrar_name = registrar_name;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
	public static final Finder<Long, WatchList> find = new Finder<>(WatchList.class);
	

}

