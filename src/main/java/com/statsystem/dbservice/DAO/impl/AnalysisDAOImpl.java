package com.statsystem.dbservice.DAO.impl;

import com.statsystem.dbservice.DAO.AnalysisDAO;
import com.statsystem.entity.Analysis;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class AnalysisDAOImpl implements AnalysisDAO {

    private Session session;

    public AnalysisDAOImpl(Session session) {
        this.session = session;
    }

    @Override
    public long insertAnalysis(String name) throws HibernateException {
        return 0;
    }

    @Override
    public void updateAnalysis(Analysis analysis) throws HibernateException {

    }

    @Override
    public void deleteAnalysis(Analysis analysis) throws HibernateException {

    }
}
