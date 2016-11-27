package com.statsystem.logic.interpolation;

import com.statsystem.entity.Sample;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;

/**
 * Created by DELL on 27.11.2016.
 */
public class LineInterpolation {
    PolynomialSplineFunction polynomialSplineFunction;

    public void interpolite(Sample sample) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException {
        LinearInterpolator interpolator = new LinearInterpolator();
        polynomialSplineFunction = interpolator.interpolate(sample.getDates(), sample.getValues());
    }

    public double getValue(double val) {
        return polynomialSplineFunction.value(val);
    }
}
