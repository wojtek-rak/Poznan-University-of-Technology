package pl.put.poznan.transformer.logic.models.scenarios;

import pl.put.poznan.transformer.logic.abstraction.ScenarioStep;
import pl.put.poznan.transformer.logic.models.scenarios.items.StepBasicOperation;
import pl.put.poznan.transformer.logic.models.scenarios.items.StepForEach;
import pl.put.poznan.transformer.logic.models.scenarios.items.StepIf;

import java.util.ArrayList;

public class Scenario extends ArrayList<ScenarioStep> {

    public static Scenario getTestScenario(){
        Scenario scenario = new Scenario();
        scenario.add(new StepBasicOperation("Bibliotekarz wybiera opcje dodania nowej pozycji książkowej"));
        scenario.add(new StepBasicOperation("Wyświetla się formularz."));
        scenario.add(new StepBasicOperation("Bibliotekarz podaje dane książki."));
        Scenario ifScenario = new Scenario();
        scenario.add(new StepIf("Bibliotekarz pragnie dodać egzemplarze książki", ifScenario));
        ifScenario.add(new StepBasicOperation("Bibliotekarz wybiera opcję definiowania egzemplarzy"));
        ifScenario.add(new StepBasicOperation("System prezentuje zdefiniowane egzemplarze"));
        Scenario forEachScenario = new Scenario();
        ifScenario.add(new StepForEach("egzemplarz", forEachScenario));
        forEachScenario.add(new StepBasicOperation("Bibliotekarz wybiera opcję dodania egzemplarza"));
        forEachScenario.add(new StepBasicOperation("System prosi o podanie danych egzemplarza"));
        forEachScenario.add(new StepBasicOperation("Bibliotekarz podaje dane egzemplarza i zatwierdza."));
        forEachScenario.add(new StepBasicOperation("System informuje o poprawnym dodaniu egzemplarza i prezentuje zaktualizowaną listę egzemplarzy."));
        scenario.add(new StepBasicOperation("Bibliotekarz zatwierdza dodanie książki."));
        scenario.add(new StepBasicOperation("System informuje o poprawnym dodaniu książki."));
        return scenario;
    }
}
