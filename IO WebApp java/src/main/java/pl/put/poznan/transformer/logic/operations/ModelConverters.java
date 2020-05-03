package pl.put.poznan.transformer.logic.operations;

import com.jayway.jsonpath.JsonPath;
import net.minidev.json.JSONArray;
import pl.put.poznan.transformer.logic.models.scenarios.Scenario;
import pl.put.poznan.transformer.logic.models.scenarios.ScenarioRoot;
import pl.put.poznan.transformer.logic.models.scenarios.items.StepBasicOperation;
import pl.put.poznan.transformer.logic.models.scenarios.items.StepElse;
import pl.put.poznan.transformer.logic.models.scenarios.items.StepForEach;
import pl.put.poznan.transformer.logic.models.scenarios.items.StepIf;
import pl.put.poznan.transformer.rest.models.RawScenario;

import java.util.ArrayList;
import java.util.LinkedHashMap;


public class ModelConverters {

    private static Scenario findScenario(JSONArray steps){
        Scenario scenario = new Scenario();
        for (Object step : steps) {
        String keyword = ((LinkedHashMap) step).get("Keyword").toString();
        String name = ((LinkedHashMap) step).get("Name").toString();
        JSONArray stepsDeep = (JSONArray)((LinkedHashMap) step).get("Steps");
            if(keyword.equals("IF")){
                scenario.add(new StepIf(name, findScenario(stepsDeep)));
            }
            else if (keyword.equals("ELSE")){
                scenario.add(new StepElse(name, findScenario(stepsDeep)));
            }
            else if (keyword.equals("FOREACH")){
                scenario.add(new StepForEach(name, findScenario(stepsDeep)));
            }
            else{   //bez keywordów
                scenario.add(new StepBasicOperation(name));
            }
        }
        return scenario;
    }

    public static ScenarioRoot RawToScenario(RawScenario rawScenario){
        String json = rawScenario.getScenarioJson();
        JSONArray steps = JsonPath.read(json, "$.Steps[*]");
        ScenarioRoot scenarioRoot = new ScenarioRoot();
        scenarioRoot.scenario = findScenario(steps);
        scenarioRoot.title = JsonPath.read(json, "$.Title");
        scenarioRoot.systemActor = JsonPath.read(json, "$.['System Actor']");
        scenarioRoot.actors = JsonPath.read(json, "$.Actors[*]");
        return scenarioRoot;
    }
}
