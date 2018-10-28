package sample;

import javafx.event.ActionEvent;
import javafx.scene.control.Label;
import java.io.*;
import java.net.*;

public class SampleController {
    public Label helloWorld;

    public void SayHelloWorld(ActionEvent actionEvent)  {
        String x = "";
        try{
            x = ReadApi.getHTML("https://api.github.com/repos/google/gvisor");
        }
        catch (Exception  e) {
            x = "error";
        }
        //helloWorld.setText("YOU ARE NOT MY FRIEND YOUE ARE MY BROTHER MY FREIDN!");
        helloWorld.setText(x);
    }
}
