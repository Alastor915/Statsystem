package com.statsystem.entity.impl;

import com.statsystem.entity.AnalysisData;
import com.statsystem.logic.statchars.DistributionFunction;
import javafx.util.Pair;

import java.util.List;

/**
 * Данные для хранения функции распределения
 */
public class DistributionAnalysisData implements AnalysisData {

    /**
     * quantity[i] = количество вхождений value[i] в выборку
     */
    private double[] values;

    private double[] quantity;

    private transient DistributionFunction f;

    public DistributionAnalysisData() {
    }

    public DistributionAnalysisData(double[] values, double[] quantity) {
        this.values = values;
        this.quantity = quantity;
        this.f = new DistributionFunction(values, quantity);
    }

    public double[] getValues() {
        return values;
    }

    public double[] getQuantity() {
        return quantity;
    }

    public DistributionFunction getF() {
        return f;
    }
}
