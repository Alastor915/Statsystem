package com.statsystem.logic.interpolation;

import com.statsystem.entity.*;
import com.statsystem.entity.impl.NewtonAnalysisData;
import org.apache.commons.math3.analysis.interpolation.*;
import org.apache.commons.math3.analysis.polynomials.*;
import org.apache.commons.math3.exception.*;

import java.util.List;


public class NewtonInterpolation {

    public static NewtonAnalysisData interpolite(Sample sample) throws DimensionMismatchException, NumberIsTooSmallException, NonMonotonicSequenceException{
        DividedDifferenceInterpolator interpolator = new DividedDifferenceInterpolator();
        PolynomialFunctionNewtonForm functionNewtonForm = interpolator.interpolate(sample.getDates(), sample.getValues());
        return new NewtonAnalysisData(functionNewtonForm.getNewtonCoefficients(), functionNewtonForm.getCenters(), null);
    }
}
