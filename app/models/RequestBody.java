package models;

public class RequestBody{
	
	private String domain_name;
	private String primary_email;
	
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
	 * @return the primary_email
	 */
	public String getPrimary_email() {
		return primary_email;
	}
	/**
	 * @param primary_email the primary_email to set
	 */
	public void setPrimary_email(String primary_email) {
		this.primary_email = primary_email;
	}
}