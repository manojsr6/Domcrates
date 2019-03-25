package dao;

import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.PagedList;
import io.ebean.RawSql;
import io.ebean.RawSqlBuilder;
import io.ebean.SqlRow;
import models.Domain;
import models.DomainResponse;
import models.User;

public class DomainDao {
	/**
	 * This function is being used for adding domain into SQL database
	 * @param domainList
	 * @throws SQLException
	 */

	public void add_domain(ArrayList<Domain> domainList ) throws SQLException
	{
		EbeanServer server = Ebean.getDefaultServer();
		for(int index= 0;index< domainList.size();index++)
		{
			server.save(domainList.get(index));
		}
	}
	/**
	 * This function fetches domain details such as domain name, domain id and expire date grouping by domain name.
	 * @param primary_email
	 * @param offset
	 * @param limit
	 * @return It returns list of domain object which include domain name, domain id and expire date etc.
	 * @throws ParseException
	 */
	
	public List<Domain> fetchByPrimaryEmail(String primary_email, int offset, int limit) throws ParseException
	{
		EbeanServer server = Ebean.getDefaultServer();
		List<Domain> domainList= new ArrayList<>();
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String query= "select max(domain_id) as domain_id, domain_name,max(expiry_date) as expiry_date from domain where registered_email= \""+ primary_email+" \" GROUP BY domain_name";
		RawSql rawSql=   RawSqlBuilder.parse(query).create();
		List<SqlRow> sqlRows =  server.createSqlQuery(query).findList();
		for(int index= 0; index < sqlRows.size();index++)
		{
			Domain response_record= new Domain();
			response_record.setDomain_id(Integer.parseInt(sqlRows.get(index).get("domain_id").toString()));
			response_record.setDomain_name(sqlRows.get(index).get("domain_name").toString());
			response_record.setExpiry_date(df.parse(sqlRows.get(index).get("expiry_date").toString()));
			domainList.add(response_record);
		}
		return domainList;
	}
	
	public int deleteDomain(String email)
	{
		EbeanServer server = Ebean.getDefaultServer();
		return server.find(Domain.class).where().eq("registered_email", email).delete();
	}
}
