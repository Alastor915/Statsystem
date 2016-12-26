package com.statsystem.entity.impl;

import com.statsystem.entity.AnalysisData;
import com.statsystem.entity.Unit;
import com.statsystem.logic.interpolation.NewtonFunction;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Данные для интерполяции Ньютона
 */
public class NewtonAnalysisData implements AnalysisData{

    /**
     * Массив, для хранения возвращаемого значения PolynomialFunctionNewtonForm.getNewtonCoefficients()
     */
    private List<double[]> newtonCoefficients;

    /**
     * Массив, для хранения возвращаемого значения PolynomialFunctionNewtonForm.getCenters()
     */
    private List<double[]> centers;

    /**
     * Список точек, для которых рассчитывалась интерполяция в точке
     */
    private List<Unit> units;

    /**
     * Поле для хранения функции в runtime
     */
    private transient NewtonFunction f;

    private transient double[] maxElem;

    public NewtonAnalysisData() {
    }

    public NewtonAnalysisData(List<double[]> newtonCoefficients, List<double[]> centers, List<Unit> units, double[] maxElem) {
        this.newtonCoefficients = newtonCoefficients;
        this.centers = centers;
        this.units = units;
        this.maxElem = maxElem;
        f = new NewtonFunction(newtonCoefficients, centers, maxElem );
    }

    public List<double[]> getNewtonCoefficients() {
        return newtonCoefficients;
    }

    public List<double[]> getCenters() {
        return centers;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public UnivariateFunction getF() {
        if (f == null)
            this.f = new NewtonFunction(newtonCoefficients, centers, maxElem);
        return f;
    }
}
