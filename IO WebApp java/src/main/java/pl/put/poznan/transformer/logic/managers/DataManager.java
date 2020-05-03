package pl.put.poznan.transformer.logic.managers;

import pl.put.poznan.transformer.logic.models.scenarios.ScenarioCollection;

public class DataManager {
    private static DataManager instance;
    public static DataManager getInstance(){
        if(instance == null){
            instance = new DataManager();
        }
        return  instance;
    }

    public ScenarioCollection scenarioCollection;

    private DataManager(){
        scenarioCollection = new ScenarioCollection();
    }
}
