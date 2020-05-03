package pl.put.poznan.transformer.logic.models.scenarios.items;

import pl.put.poznan.transformer.logic.abstraction.ScenarioStep;
import pl.put.poznan.transformer.logic.models.scenarios.Scenario;

import java.util.ArrayList;
import java.util.List;

public class StepElse extends ScenarioStep{
    public String condition;
    public Scenario elseBlock;

    public StepElse(String condition, Scenario elseBlock){
        this.condition = condition;
        this.elseBlock = elseBlock;
    }

    @Override
    public List<Scenario> getSubScenarios() {
        ArrayList<Scenario> subScenarios = new ArrayList<>(1);
        subScenarios.add(elseBlock);
        return subScenarios;
    }

    @Override
    public String getScenarioHeader() {
        return String.format("ELSE: %s;", condition);
    }
}