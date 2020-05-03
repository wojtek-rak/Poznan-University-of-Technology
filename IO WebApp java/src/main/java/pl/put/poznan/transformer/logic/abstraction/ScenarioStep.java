package pl.put.poznan.transformer.logic.abstraction;

import pl.put.poznan.transformer.logic.models.scenarios.Scenario;

import java.util.List;

public abstract class ScenarioStep {
    public abstract List<Scenario> getSubScenarios();
    public abstract String getScenarioHeader();
}
