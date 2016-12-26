package com.statsystem.logic.statchars;

import com.statsystem.entity.*;
import java.util.Arrays;
import org.apache.commons.math3.analysis.UnivariateFunction;
import org.apache.commons.math3.linear.RealMatrix;
import org.apache.commons.math3.stat.correlation.PearsonsCorrelation;

/**
 * Created by Илья on 25.11.2016.
 *
 */
public class CorrelationFunction implements UnivariateFunction{

    private double[] values;

    private double[] quantity;

    public CorrelationFunction(double[] values, double[] quantity) {
        this.values = values;
        this.quantity = quantity;
    }

    public CorrelationFunction(Sample sample){
        PearsonsCorrelation correlation = new PearsonsCorrelation();
        double[][] data = new double[sample.getValues().length][2];
        for (int i=0;i<sample.getValues().length;i++){
            data[i][0]=sample.getDates()[i];
            data[i][1]=sample.getValues()[i];
        }
        RealMatrix correlationMatrix = correlation.computeCorrelationMatrix(data);
        int diagnumber = correlationMatrix.getColumnDimension()*2-1; //количество диагоналей
        double[][] mean = new double[diagnumber][2]; //значения и количество по диагоналям
        for (int i = 0; i< correlationMatrix.getColumnDimension(); i++){
            for (int j = 0; j<correlationMatrix.getRowDimension(); j++){
                //System.out.print(correlationMatrix.getEntry(i,j) + " ");
                //System.out.print((diagnumber/2+j-i) + " ");
                mean[(diagnumber/2)+j-i][0] += correlationMatrix.getEntry(i,j);
                mean[(diagnumber/2)+j-i][1]++;
            }
        }

        /*for (int i=0;i<diagnumber;i++){
            for (int j=0;j<data.length;j++){
                mean[diagnumber/2+j-i][0] += correlationMatrix.getData()[i][j];
                mean[diagnumber/2+j-i][1]++;
            }

        }*/
        values = new double[mean.length];
        quantity = new double[mean.length];
        for (int i=0;i<mean.length;i++){
            values[i] = mean[i][1]; //Ox
            quantity[i] = mean[i][0]/mean[i][1]; //Oy
        }
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
    public double value(double y) {
        for (int i = 0; i < values.length; i++) {
            if (values[i] == y) {
                return quantity[i];
            }
        }
        return 0;
    }

    @Override
    public String toString() {
        String res = "values: ";
        for (double value : values) {
            res += value + "  |||  ";
        }
        res += "\n quantities: ";
        for (int i = 0; i < values.length; i++) {
            res += quantity[i] + "  |||  ";
        }
        return res;
    }
}
