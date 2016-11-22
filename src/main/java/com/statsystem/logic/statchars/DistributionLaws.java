package com.statsystem.logic.statchars;

import com.statsystem.entity.*;
import org.apache.commons.math3.distribution.*;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;
import static com.statsystem.logic.statchars.StatChars.MeanValue;
import static com.statsystem.logic.statchars.StatChars.StandartDeviation;

/**
 * Created by Илья on 20.11.2016.
 */
public class DistributionLaws implements AnalysisData{

    public NormalDistribution FindNormalDistribution(Sample sample) throws NotStrictlyPositiveException {
        double mean = MeanValue(sample);
        double sd = StandartDeviation(sample);
        //NormalDistribution fnd = new NormalDistribution(mean, sd);
        return new NormalDistribution(mean, sd);
    }


}
