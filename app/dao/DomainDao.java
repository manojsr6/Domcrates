package dao;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.PagedList;
import models.Domain;

public class DomainDao {

	public void add_domain(ArrayList<Domain> domainList ) throws SQLException
	{
		EbeanServer server = Ebean.getDefaultServer();
		for(int index= 0;index< domainList.size();index++)
		{
			server.save(domainList.get(index));
		}
	}
	
	public List<Domain> fetchByPrimaryEmail(String primary_email, int offset, int limit)
	{
		EbeanServer server = Ebean.getDefaultServer();
		List<Domain> domainList= server.find(Domain.class).where().eq("registered_email", primary_email).order("domain_name").setFirstRow(offset).setMaxRows(10).findList();
		return domainList;
	}
}
