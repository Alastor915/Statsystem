package com.statsystem.logic.statchars;

import com.statsystem.entity.*;
import java.util.Arrays;
import org.apache.commons.math3.analysis.UnivariateFunction;

public class DistributionFunction implements UnivariateFunction{

    private double[] values;

    private double[] quantity;

    public DistributionFunction() {
    }

    public DistributionFunction(double[] values, double[] quantity) {
        this.values = values;
        this.quantity = quantity;
    }

    public DistributionFunction(Sample sample){
        double[] variational = new double[sample.getValues().length];
        System.arraycopy(sample.getValues(),0, variational,0,sample.getValues().length);
        Arrays.sort(variational);
        int norepsnumber = 0; //int quantnumber = 0;
        values = new double[variational.length];
        quantity = new double[variational.length];
        values[0] = variational[0];
        quantity[0] = 1;
        for (int i=1;i<variational.length;i++){
            if (variational[i]!=variational[i-1]) {
                norepsnumber++; //quantnumber++;
                values[norepsnumber] = variational[i];
                quantity[norepsnumber] +=  quantity[norepsnumber-1] + 1;
            } else {
                quantity[norepsnumber]++;
            }
        }

        for (int i=0;i < quantity.length;i++) {
            quantity[i]/=variational.length;
        }
    }

    public DistributionFunction getBarChart(Sample sample, int intervalsquantity){ //Гистограмма

        double[] variational = new double[sample.getValues().length];
        System.arraycopy(sample.getValues(),0, variational,0,sample.getValues().length);
        Arrays.sort(variational);
        if (intervalsquantity==-1) { //Если выбрано, что значение по умолчанию, то по формуле Стерджесса k=1+[log2(n)]
            intervalsquantity = stearj(variational.length);
        }
        double gistlength = (variational[variational.length-1]-variational[0]);
        double oneintervalvalue = gistlength/intervalsquantity;
        double pos = variational[0]+oneintervalvalue;
        int thisintervalnumber=0;
        while (thisintervalnumber<intervalsquantity) {
            values[thisintervalnumber]=pos;
            int n = 0;
            for (int i=0;i<variational.length;i++){
                if ((variational[i]>=pos-oneintervalvalue)&&(variational[i]<pos)) {n++;}
            }
            quantity[thisintervalnumber]=n/(oneintervalvalue*variational.length);
            thisintervalnumber++;
            pos+=oneintervalvalue;
        }
        return new DistributionFunction(values, quantity);
    }

    private static int stearj(double x) {
        double s = Math.log(x)/Math.log(2.0); // логарифм по основанию 2
        if ((s-(int)s)>=0.5) {return ((int)(s)+2);}
        else {return ((int)(s)+1);}
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
        double res = 0;
        int i = 0;
        while (i < values.length && value > values[i]){
            if(values[i] == 0)
                return 1;
            res = quantity[i];
            i++;
        }
        return res;
    }

    @Override
    public String toString() {
        String res = "values: ";
        for (int i = 0; i < values.length; i++) {
            res += values[i] + "  |||  ";
        }
        res += "\n quantities: ";
        for (int i = 0; i < values.length; i++) {
            res += quantity[i] + "  |||  ";
        }
        return res;
    }
}
