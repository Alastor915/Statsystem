package com.statsystem.entity.impl;

import com.statsystem.entity.AnalysisData;
import com.statsystem.entity.Unit;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;

import java.util.List;

/**
 * Данные для интерполяции Ньютона
 */
public class NewtonAnalysisData implements AnalysisData{

    /**
     * Массив, для хранения возвращаемого значения PolynomialFunctionNewtonForm.getNewtonCoefficients()
     */
    private double[] newtonCoefficients;

    /**
     * Массив, для хранения возвращаемого значения PolynomialFunctionNewtonForm.getCenters()
     */
    private double[] centers;

    /**
     * Список точек, для которых рассчитывалась интерполяция в точке
     */
    private List<Unit> units;

    /**
     * Поле для хранения функции в runtime
     */
    private transient PolynomialFunctionNewtonForm f;

    public NewtonAnalysisData() {
    }

    public NewtonAnalysisData(double[] newtonCoefficients, double[] centers, List<Unit> units) {
        this.newtonCoefficients = newtonCoefficients;
        this.centers = centers;
        this.units = units;
        this.f = new PolynomialFunctionNewtonForm(newtonCoefficients, centers);
    }

    public double[] getNewtonCoefficients() {
        return newtonCoefficients;
    }

    public double[] getCenters() {
        return centers;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public PolynomialFunctionNewtonForm getF() {
        return f;
    }
}
