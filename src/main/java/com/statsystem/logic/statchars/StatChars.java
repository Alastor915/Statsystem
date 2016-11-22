package com.statsystem.logic.statchars;

import com.statsystem.entity.*;
import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;

/**
 * Created by Илья on 20.11.2016.
 */
public class StatChars implements AnalysisData{

    public static double MeanValue(Sample sample) {

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for( int i = 0; i < sample.getValues().length; i++) {
            stats.addValue(sample.getValues()[i]);
        }
        return stats.getMean();
    }

    public static double Variance(Sample sample) {

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for( int i = 0; i < sample.getValues().length; i++) {
            stats.addValue(sample.getValues()[i]);
        }
        return stats.getVariance();
    }

    public static double StandartDeviation(Sample sample) {

        DescriptiveStatistics stats = new DescriptiveStatistics();
        for( int i = 0; i < sample.getValues().length; i++) {
            stats.addValue(sample.getValues()[i]);
        }
        return stats.getStandardDeviation();
    }
}
