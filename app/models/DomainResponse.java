package models;

import java.util.Date;

import javax.persistence.Entity;

@Entity
public class DomainResponse {

	private int id;
	private String domain_name;
	private Date expiry_date;
	
	private int year;
	private int month;
	private int day;
	
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
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	
	/**
	 * @param id the id to set
	 */
	
	public void setId(int id) {
		this.id = id;
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
	
	
	
}
