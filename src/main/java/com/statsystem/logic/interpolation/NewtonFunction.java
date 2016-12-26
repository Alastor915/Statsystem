package com.statsystem.logic.interpolation;

import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.analysis.differentiation.UnivariateDifferentiableFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunctionNewtonForm;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 24.12.2016.
 */
public class NewtonFunction  implements UnivariateFunction {
    private List<double[]> newtonCoefficients;
    private List<double[]> centers;

    private transient double [] maxEl = new double[centers.size()];

    private transient List<PolynomialFunctionNewtonForm> flist;

    public NewtonFunction(List<double[]> centers, List<double[]> newtonCoefficients, double [] maxEl) {
        this.centers = centers;
        this.newtonCoefficients = newtonCoefficients;
        flist = create(newtonCoefficients, centers);
        this.maxEl = maxEl;
    }

    private List<PolynomialFunctionNewtonForm> create(List<double[]> coef, List<double[]> center){
        List<PolynomialFunctionNewtonForm> flist = new ArrayList<>();
        for (int i=0; i < center.size(); i++){
            flist.add(new PolynomialFunctionNewtonForm(center.get(i), coef.get(i)));
        }
        return flist;
    }

    private int getNumOfElemList(double v){

        int i= 0;
        for (i= 0; i < this.maxEl.length; i++ ){
            if (v <= maxEl[i]){
                return i;
            }
        }
        return i;
    }
    @Override
    public double value(double v) {
        int numOfElemList = getNumOfElemList(v);
        return flist.get(numOfElemList).value(v);
    }
}
