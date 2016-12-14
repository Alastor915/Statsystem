package com.statsystem.logic.interpolation;

import com.statsystem.entity.*;
import com.statsystem.entity.impl.NewtonAnalysisData;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.exception.*;

import java.util.ArrayList;
import java.util.List;


public class NewtonInterpolation {

    public static NewtonAnalysisData interpolite(Sample sample) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException{
        double[] x = sample.getDates();
        double[] y = sample.getValues();
        DividedDifferenceInterpolator interpolator = new DividedDifferenceInterpolator();

        List<double[]> coeff = new ArrayList<>();
        List<double[]> center = new ArrayList<>();

        int i = 0;
        while (i < x.length){
            double[] x1 = new double[10];
            double[] y1 = new double[10];
            int j = 0;
            while (j < 10 && i < x.length){
                i++;
                j++;
                x1[j] = x[j];
                y1[j] = y[j];
            }
            PolynomialFunctionNewtonForm functionNewtonForm = interpolator.interpolate(x1, y1);
            coeff.add(functionNewtonForm.getNewtonCoefficients());
            center.add(functionNewtonForm.getCenters());
        }

        return new NewtonAnalysisData(coeff, center , new ArrayList<>());
    }
}
