package com.statsystem.dbservice.DAO;

import com.statsystem.entity.Analysis;
import org.hibernate.HibernateException;

public interface AnalysisDAO {
    long insertAnalysis(String name) throws HibernateException;
    void updateAnalysis(Analysis analysis) throws HibernateException;
    void deleteAnalysis(Analysis analysis) throws HibernateException;
}
