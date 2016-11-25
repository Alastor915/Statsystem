/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbservice.execute;

import com.statsystem.dbservice.DAO.*;
import com.statsystem.dbservice.DAO.impl.*;
import com.statsystem.entity.*;
import com.statsystem.entity.impl.*;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

/**
 *
 * @author sereg
 */
public class DBService {
    
    private static ProjectDAO projectDAO = null;
    private static AnalysisDAO analysisDAO = null;
    private static SampleDAO sampleDAO = null;
    private static UnitDAO unitDAO = null;
    private static DBService instance = null;

    public static synchronized DBService getInstance(){
        if (instance == null)
            instance = new DBService();
        return instance;
    }
    
    public ProjectDAO getProjectDAO(){
        if (projectDAO == null)
            projectDAO = new ProjectDAOImpl();
        return projectDAO;
    }
    
    public AnalysisDAO getAnalysisDAO(){
        if (analysisDAO == null)
            analysisDAO = new AnalysisDAOImpl();
        return analysisDAO;
    }
    

    public SampleDAO getSampleDAO(){
        if (sampleDAO == null)
            sampleDAO = new SampleDAOImpl();
        return sampleDAO;
    }

    private UnitDAO getUnitDAO(){
        if (unitDAO == null)
            unitDAO = new UnitDAOImpl();
        return unitDAO;
    }

    public HashMap getData(Long id) throws SQLException, LazyInitializationException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        HashMap dataMap = new HashMap();
        Project project = (Project) session.createCriteria(Project.class).list().get((int) (id - 1));
        dataMap.put("проект", project);
        for (Sample sample: project.getSamples()){
            dataMap.put(sample.getName(), sample);
            for (Analysis analysis: sample.getAnalyses()){
                if (analysis.getSample().equals(sample)){
                    dataMap.put(analysis.getName(), analysis);
                    dataMap.put(analysis.getType().name(), analysis.getType());
                    AnalysisData data = analysis.getData();
                    if(data instanceof DistributionAnalysisData){
                        dataMap.put("функцияраспределения",((DistributionAnalysisData) data).getF());
                    } else if(data instanceof NewtonAnalysisData){
                        HashMap newton = new HashMap();
                        newton.put("центр", ((NewtonAnalysisData) data).getCenters());
                        newton.put("функция", ((NewtonAnalysisData) data).getF());
                        newton.put("коэффициент", ((NewtonAnalysisData) data).getNewtonCoefficients());
                        newton.put("точки", ((NewtonAnalysisData) data).getUnits());
                        dataMap.put("Ньютон", newton);
                    } else if(data instanceof SimpleAnalysisData){
                        dataMap.put("матожидание", ((SimpleAnalysisData) data).getValue());
                    } else if (data instanceof SplineAnalysisData){
                        HashMap spline = new HashMap();
                        spline.put("функция", ((SplineAnalysisData) data).getF());
                        spline.put("кнот", ((SplineAnalysisData) data).getKnots());
                        spline.put("полиномиальный_коэффициент", ((SplineAnalysisData) data).getPolynomialCoefficients());
                        spline.put("юнит", ((SplineAnalysisData) data).getUnits());
                        dataMap.put("сплайн", spline);
                    }
                }
            }
        }
        session.close();
        return dataMap;
    }

    public Project getProjectByIdWithDetails(long id) throws SQLException {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Project project = (Project) session.createCriteria(Project.class).list().get((int) (id - 1));
        return project;
    }


}