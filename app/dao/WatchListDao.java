package dao;

import java.sql.SQLException;
import java.util.List;

import io.ebean.Ebean;
import io.ebean.EbeanServer;
import models.Domain;
import models.User;
import models.WatchList;

public class WatchListDao{
	
	public WatchList fetch(String domainName)
	{
		EbeanServer server = Ebean.getDefaultServer();
		WatchList watch=  server.find(WatchList.class).where().eq("domain_name", domainName).findOne();
		return watch;
	}
	
	public WatchList add(WatchList watchList) throws SQLException
	{
		EbeanServer server = Ebean.getDefaultServer();
		server.save(watchList);
		return watchList;
	}
	
	public List<WatchList> fetchByuser_id(int id, int offset, int limit)
	{
		EbeanServer server = Ebean.getDefaultServer();
		System.out.println("inisde fetch user");
		List<WatchList> watchList= server.find(WatchList.class).where().eq("user_id", id).order("domain_name").setFirstRow(offset).setMaxRows(10).findList();
		return watchList;
	}
}