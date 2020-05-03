package pl.put.poznan.transformer.logic.operations;

import pl.put.poznan.transformer.logic.abstraction.ScenarioStep;
import pl.put.poznan.transformer.logic.abstraction.ScenarioItemVisitor;
import pl.put.poznan.transformer.logic.models.scenarios.Scenario;
import pl.put.poznan.transformer.rest.models.CountScenarioItemsResponse;

public class CountScenarioItems extends ScenarioItemVisitor<CountScenarioItemsResponse> {
    private Integer amount = 0;

    @Override
    protected void visit(Scenario scenario) {
        for (ScenarioStep step : scenario) {
            amount++;
            for (Scenario subScenario : step.getSubScenarios()) {
                visit(subScenario);
            }
        }
    }

    @Override
    protected CountScenarioItemsResponse getResult() {
        CountScenarioItemsResponse result = new CountScenarioItemsResponse();
        result.amount = amount;
        return result;
    }
}
