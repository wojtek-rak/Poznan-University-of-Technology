package pl.put.poznan.transformer.logic.models.scenarios.items;

import pl.put.poznan.transformer.logic.abstraction.ScenarioStep;
import pl.put.poznan.transformer.logic.models.scenarios.Scenario;

import java.util.ArrayList;
import java.util.List;

public class StepBasicOperation extends ScenarioStep {
    public String operation;

    public StepBasicOperation(String operation){
        this.operation = operation;
    }

    @Override
    public List<Scenario> getSubScenarios() {
        return new ArrayList<>(0);
    }

    @Override
    public String getScenarioHeader() {
        return operation;
    }
}
