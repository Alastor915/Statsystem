package com.statsystem.logic.interpolation;


import com.statsystem.entity.*;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.exception.*;

/**
 * Created by DELL on 14.11.2016.
 */

//http://commons.apache.org/proper/commons-math/javadocs/api-3.6.1/org/apache/commons/math3/analysis/interpolation/DividedDifferenceInterpolator.html

public class NewtonInterpolation {

    public Analysis interpolite(Sample sampleX, Sample sampleY) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {

        DividedDifferenceInterpolator interpolator = new DividedDifferenceInterpolator();
        PolynomialFunctionNewtonForm f = interpolator.interpolate(sampleX.getValues(), sampleY.getValues());

        double[] coeff = f.getNewtonCoefficients();

        Long id = Long.valueOf(1123214); //todo read or generate id, or make constructor Analysis(name, AnalysisType.NEWTON, dataList );
        String name = "Newton"; // todo read from UI or auto generate

        return InterpolationHelper.createResult(id, name, AnalysisType.NEWTON, coeff);
    }
}

