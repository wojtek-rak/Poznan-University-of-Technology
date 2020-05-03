package pl.put.poznan.transformer.rest.models;

public class RawScenario {
    private String scenarioJson;

    public RawScenario(String scenarioJson) {
        this.scenarioJson = scenarioJson;
    }

    public String getScenarioJson() {
        return scenarioJson;
    }
}
