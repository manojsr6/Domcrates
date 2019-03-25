package controllers;

import javax.inject.Named;
import javax.inject.Inject;
import akka.actor.UntypedActor;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

import java.util.concurrent.TimeUnit;

public class MyActorTask extends UntypedActor{
	
	
	@Override
    public void onReceive(Object message) throws Exception {
		System.out.println("Testing");
        // doDBCleanUpHere();
    }
	
}