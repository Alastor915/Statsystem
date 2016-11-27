package com.statsystem.logic.interpolation;

import com.statsystem.entity.*;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.exception.*;

/**
 * Created by DELL on 15.11.2016.
 */
public class SplineInterpolation {

    public static PolynomialSplineFunction interpolite(Sample sample) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {

        SplineInterpolator interpolator = new SplineInterpolator(); //fixme это не ньютонская интерполяция
        PolynomialSplineFunction f = interpolator.interpolate(sample.getDates(), sample.getValues());

//        double[] coeff = f.getNewtonCoefficients();

        //Long id = Long.valueOf(1123214); //todo read or generate id, or make constructor Analysis(name, AnalysisType.NEWTON, dataList );
        //String name = "Newton"; // todo read from UI or db or auto generate

        return f;
        //return InterpolationHelper.createResult(id, name, AnalysisType.NEWTON, coeff);
    }
}
