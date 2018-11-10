package sample.Actors;

import akka.actor.AbstractActor;
import javafx.application.Platform;
import sample.Actors.Messages.ReadApiMessage;
import sample.ReadApi;
import sample.SampleController;

public class ReadApiActor extends AbstractActor {
    private SampleController sampleController;
    public ReadApiActor(SampleController controller)
    {
        sampleController = controller;
    }

    public Receive createReceive() {
        return receiveBuilder()
                .match(ReadApiMessage.class, g -> {
                    System.out.println(g.username + g.repositoryName);
                    String x = "";
                    //x = ReadApi.getHTML("https://api.github.com/repos/google/gvisor");
                    //x = ReadApi.getHTML("https://api.github.com/repos/wojtek-rak/ApiReader");
                    //System.out.println("https://api.github.com/repos/" + g.username + "/" +  g.repositoryName);
                    x = ReadApi.getHTML("https://api.github.com/repos/" + g.username + "/" +  g.repositoryName);
                    sampleController.dataString = x;
                    sender().tell("XD", getSelf());
                    Platform.runLater(
                            () -> {
                                sampleController.GetDataBlyat();
                            });

                })
                .build();
    }
}