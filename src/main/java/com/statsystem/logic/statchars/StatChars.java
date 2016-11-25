package com.statsystem.logic.statchars;

import com.statsystem.entity.*;
import com.statsystem.entity.impl.SimpleAnalysisData;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Created by Илья on 20.11.2016.
 */
public class StatChars{

    public static SimpleAnalysisData getMeanValue(Sample sample) {

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for( int i = 0; i < sample.getValues().length; i++) {
            stats.addValue(sample.getValues()[i]);
        }
        return new SimpleAnalysisData(stats.getMean());
    }

    public static SimpleAnalysisData getVariance(Sample sample) {

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for( int i = 0; i < sample.getValues().length; i++) {
            stats.addValue(sample.getValues()[i]);
        }
        return new SimpleAnalysisData(stats.getVariance());
    }

    public static SimpleAnalysisData getStandartDeviation(Sample sample) {

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for( int i = 0; i < sample.getValues().length; i++) {
            stats.addValue(sample.getValues()[i]);
        }
        return new SimpleAnalysisData(stats.getStandardDeviation());
    }
}
