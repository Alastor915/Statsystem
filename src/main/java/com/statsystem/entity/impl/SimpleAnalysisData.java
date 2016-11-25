package com.statsystem.entity.impl;

import com.statsystem.entity.AnalysisData;

/**
 * Данные для хранения мат ожидания и дисперсии
 */
public class SimpleAnalysisData implements AnalysisData{

    private Double value;

    public SimpleAnalysisData() {
    }

    public SimpleAnalysisData(Double value) {
        this.value = value;
    }

    public Double getValue() {
        return value;
    }
}
