package sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
import sample.Actors.Messages.ReadApiMessage;
import sample.Actors.ReadApiActor;
import sample.Models.GithubProperties;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.net.*;
import java.util.ResourceBundle;

import java.net.URL;
import javafx.collections.FXCollections;
import scala.concurrent.Await;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;


public class SampleController {

    public GridPane gridPane;
    public CheckBox checkBoxName;
    public CheckBox checkBoxDescription;
    public CheckBox checkBoxURL;
    public CheckBox checkBoxDate;
    public CheckBox checkBoxClone;
    public CheckBox checkBoxLanguage;
    public CheckBox checkBoxMirror;
    public CheckBox checkBoxOpen;
    public CheckBox checkBoxWatchers;
    public CheckBox checkBoxStars;
    public CheckBox checkBoxForks;
    public CheckBox checkBoxCheck;
    public CheckBox checkBoxZero;
    public TextField username;
    public TextField repositoryName;

    public String dataString;
    public ActorSystem system;
    public ActorRef readApiActor;
    public SampleController()
    {
        system = ActorSystem.create("test-system");
        readApiActor = system.actorOf(Props.create(ReadApiActor.class, this));
    }
    public void GetData(ActionEvent actionEvent)  {
        String x = "xD";
        GithubProperties githubProperties;

        Future<Object> future = Patterns.ask(readApiActor, new ReadApiMessage(username.getText(), repositoryName.getText()), 5000);

        //readApiActor.tell(new ReadApiMessage(username.getText(), repositoryName.getText()), ActorRef.noSender());
        try{

            Await.result(future, Duration.Inf());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            githubProperties = objectMapper.readValue(dataString , GithubProperties.class);
            x = githubProperties.full_name;

            inspect(GithubProperties.class, githubProperties);

        }
        catch (Exception  e) {
            x = "error";
            x = e.toString();
            System.out.println(e.toString());
        }


        //helloWorld.setText("YOU ARE NOT MY FRIEND YOUE ARE MY BROTHER MY FREIDN!");

//        gridPane.getChildren().clear();
//
//
//        for (int i = 0, j = 0; i < 10; i++, j++) {
//            Label label = new Label("Label Number"+i);
//            GridPane.setRowIndex(label, i);
//            if(i%2==0) GridPane.setColumnIndex(label, 0);
//            else GridPane.setColumnIndex(label, 1);
//
//            gridPane.getChildren().add(label);
//        }


        //helloWorld.setText(x);
    }

    private <T> void inspect(Class<T> klazz,  Object container) {
//        Field[] fields = klazz.getDeclaredFields();
//        System.out.printf("%d fields:%n", fields.length);
//        for (Field field : fields) {
//            System.out.printf("%s %s %s%n",
//                    Modifier.toString(field.getModifiers()),
//                    field.getType().getSimpleName(),
//                    field.getName()
//            );
//        }
//    }
        gridPane.getChildren().clear();

        int i = 0;
        try
        {
            for (Field field : klazz.getDeclaredFields()) {
                System.out.print(field.getName() + ": ");
                Object value = field.get(container);
                if(CheckForSkip(field.getName(), value)) continue;
                Label labelName = new Label(field.getName());
                GridPane.setRowIndex(labelName, i);
                GridPane.setColumnIndex(labelName, 0);

                Label labelValue;

                if(value != null)
                {
                    labelValue = new Label(value.toString());
                }
                else
                {
                    labelValue = new Label("null");
                }
                GridPane.setRowIndex(labelValue, i);
                GridPane.setColumnIndex(labelValue, 1);
                labelName.getStyleClass().add("dataLabelLeft");
                labelValue.getStyleClass().add("dataLabelRight");
                gridPane.getChildren().add(labelName);
                gridPane.getChildren().add(labelValue);
                i++;


                System.out.print(value + "\n");
            }
        }
        catch (Exception  e) {
            System.out.print(e.toString());
        }

    }
    private boolean CheckForSkip(String name, Object value)
    {
        if(!checkBoxName.isSelected() && name == "full_name") return true;
        if(!checkBoxDescription.isSelected() && name == "description") return true;
        if(!checkBoxURL.isSelected() && name == "url") return true;
        if(!checkBoxDate.isSelected() && name == "created_at") return true;
        if(!checkBoxClone.isSelected() && name == "clone_url") return true;
        if(!checkBoxLanguage.isSelected() && name == "language") return true;
        if(!checkBoxMirror.isSelected() && name == "mirror_url") return true;
        if(!checkBoxOpen.isSelected() && name == "open_issues_count") return true;
        if(!checkBoxWatchers.isSelected() && name == "subscribers_count") return true;
        if(!checkBoxStars.isSelected() && name == "stargazers_count") return true;
        if(!checkBoxForks.isSelected() && name == "forks_count") return true;
        if(checkBoxZero.isSelected())
        {
            if(value == null)
            {
                return true;
            }
            else
            {
                if(value == "0") return true;
                if(value.equals(Integer.valueOf(0))) return true;

            }
        }

        return false;
    }

    public void CheckBoxes(ActionEvent actionEvent) {
        System.out.print(checkBoxCheck.isSelected() + "\n");
        if(checkBoxCheck.isSelected())
        {
            checkBoxName.setSelected(true);
            checkBoxDescription.setSelected(true);
            checkBoxURL.setSelected(true);
            checkBoxDate.setSelected(true);
            checkBoxClone.setSelected(true);
            checkBoxLanguage.setSelected(true);
            checkBoxMirror.setSelected(true);
            checkBoxOpen.setSelected(true);
            checkBoxWatchers.setSelected(true);
            checkBoxStars.setSelected(true);
            checkBoxForks.setSelected(true);
        }
        else
        {
            checkBoxName.setSelected(false);
            checkBoxDescription.setSelected(false);
            checkBoxURL.setSelected(false);
            checkBoxDate.setSelected(false);
            checkBoxClone.setSelected(false);
            checkBoxLanguage.setSelected(false);
            checkBoxMirror.setSelected(false);
            checkBoxOpen.setSelected(false);
            checkBoxWatchers.setSelected(false);
            checkBoxStars.setSelected(false);
            checkBoxForks.setSelected(false);
        }
    }
}
