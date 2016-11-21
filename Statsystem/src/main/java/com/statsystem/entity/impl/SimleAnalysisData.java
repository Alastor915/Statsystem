package com.statsystem.entity.impl;

import com.statsystem.entity.AnalysisData;

/**
 * Данные для хранения мат ожидания и дисперсии
 */
public class SimleAnalysisData implements AnalysisData{

    private Double value;

    public SimleAnalysisData(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
