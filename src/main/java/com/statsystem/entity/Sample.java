package com.statsystem.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.*;

/**
 * Выборка имеет имя(имя для вкладки) хранит лист точек и имеет ссылки на объекты стат анализа
 */
@Entity
@Table(name = "samples")
public class Sample implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    private Project project;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "sample")
    private List<Unit> data = new ArrayList<>();

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "sample")
    private List<Analysis> analyses = new ArrayList<>();

    @SuppressWarnings("UnusedDeclaration")
    public Sample() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Sample(long id, String name, Project project, List<Unit> data, List<Analysis> analyses) {
        this.setId(id);
        this.setName(name);
        this.setProject(project);
        this.setData(data);
        this.setAnalyses(analyses);
    }

    public Sample(String name){
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

    public List<Unit> getData() {
        return data;
    }

    public Unit getUnitByDate(Double date) {
        Unit unit = null;
        for (Unit u: data){
            if (u.getDate().equals(date))
                unit = u;
        }
        return unit;
    }

    public void setData(List<Unit> data) {
        for (Unit unit : data){
            unit.setSample(this);
        }
        this.data = data;
    }

    public List<Analysis> getAnalyses() {
        return analyses;
    }

    public void setAnalyses(List<Analysis> analyses) {
        for (Analysis analysis : analyses){
            analysis.setSample(this);
        }
        this.analyses = analyses;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public boolean addUnit(Unit unit){
        boolean result = data.add(unit);
        if (result)
            unit.setSample(this);
        return result;
    }

    public boolean addAnalysis(Analysis analysis){
        boolean result = analyses.add(analysis);
        if (result)
            analysis.setSample(this);
        return result;
    }

    public boolean removeAnalysis(Analysis analysis){
        return analyses.remove(analysis);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Sample other = (Sample) obj;
        return id == other.getId();
    }

    @Override
    public String toString() {
        return "Sample{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    public double[] getValues(){
        double [] values = new double [data.size()];
        for (int i = 0; i < data.size();  i++){
            values[i] = data.get(i).getValue();
        }
        return values;
    }

    public double[] getDates(){
        double [] dates = new double [data.size()];
        for (int i = 0; i < data.size();  i++){
            dates[i] = data.get(i).getDate();
        }
        return dates;
    }
}
