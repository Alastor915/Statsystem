package com.statsystem.entity;

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
    private List<Sample> samples;

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
        this.setSamples(new ArrayList<>());
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
        this.samples = samples;
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
