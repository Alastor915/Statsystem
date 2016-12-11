package com.statsystem.logic;

import com.statsystem.entity.Sample;
import com.statsystem.entity.impl.*;
import com.statsystem.logic.interpolation.NewtonInterpolation;
import com.statsystem.logic.interpolation.SplineInterpolation;
import com.statsystem.logic.statchars.DistributionLaws;
import com.statsystem.logic.statchars.StatChars;
import org.apache.commons.math3.exception.DimensionMismatchException;
import org.apache.commons.math3.exception.NonMonotonicSequenceException;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import org.apache.commons.math3.exception.NumberIsTooSmallException;

public interface AnalysisService {

    static SimpleAnalysisData getMeanValue(Sample sample) {
        return StatChars.getMeanValue(sample);
    }

    static SimpleAnalysisData getVariance(Sample sample) {
        return StatChars.getVariance(sample);
    }

    static CorrelationAnalysisData getCorrelationFunction(Sample sample) {
        return StatChars.getCorrelationFunction(sample);
    }

    static DistributionAnalysisData getDistributionFunction(Sample sample) throws NotStrictlyPositiveException {
        return DistributionLaws.getDistributionFunction(sample);
    }

    static DistributionAnalysisData getDistributionDiagramm(Sample sample, int intervalsQuantity)
            throws NotStrictlyPositiveException {
        return DistributionLaws.getDistributionDiagramm(sample, intervalsQuantity);
    }

    static NewtonAnalysisData getNewtonInterpolationFunction(Sample sample) throws DimensionMismatchException,
            NumberIsTooSmallException, NonMonotonicSequenceException{
        return NewtonInterpolation.interpolite(sample);
    }

    static SplineAnalysisData getSplineInterpolationFunction(Sample sample) throws DimensionMismatchException,
            NumberIsTooSmallException, NonMonotonicSequenceException {
        return SplineInterpolation.interpolite(sample);
    }
}
