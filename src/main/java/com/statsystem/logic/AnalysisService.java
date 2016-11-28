package com.statsystem.logic;

import com.statsystem.entity.Sample;
import com.statsystem.entity.impl.*;
import com.statsystem.logic.interpolation.NewtonInterpolation;
import com.statsystem.logic.interpolation.SplineInterpolation;
import com.statsystem.logic.statchars.DistributionLaws;
import com.statsystem.logic.statchars.StatChars;

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

    static DistributionAnalysisData getDistributionFunction(Sample sample) {
        return DistributionLaws.getDistributionFunction(sample);
    }

    static DistributionAnalysisData getDistributionDiagramm(Sample sample, int intervalsQuantity) {
        return DistributionLaws.getDistributionDiagramm(sample, intervalsQuantity);
    }

    static NewtonAnalysisData getNewtonInterpolationFunction(Sample sample) {
        return NewtonInterpolation.interpolite(sample);
    }

    static SplineAnalysisData getSplineInterpolationFunction(Sample sample) {
        return SplineInterpolation.interpolite(sample);
    }
}
