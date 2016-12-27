package com.statsystem.logic.interpolation;

import com.statsystem.entity.*;
import com.statsystem.entity.impl.NewtonAnalysisData;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.exception.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class NewtonInterpolation {
    private static int MAX_NUM = 5;

    public static NewtonAnalysisData interpolite(Sample sample) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException{
        double[] x = sample.getDates();
        double[] y = sample.getValues();
        DividedDifferenceInterpolator interpolator = new DividedDifferenceInterpolator();

        List<double[]> coeff = new ArrayList<>();
        List<double[]> center = new ArrayList<>();

        double[] maxElem;
        if (x.length%MAX_NUM == 0)
            maxElem = new double[x.length/MAX_NUM];
        else maxElem = new double[x.length/MAX_NUM+1];

        int p = 0;
        int i = 1;
        while (i < x.length){
            double[] x1 = new double[MAX_NUM+1];
            double[] y1 = new double[MAX_NUM+1];
            int j = 0;
            while (j <= MAX_NUM && i <= x.length){

                x1[j] = x[i-1];
                y1[j] = y[i-1];
                if (j == MAX_NUM) {
                    maxElem[p] = x1[j];
                    p++;
                }
                i++;
                j++;
            }
            i--;

            PolynomialFunctionNewtonForm functionNewtonForm;
            if (j <= MAX_NUM){
                functionNewtonForm = interpolator.interpolate(Arrays.copyOfRange(x1, 0, j), Arrays.copyOfRange(y1, 0, j));
            } else {
                functionNewtonForm = interpolator.interpolate(x1, y1);
            }
            coeff.add(functionNewtonForm.getNewtonCoefficients());
            center.add(functionNewtonForm.getCenters());
        }

        return new NewtonAnalysisData(coeff, center , new ArrayList<>(), maxElem);
    }
}
