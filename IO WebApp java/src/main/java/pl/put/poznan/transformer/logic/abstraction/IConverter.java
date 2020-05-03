package pl.put.poznan.transformer.logic.abstraction;

public interface IConverter<SourceType, ResultType> {
    public ResultType convert(SourceType source);
}
