package com.statsystem.entity;

import java.util.List;

/**
 * Проект группирует выборки
 */
public class Project {

    private long id;

    private String name;

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
