package pl.put.poznan.transformer.rest.controllers;

import org.springframework.web.bind.annotation.*;
import pl.put.poznan.transformer.logic.managers.DataManager;
import pl.put.poznan.transformer.logic.models.scenarios.Scenario;
import pl.put.poznan.transformer.logic.models.scenarios.ScenarioRoot;
import pl.put.poznan.transformer.logic.operations.*;
import pl.put.poznan.transformer.rest.models.CountScenarioItemsResponse;
import pl.put.poznan.transformer.rest.models.CreateScenarioResponse;
import pl.put.poznan.transformer.rest.models.RawScenario;
import pl.put.poznan.transformer.rest.models.ScenarioWithNumberingResponse;

/**
 * Główna klasa kontrolująca odbieranie requestów i wysyłanie odpowiedzi
 *
 * @author Jacek Gulij, Marek Subocz, Wojciech Rak
 * @version 1.0
 */
@RestController
public class ScenarioController {

    /**
     * Metoda przyjmująca requesta get i zwracająca ilość elementów wybranego scenariusza
     *
     * Przykładowy request: http://localhost:8080/count/12345678
     *
     * @param id Id scenariusza, jaki znajduje się już w bazie.
     *
     * @return Prosta klasa zawierająca jedno pole określające ilość elementów scenariusza
     */
    @RequestMapping(value = "/count/{id}", method = RequestMethod.GET, produces = "application/json")
    public CountScenarioItemsResponse getCount(@PathVariable("id") String id) {
        return new CountScenarioItems()
                .setScenario(GetScenarioRoot(id).scenario)
                .execute();
    }
    public CountScenarioItemsResponse get_custom(Scenario scenario) {
        return new CountScenarioItems()
                .setScenario(scenario)
                .execute();
    }

    /**
     * Metoda przyjmująca requesta get i zwracająca ilość elementów warunkowych wybranego scenariusza
     *
     * Przykładowy request: http://localhost:8080/count/conditional/12345678
     *
     * @param id id scenariusza, jaki znajduje się już w bazie.
     *
     * @return prosta klasa zawierająca jedno pole określające ilość elementów scenariusza
     */
    @RequestMapping(value = "/count/conditional/{id}", method = RequestMethod.GET, produces = "application/json")
    public CountScenarioItemsResponse getConditionalCount(@PathVariable("id") String id) {
        return new CountConditionalScenarioItems()
                .setScenario(GetScenarioRoot(id).scenario)
                .execute();
    }

    /**
     * Metoda przyjmująca requesta post i zapisująca przekazany scenariusz do bazy
     *
     * @param json struktura przekazanego scenariusza
     *
     * @return prosta klasa zawierająca jedno pole określające id zapisanego w bazie scenariusza
     */
    @RequestMapping(method = RequestMethod.POST, produces = "application/json", consumes = "application/json")
    @ResponseBody
    public CreateScenarioResponse post(@RequestBody String json) {
        return new CreateScenario()
                .setRawScenario(new RawScenario(json))
                .execute();
    }

    /**
     * Metoda przyjmująca requesta get i zwracająca strukturę scenariusza o podanym id
     *
     * Przykładowy request: http://localhost:8080/12345678
     *
     * @param id struktura przekazanego scenariusza
     *
     * @return prosta klasa zawierająca jedno pole określające id zapisanego w bazie scenariusza
     */
    @RequestMapping(value = "/{id}", method = RequestMethod.GET, produces = "application/json")
    public Scenario getScenario(@PathVariable("id") String id) {
        return DataManager.getInstance().scenarioCollection.get(id).scenario;
    }

    /**
     * Metoda przyjmująca requesta get i zwracająca schemat wybranego
     * scenariusza z ponumerowanymi elementami
     *
     * Przykładowy request: http://localhost:8080/ScenarioWithNumbering/12345678
     *
     * @param id Id scenariusza, jaki znajduje się już w bazie.
     *
     * @return Klasa zawierająca ponumerowany i podzielony na kroki scenariusz
     */
    //ScenarioWithNumberingResponse
    @RequestMapping(value = "/ScenarioWithNumbering/{id}", method = RequestMethod.GET, produces = "text/plain")
    public String getScenarioWithNumbering(@PathVariable("id") String id) {
        return new ScenarioWithNumbering()
                .setScenario(GetScenario(id))
                .execute().scenario;
    }

    private Scenario GetScenario(String id){
        return DataManager.getInstance().scenarioCollection.get(id);
    }
    private ScenarioRoot GetScenarioRoot(String id){
        return DataManager.getInstance().scenarioCollection.get(id);
    }
}
