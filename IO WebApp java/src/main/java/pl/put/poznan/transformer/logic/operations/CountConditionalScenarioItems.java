package pl.put.poznan.transformer.logic.operations;

import pl.put.poznan.transformer.logic.abstraction.ScenarioItemVisitor;
import pl.put.poznan.transformer.logic.abstraction.ScenarioStep;
import pl.put.poznan.transformer.logic.models.scenarios.Scenario;
import pl.put.poznan.transformer.rest.models.CountScenarioItemsResponse;

/**
 * Klasa służąca do policzenia ilości kroków warunkowych w scenariuszu i zwrócenia wyniku
 *
 * @author Marek Subocz
 * @version 1.3
 */
public class CountConditionalScenarioItems extends ScenarioItemVisitor<CountScenarioItemsResponse> {

    /**
     * Licznik kroków warunkowych
     */
    private Integer amount = 0;

    /**
     * Metoda służąca do rekurencyjnego przejścia przez wszystkie podscenariusze,
     * czyli kroki zawarte w innym kroku.
     *
     * @param scenario wejściowy scenariusz, to w nim liczymy kroki warunkowe.
     */
    @Override
    protected void visit(Scenario scenario) {
        for (ScenarioStep step : scenario) {
            if(!step.getSubScenarios().isEmpty())
                amount++;
            for (Scenario subScenario : step.getSubScenarios()) {
                visit(subScenario);
            }
        }
    }

    /**
     * Metoda zwracająca klasę z wynikiem liczenia
     *
     * @return  rezultat w postaci klasy z jednym polem (generujemy z niej JSON-a na POST Response)
     */
    @Override
    protected CountScenarioItemsResponse getResult() {
        CountScenarioItemsResponse result = new CountScenarioItemsResponse();
        result.amount = amount;
        return result;
    }
}
