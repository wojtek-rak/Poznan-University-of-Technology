package sample;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.pattern.Patterns;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import sample.Actors.Messages.ReadApiMessage;
import sample.Actors.ReadApiActor;
import sample.Models.GithubProperties;

import java.lang.reflect.Field;


import java.util.regex.Matcher;
import java.util.regex.Pattern;

import scala.concurrent.Future;


// Class Controller responsible for manage window
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
    //constructor
    public SampleController()
    {
        system = ActorSystem.create("test-system");
        readApiActor = system.actorOf(Props.create(ReadApiActor.class, this));
    }

    private static Pattern usrNamePtrn = Pattern.compile("^[a-zA-Z0-9-]+$");

    // Validate Username method
    public static boolean validateUserName(String userName){

        Matcher mtch = usrNamePtrn.matcher(userName);
        if(mtch.matches()){
            return true;
        }
        return false;
    }

    // On button Action
    public void GetData(ActionEvent actionEvent)  {
        if("".equals(username.getText()) || "".equals(repositoryName.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Fill all fields!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        username.setText(username.getText().replaceAll(" ", "-"));
        repositoryName.setText(repositoryName.getText().replaceAll(" ", "-"));
        //repositoryName.setText(repositoryName.getText().replace(' ', '-'));
        if(!validateUserName(username.getText()))
        {
            Alert alert = new Alert(Alert.AlertType.ERROR, "Use only alphanumeric, space and '-' characters!", ButtonType.OK);
            alert.showAndWait();
            return;
        }
        Future<Object> future = Patterns.ask(readApiActor, new ReadApiMessage(username.getText(), repositoryName.getText()), 5000);

    }

    //Map data from json to class
    public void ShowData()
    {

        String x ="";
        try{

            //Await.result(future, Duration.Inf());
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            GithubProperties githubProperties = objectMapper.readValue(dataString , GithubProperties.class);
            //x = githubProperties.full_name;

            Inject(GithubProperties.class, githubProperties);

        }
        catch (Exception  e) {
            x = "error";
            x = e.toString();
            System.out.println(e.toString());
        }

    }

    //Inject data from class to grid Pane
    private <T> void Inject(Class<T> properties,  Object container) {
        gridPane.getChildren().clear();

        int i = 0;
        try
        {
            for (Field field : properties.getDeclaredFields()) {
                System.out.print(field.getName() + ": ");
                Object value = field.get(container);
                if(CheckForSkip(field.getName(), value)) continue;
                Label labelName = new Label(field.getName());
                GridPane.setRowIndex(labelName, i);
                GridPane.setColumnIndex(labelName, 0);

                Label labelValue;

                if(value != null)
                {
                    if("created_at".equals(field.getName()))
                    {
                        labelValue = new Label(value.toString().substring(0, value.toString().length() - value.toString().indexOf("T")));
                    }
                    else
                    {
                        labelValue = new Label(value.toString());
                    }
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

    //Check for checkboxes
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

    // Check / Uncheck all
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
