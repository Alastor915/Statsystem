package com.statsystem.entity.impl;

import com.statsystem.entity.AnalysisData;
import com.statsystem.entity.Unit;
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
    private transient List<PolynomialFunctionNewtonForm> f;

    public NewtonAnalysisData() {
    }

    public NewtonAnalysisData(List<double[]> newtonCoefficients, List<double[]> centers, List<Unit> units) {
        this.newtonCoefficients = newtonCoefficients;
        this.centers = centers;
        this.units = units;
        this.f = create(newtonCoefficients, centers);
    }

    private List<PolynomialFunctionNewtonForm> create(List<double[]> coef, List<double[]> center){
        List<PolynomialFunctionNewtonForm> flist = new ArrayList<>();
        for (int i=0; i < coef.size(); i++){
            flist.add(new PolynomialFunctionNewtonForm(coef.get(i), center.get(i)));
        }
        return flist;
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

    public List<PolynomialFunctionNewtonForm> getF() {
        return f;
    }
}
