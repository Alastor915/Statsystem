package com.statsystem.entity;

import com.sun.istack.internal.Nullable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Проект группирует выборки
 */
@Entity
@Table(name = "project")
public class Project implements Serializable {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "name")
    private String name;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "project")
    private List<Sample> samples = new ArrayList<>();

    @SuppressWarnings("UnusedDeclaration")
    public Project() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Project(long id, String name, List<Sample> samples) {
        this.setId(id);
        this.setName(name);
        this.setSamples(samples);
    }

    public Project(String name) {
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

    public List<Sample> getSamples() {
        return samples;
    }

    public void setSamples(List<Sample> samples) {
        for (Sample sample : samples){
            sample.setProject(this);
        }
        this.samples = samples;
    }

    public boolean addSample(Sample sample){
        boolean result = samples.add(sample);
        if (result)
            sample.setProject(this);
        return result;
    }

    public boolean removeSample(Sample sample){
        return samples.remove(sample);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Project other = (Project) obj;
        return id == other.getId();
    }

    @Override
    public String toString() {
        return "Project{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", samples=" + samples +
                '}';
    }
}
