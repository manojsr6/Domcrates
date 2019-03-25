package controllers;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Singleton;

import com.google.inject.AbstractModule;

import play.api.libs.concurrent.Akka;
import scala.concurrent.duration.Duration;
public class NotificationScheduler extends AbstractModule {

	@Override
    protected void configure() {
		System.out.println("Inside configure function....");
        bind(MyActorTask.class).asEagerSingleton();
    }

}
