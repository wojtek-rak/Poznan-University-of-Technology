package sample.Actors;

import akka.actor.AbstractActor;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
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
                    String x = "";
                    //x = ReadApi.getHTML("https://api.github.com/repos/google/gvisor");
                    //x = ReadApi.getHTML("https://api.github.com/repos/wojtek-rak/ApiReader");
                    //System.out.println("https://api.github.com/repos/" + g.username + "/" +  g.repositoryName);
                    try
                    {
                        x = ReadApi.getHTML("https://api.github.com/repos/" + g.username + "/" +  g.repositoryName);

                    }
                    catch (Exception  e)
                    {
                        Platform.runLater(
                                () -> {
                                    Alert alert = new Alert(Alert.AlertType.ERROR, "Check if fields correct, and your internet connection! \n" + e.getMessage(), ButtonType.OK);
                                    alert.showAndWait();
                                });

                    }
                    if(!"".equals(x))
                    {
                        sampleController.dataString = x;
                        sender().tell("XD", getSelf());
                        Platform.runLater(
                                () -> {
                                    sampleController.ShowData();
                                });
                    }


                })
                .build();
    }
}