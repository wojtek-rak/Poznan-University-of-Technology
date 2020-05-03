package pl.put.poznan.transformer.logic.abstraction;

import pl.put.poznan.transformer.logic.models.scenarios.Scenario;

public abstract class ScenarioItemVisitor<ResultType> implements IOperation<ResultType> {
    private Scenario scenario;

    public ScenarioItemVisitor<ResultType> setScenario(Scenario scenario){
        this.scenario = scenario;
        return this;
    }

    public ResultType execute(){
        visit(scenario);
        return getResult();
    }

    protected abstract void visit(Scenario scenario);

    protected abstract ResultType getResult();

    protected Scenario getScenario(){
        return scenario;
    }
}
