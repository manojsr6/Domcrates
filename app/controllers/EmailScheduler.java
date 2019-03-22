package controllers;

import javax.inject.Named;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import akka.actor.*;
import scala.concurrent.ExecutionContext;
import scala.concurrent.duration.Duration;

public class EmailScheduler
{
	
	private final ActorRef someActor;
    private final ActorSystem actorSystem;
    private final ExecutionContext executionContext;
    
    @Inject
    public EmailScheduler(@Named("some-actor") ActorRef someActor, ActorSystem actorSystem, ExecutionContext executionContext) {
        this.someActor = someActor;
        this.actorSystem = actorSystem;
        this.executionContext = executionContext;
        this.initialize();
    }
    
    private void initialize() {
        actorSystem.scheduler().schedule(
            Duration.create(0, TimeUnit.SECONDS), // initialDelay
            Duration.create(30, TimeUnit.SECONDS), // interval
            someActor,
            "tick", // message,
            executionContext,
            ActorRef.noSender()
        );

    }

}
