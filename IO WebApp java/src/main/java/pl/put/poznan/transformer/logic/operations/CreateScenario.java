package pl.put.poznan.transformer.logic.operations;

import pl.put.poznan.transformer.logic.abstraction.IOperation;
import pl.put.poznan.transformer.logic.managers.DataManager;
import pl.put.poznan.transformer.logic.models.scenarios.Scenario;
import pl.put.poznan.transformer.logic.models.scenarios.ScenarioRoot;
import pl.put.poznan.transformer.rest.models.CreateScenarioResponse;
import pl.put.poznan.transformer.rest.models.RawScenario;

import java.util.UUID;

/**
 * Główna klasa kontrolująca wrzucanie do bazy nowych scenariuszy
 *
 * @author Marek Subocz
 * @version 1.3
 */
public class CreateScenario implements IOperation<CreateScenarioResponse> {

    /**
     * Surowy scenariusz w postaci JSON-a przekazanego w requeście POST
     */
    private RawScenario rawScenario;

    public CreateScenario setRawScenario(RawScenario rawScenario){
        this.rawScenario = rawScenario;
        return this;
    }

    /**
     * Metoda kontrolująca przekonwertowanie scenariusza z JSON-a na obiekt klasy Scenario
     * i ustalająca nowe, unikalne id
     *
     * Id jest liczbą całkowitą zwiększaną o 1 po każdym dodaniu nowego scenariusza do bazy
     *
     * @return Id nowego scenariusza
     */
    @Override
    public CreateScenarioResponse execute() {
        ScenarioRoot scenario = ModelConverters.RawToScenario(rawScenario);
        String uniqueID = UUID.randomUUID().toString();
        DataManager.getInstance().scenarioCollection.put(uniqueID, scenario);
        CreateScenarioResponse result = new CreateScenarioResponse();
        result.id = uniqueID;
        return result;
    }
}
