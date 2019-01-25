package models;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.Constraint;

import ch.qos.logback.core.joran.spi.DefaultClass;
import io.ebean.Finder;
import io.ebean.Model;

@Entity
public class TestEbean extends Model{
	@Id
	@GeneratedValue
	public  int user_id;
	public  String name;
	
	public static final Finder<Long, TestEbean> find = new Finder<>(TestEbean.class);
}
