package com.statsystem.entity;

import javax.persistence.*;
import java.util.*;

/**
 * Выборка имеет имя(имя для вкладки) хранит лист точек и имеет ссылки на объекты стат анализа
 */
@Entity
@Table(name = "samples")
public class Sample {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<Unit> data;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "id")
    private List<Analysis> analyses;

    @SuppressWarnings("UnusedDeclaration")
    public Sample() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Sample(Long id, String name, List<Unit> data, List<Analysis> analyses) {
        this.setId(id);
        this.setName(name);
        this.setData(data);
        this.setAnalyses(analyses);
    }

    public Sample(String name){
        this.setId(-1L);
        this.setName(name);
        this.setData(new ArrayList<>());
        this.setAnalyses(new ArrayList<>());
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

    public List<Unit> getData() {
        return data;
    }

    public void setData(List<Unit> data) {
        this.data = data;
    }

    public List<Analysis> getAnalyses() {
        return analyses;
    }

    public void setAnalyses(List<Analysis> analyses) {
        this.analyses = analyses;
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", data=" + data +
                ", analyses=" + analyses +
                '}';
    }
}
