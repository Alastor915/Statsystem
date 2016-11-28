package com.statsystem.logic.interpolation;


import com.statsystem.entity.*;
import com.statsystem.entity.impl.SplineAnalysisData;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.exception.*;

import java.util.ArrayList;
import java.util.List;

public class SplineInterpolation {

    public static SplineAnalysisData interpolite(Sample sample) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
        SplineInterpolator interpolator = new SplineInterpolator();
        PolynomialSplineFunction f = interpolator.interpolate(sample.getDates(), sample.getValues());

        List<double[]> polynomialCoefficients = new ArrayList<>();
        for (PolynomialFunction polinomial:f.getPolynomials()){
            polynomialCoefficients.add(polinomial.getCoefficients());
        }

        return new SplineAnalysisData(f.getKnots(), polynomialCoefficients, null);
    }
}

