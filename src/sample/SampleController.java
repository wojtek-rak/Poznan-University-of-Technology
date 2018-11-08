package sample;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.util.Callback;
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


public class SampleController {
    public Label helloWorld;
    public TableView table;
    public TableColumn buttoncol;
    public TableColumn hyperlinkcol;
    public TableColumn descriptioncol;
    public GridPane gridPane;

    public void SayHelloWorld(ActionEvent actionEvent)  {
        String x = "xD";
        GithubProperties githubProperties;
        try{
            x = ReadApi.getHTML("https://api.github.com/repos/google/gvisor");
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            githubProperties = objectMapper.readValue(x.toString() , GithubProperties.class);
            x = githubProperties.full_name;

            inspect(GithubProperties.class, githubProperties);

        }
        catch (Exception  e) {
            x = "error";
            x = e.toString();
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


                Label labelName = new Label(field.getName());
                GridPane.setRowIndex(labelName, i);
                GridPane.setColumnIndex(labelName, 0);

                Label labelValue;
                Object value = field.get(container);
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
}
