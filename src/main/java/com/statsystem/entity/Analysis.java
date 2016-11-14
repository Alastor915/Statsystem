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
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "type")
    private AnalysisType type;

    /** Результаты расчета для интерполяции */
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<Unit> data;

    @SuppressWarnings("UnusedDeclaration")
    public Analysis() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Analysis(Long id, String name, AnalysisType type, List<Unit> data) {
        this.setId(id);
        this.setName(name);
        this.setType(type);
        this.setData(data);
    }

    public Analysis(String name, AnalysisType type){
        this.setId(-1L);
        this.setName(name);
        this.setData(new ArrayList<>());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    public List<Unit> getData() {
        return data;
    }

    public void setData(List<Unit> data) {
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
