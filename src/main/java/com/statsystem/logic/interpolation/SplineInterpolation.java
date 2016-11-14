package com.statsystem.logic.interpolation;

import com.statsystem.entity.*;
import org.apache.commons.math3.analysis.interpolation.*;

/**
 * Created by DELL on 15.11.2016.
 */
public class SplineInterpolation {

    public Analysis interpolite(Sample sampleX, Sample sampleY) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException{
        LinearInterpolator interpolator = new LinearInterpolator();
        PolynomialSplineFunction f = interpolator.interpolate(sampleX.getValues(), sampleY.getValues());

        double[] coeff = f.getKnots();

        Long id = Long.valueOf(1123215); //todo read or generate id, or make constructor Analysis(name, AnalysisType.NEWTON, dataList );
        String name = "Spline"; // todo read from UI or auto generate

        return InterpolationHelper.createResult(id, name, AnalysisType.SPLINE, coeff);
    }
}
