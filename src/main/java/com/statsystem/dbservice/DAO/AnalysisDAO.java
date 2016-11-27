package com.statsystem.dbservice.DAO;

import com.statsystem.entity.Analysis;
import com.statsystem.entity.AnalysisType;
import org.hibernate.HibernateException;

public interface AnalysisDAO {
    long insertAnalysis(Analysis analysis) throws HibernateException;
    void updateAnalysis(Analysis analysis) throws HibernateException;
    void deleteAnalysis(Analysis analysis) throws HibernateException;
}
