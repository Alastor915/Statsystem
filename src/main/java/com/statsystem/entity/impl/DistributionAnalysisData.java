package com.statsystem.entity.impl;

import com.statsystem.entity.AnalysisData;
import javafx.util.Pair;

import java.util.List;

/**
 * Данные для хранения функции распределения
 */
public class DistributionAnalysisData implements AnalysisData {

    /**
     * distribution.get(i).getKey() -> value выборки,
     * distribution.get(i).getValue() -> количество таких значений в выборке
     */
    private List<Pair<Double,Double>> distribution;

    public DistributionAnalysisData(List<Pair<Double, Double>> distribution) {
        this.distribution = distribution;
    }

    public List<Pair<Double, Double>> getDistribution() {
        return distribution;
    }
}
