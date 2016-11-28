package com.statsystem.logic.statchars;

import com.statsystem.entity.*;
import java.util.Arrays;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * Created by Илья on 25.11.2016.
 */
public class CorrelationFunction implements UnivariateFunction{

    private double[] values;

    private double[] quantity;

    public CorrelationFunction(double[] values, double[] quantity) {
        this.values = values;
        this.quantity = quantity;
    }

    public CorrelationFunction getCorrelationFunction(Sample sample){
        PearsonsCorrelation correlation = new PearsonsCorrelation();
        double[][] data = new double[sample.getValues().length][2];
        for (int i=0;i<sample.getValues().length;i++){
            data[i][0]=sample.getDates()[i];
            data[i][1]=sample.getValues()[i];
        }
        int diagnumber = data.length*2-1; //количество диаголналей
        double[][] mean = new double[diagnumber][2]; //значения и количество по диагоналям
        RealMatrix correlationMatrix = correlation.computeCorrelationMatrix(data);

        for (int i=0;i<diagnumber;i++){
            for (int j=0;j<data.length;j++){
                mean[diagnumber/2+j-i][0] += correlationMatrix.getData()[i][j];
                mean[diagnumber/2+j-i][1]++;
            }

        }
        for (int i=0;i<mean.length;i++){
            values[i] = mean[i][1]; //Ox
            quantity[i] = mean[i][0]/mean[i][1]; //Oy
        }
        return new CorrelationFunction(values, quantity);
    }


    public double[] getValues() {
        return values;
    }

    public void setValues(double[] values) {
        this.values = values;
    }

    public double[] getQuantity() {
        return quantity;
    }

    public void setQuantity(double[] quantity) {
        this.quantity = quantity;
    }

    @Override
    public double value(double value) {
        return 0; // возвращает количество элементов выборки, меньших value.
    }


}
