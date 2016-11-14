package com.statsystem.logic.interpolation;

import com.statsystem.entity.Analysis;
import com.statsystem.entity.AnalysisType;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by DELL on 15.11.2016.
 */
public class InterpolationHelper {

    public static Analysis createResult(Long id, String name, AnalysisType type, double [] coeff){
        List<Unit> dataList = new ArrayList<>();

        for (double c : coeff) {
            dataList.add(new Unit(null, new Double(c)));
        }

        return new Analysis(id, name, type, dataList);
    }
}
