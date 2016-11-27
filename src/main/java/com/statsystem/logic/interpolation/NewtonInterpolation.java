package com.statsystem.logic.interpolation;

import com.statsystem.entity.*;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.exception.*;

/**
 * 6.
 */
public class NewtonInterpolation {
    PolynomialFunctionNewtonForm functionNewtonForm;

     public void interpolite(Sample sample) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException{
        DividedDifferenceInterpolator interpolator = new DividedDifferenceInterpolator();
        functionNewtonForm = interpolator.interpolate(sample.getDates(), sample.getValues());
    }

    public double getValue(double val) {
        return functionNewtonForm.value(val);
    }
}
