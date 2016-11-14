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
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany
    @JoinColumn(name="sample_id")
    private List<Sample> samples;

    @SuppressWarnings("UnusedDeclaration")
    public Project() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Project(Long id, String name, List<Sample> samples) {
        this.setId(id);
        this.setName(name);
        this.setSamples(samples);
    }

    public Project(String name) {
        this.setId(-1L);
        this.setName(name);
        this.setSamples(new ArrayList<>());
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
