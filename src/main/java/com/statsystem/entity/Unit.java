package com.statsystem.entity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Значение выборки
 */
@Entity
@Table(name = "units")
public class Unit implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "date")
    private Double date;

    @Column(name = "value")
    private Double value;

    @SuppressWarnings("UnusedDeclaration")
    public Unit() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Unit(long id, Double date, Double value) {
        this.setId(id);
        this.setDate(date);
        this.setValue(value);
    }

    public Unit(Double date, Double value) {
        this.setId(-1L);
        this.setDate(date);
        this.setValue(value);
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public Double getDate() {
        return date;
    }

    public void setDate(Double date) {
        this.date = date;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Unit{" +
                "id=" + id +
                ", date=" + date +
                ", value=" + value +
                '}';
    }
}
