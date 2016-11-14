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
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "value")
    private Double value;

    @SuppressWarnings("UnusedDeclaration")
    public Unit() {
    }

    @SuppressWarnings("UnusedDeclaration")
    public Unit(Long id, Date date, Double value) {
        this.setId(id);
        this.setDate(date);
        this.setValue(value);
    }

    public Unit(Date date, Double value) {
        this.setId(-1L);
        this.setDate(date);
        this.setValue(value);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
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
