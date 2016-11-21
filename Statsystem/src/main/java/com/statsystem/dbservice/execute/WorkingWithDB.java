/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbservice.execute;

import com.statsystem.entity.*;
import com.statsystem.entity.impl.*;
import java.util.*;
import javafx.util.Pair;
import org.hibernate.*;

/**
 *
 * @author sereg
 */
public class WorkingWithDB {

    private SessionFactory sessionFactory;
    
    public List getProjects(){
        Session session = sessionFactory.openSession();
        String query = "select p from " + Project.class.getSimpleName() + " p";
        List<Project> projects = (List<Project>) session.createQuery(query).list();
        return projects;
    }
    
    public HashMap getData(Long id){
        Session session = sessionFactory.openSession();
        HashMap dataMap = new HashMap();
        Project project = session.load(Project.class, id);
        dataMap.put("проект", project);
        for (Sample sample: project.getSamples()){
            dataMap.put(sample.getName(), sample);
            for(Analysis analysis : sample.getAnalyses()){
                dataMap.put(analysis.getName(), analysis);
                dataMap.put(analysis.getType().name(), analysis.getType());
                AnalysisData data = analysis.getData();
                if(data instanceof DistributionAnalysisData){
                    dataMap.put("функцияраспределения",((DistributionAnalysisData) data).getDistribution());
                } else if(data instanceof NewtonAnalysisData){
                    HashMap newton = new HashMap();
                    newton.put("центр", ((NewtonAnalysisData) data).getCenters());
                    newton.put("функция", ((NewtonAnalysisData) data).getF());
                    newton.put("коэффициент", ((NewtonAnalysisData) data).getNewtonCoefficients());
                    newton.put("точки", ((NewtonAnalysisData) data).getUnits());
                    dataMap.put("Ньютон", newton);
                } else if(data instanceof SimleAnalysisData){
                    dataMap.put("матожидание", ((SimleAnalysisData) data).getValue());
                } else if (data instanceof SplineAnalysisData){
                    HashMap spline = new HashMap();
                    spline.put("", ((SplineAnalysisData) data).getF());
                    spline.put("", ((SplineAnalysisData) data).getKnots());
                    spline.put("", ((SplineAnalysisData) data).getPolynomialCoefficients());
                    spline.put("", ((SplineAnalysisData) data).getUnits());
                    dataMap.putAll(spline);
                }
            }
        }
        return dataMap;
    }
}
