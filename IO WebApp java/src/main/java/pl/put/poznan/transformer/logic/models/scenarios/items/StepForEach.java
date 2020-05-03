package pl.put.poznan.transformer.logic.models.scenarios.items;

import pl.put.poznan.transformer.logic.abstraction.ScenarioStep;
import pl.put.poznan.transformer.logic.models.scenarios.Scenario;

import java.util.ArrayList;
import java.util.List;

public class StepForEach extends ScenarioStep {
    public String item;
    public Scenario foreachBlock;

    public StepForEach(String item, Scenario foreachBlock){
        this.item = item;
        this.foreachBlock = foreachBlock;
    }

    @Override
    public List<Scenario> getSubScenarios() {
        ArrayList<Scenario> subScenarios = new ArrayList<>(1);
        subScenarios.add(foreachBlock);
        return subScenarios;
    }

    @Override
    public String getScenarioHeader() {
        return String.format("FOR EACH: %s;", item);
    }
}
