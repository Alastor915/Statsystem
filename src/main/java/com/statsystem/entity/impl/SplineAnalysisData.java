package com.statsystem.entity.impl;

import com.statsystem.entity.AnalysisData;
import com.statsystem.entity.Unit;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;

import java.util.List;

/**
 * Данные для интерполяции Ньютона
 */
public class SplineAnalysisData implements AnalysisData {

    /**
     * Массив, для хранения возвращаемого значения PolynomialSplineFunction.getKnots()
     */
    private double[] knots;

    /**
     * Список массивов, для хранения массивов коэффициетов PolynomialSplineFunction.getPolynomials().getCoefficients()
     */
    private List<double[]> polynomialCoefficients;

    /**
     * Список точек, для которых рассчитывалась интерполяция в точке
     */
    private List<Unit> units;

    /**
     * Поле для хранения функции в runtime
     */
    private transient PolynomialSplineFunction f;

    public SplineAnalysisData() {
    }

    public SplineAnalysisData(double[] knots, List<double[]> polynomialCoefficients, List<Unit> units) {
        this.knots = knots;
        this.polynomialCoefficients = polynomialCoefficients;
        this.units = units;
        PolynomialFunction polynomials[] = new PolynomialFunction[polynomialCoefficients.size()];
        for (int i = 0; i < polynomialCoefficients.size();i++) {
            polynomials[i] = new PolynomialFunction(polynomialCoefficients.get(i));
        }
        this.f = new PolynomialSplineFunction(knots, polynomials);
    }

    public double[] getKnots() {
        return knots;
    }

    public List<double[]> getPolynomialCoefficients() {
        return polynomialCoefficients;
    }

    public List<Unit> getUnits() {
        return units;
    }

    public PolynomialSplineFunction getF() {
        return f;
    }
}