package pl.put.poznan.transformer.logic.models.scenarios.items;

import pl.put.poznan.transformer.logic.abstraction.ScenarioStep;
import pl.put.poznan.transformer.logic.models.scenarios.Scenario;

import java.util.ArrayList;
import java.util.List;

public class StepIf extends ScenarioStep {
    public String condition;
    public Scenario ifBlock;

    public StepIf(String condition, Scenario ifBlock){
        this.condition = condition;
        this.ifBlock = ifBlock;
    }

    @Override
    public List<Scenario> getSubScenarios() {
        ArrayList<Scenario> subScenarios = new ArrayList<>(1);
        subScenarios.add(ifBlock);
        return subScenarios;
    }

    @Override
    public String getScenarioHeader() {
        return String.format("IF: %s;", condition);
    }
}
