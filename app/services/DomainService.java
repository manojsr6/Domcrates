package services;

import dao.DomainDao;
import models.*;

import java.text.ParseException;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DomainService {
	
	DomainDao domainDao= new DomainDao();
	
	
	public List<Domain> fetchDomainByemail(String primary_email,int offset,int limit) throws ParseException
	{
		List<Domain> domainList= domainDao.fetchByPrimaryEmail(primary_email, offset, limit);
		
		for(int index =0 ;index<domainList.size();index++)
		{
			
			LocalDate expiryLocalDate= domainList.get(index).getExpiry_date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
			
			Period difference= Period.between(LocalDate.now(), expiryLocalDate);
			domainList.get(index).setYear(difference.getYears());
			domainList.get(index).setMonth(difference.getMonths());
			domainList.get(index).setDay(difference.getDays());
		}
		return domainList;
	}

}
