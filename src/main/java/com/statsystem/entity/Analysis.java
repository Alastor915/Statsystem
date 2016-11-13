package com.statsystem.entity;

import javafx.util.Pair;

import java.util.Date;
import java.util.List;

/**
 * Данные стат анализа содержат имя вкладки, тип анализа, результаты анализа
 */
public class Analysis {

    private long id;

    private String name;

    private AnalysisType type;

    /** Результаты расчета для интерполяции */
    private List<Pair<Date,Double>> data;

    @SuppressWarnings("UnusedDeclaration")
    public Analysis() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Analysis(long id, String name, AnalysisType type, List<Pair<Date,Double>> data) {
        this.setId(id);
        this.setName(name);
        this.setType(type);
        this.setData(data);
    }

    public Analysis(String name){
        this.setId(-1);
        this.setName(name);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public AnalysisType getType() {
        return type;
    }

    public void setType(AnalysisType type) {
        this.type = type;
    }

    public List<Pair<Date,Double>> getData() {
        return data;
    }

    public void setData(List<Pair<Date,Double>> data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Analysis{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", data=" + data +
                '}';
    }
}
