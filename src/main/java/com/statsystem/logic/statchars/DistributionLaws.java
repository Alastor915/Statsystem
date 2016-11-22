package com.statsystem.logic.statchars;

import com.statsystem.entity.*;
import com.statsystem.entity.impl.DistributionAnalysisData;
import org.apache.commons.math3.distribution.*;
import org.apache.commons.math3.exception.NotStrictlyPositiveException;

import static com.statsystem.logic.statchars.StatChars.*;

/**
 * Created by Илья on 20.11.2016.
 */
public class DistributionLaws{

    public NormalDistribution findNormalDistribution(Sample sample) throws NotStrictlyPositiveException {
        double mean = getMeanValue(sample).getValue();
        double sd = getStandartDeviation(sample).getValue();
        //NormalDistribution fnd = new NormalDistribution(mean, sd);
        return new NormalDistribution(mean, sd);
    }

    public DistributionAnalysisData getDistributionFunction(Sample sample){
        DistributionAnalysisData distributionData = new DistributionAnalysisData();
        //создать объект
        return distributionData;
    }
}
