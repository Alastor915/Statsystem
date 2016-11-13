package com.statsystem.entity;

import javafx.util.Pair;

import java.util.Date;
import java.util.List;

/**
 * Выборка имеет имя(имя для вкладки) хранит лист точек и имеет ссылки на объекты стат анализа
 */
public class Sample {

    private long id;

    private String name;

    private List<Pair<Date,Double>> data;

    private long size;

    private List<Analysis> analysises;

    @SuppressWarnings("UnusedDeclaration")
    public Sample() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Sample(long id, String name, List<Pair<Date,Double>> data, long size, List<Analysis> analysises) {
        this.setId(id);
        this.setName(name);
        this.setData(data);
        this.setSize(size);
        this.setAnalysises(analysises);
    }

    public Sample(String name){
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

    public List<Pair<Date,Double>> getData() {
        return data;
    }

    public void setData(List<Pair<Date,Double>> data) {
        this.data = data;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    public List<Analysis> getAnalysises() {
        return analysises;
    }

    public void setAnalysises(List<Analysis> analysises) {
        this.analysises = analysises;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", data=" + data +
                ", analysises=" + analysises +
                '}';
    }
}
