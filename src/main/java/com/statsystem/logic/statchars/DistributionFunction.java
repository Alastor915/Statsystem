package com.statsystem.logic.statchars;

import org.apache.commons.math3.analysis.UnivariateFunction;

/**
 * Created by Djdf on 22.11.2016.
 */
public class DistributionFunction implements UnivariateFunction{

    private double[] values;

    private double[] quantity;

    public DistributionFunction(double[] values, double[] quantity) {
        this.values = values;
        this.quantity = quantity;
    }

    public DistributionFunction(double[] sample){
        this.values = null;//рассчитать количество вхождений
        this.quantity = null;
    }

    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public double[] getQuantity() {
        return quantity;
    }

    public void setQuantity(double[] quantity) {
        this.quantity = quantity;
    }

    @Override
    public double value(double value) {
        return 0; // возвращает количество элементов выборки, меньших value.
    }
}
