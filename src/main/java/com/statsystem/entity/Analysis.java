package com.statsystem.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Данные стат анализа содержат имя вкладки, тип анализа, результаты анализа
 */
@Entity
@Table(name = "analyses")
public class Analysis implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private AnalysisType type;

    @Lob
    @Column(name = "data")
    private AnalysisData data;

    @SuppressWarnings("UnusedDeclaration")
    public Analysis() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Analysis(long id, String name, AnalysisType type, AnalysisData data) {
        this.setId(id);
        this.setName(name);
        this.setType(type);
        this.setData(data);
    }

    public Analysis(String name, AnalysisType type){
        this.setId(-1L);
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

    public AnalysisData getData() {
        return data;
    }

    public void setData(AnalysisData data) {
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
