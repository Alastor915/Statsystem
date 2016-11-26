package com.statsystem.dbservice.DAO.impl;

import com.statsystem.dbservice.DAO.SampleDAO;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import org.hibernate.HibernateException;
import org.hibernate.Session;

import java.util.List;

public class SampleDAOImpl implements SampleDAO {

    private Session session;

    public SampleDAOImpl(Session session) {
        this.session = session;
    }

    @Override
    public long insertSample(Sample sample) throws HibernateException {
        long id = (Long) session.save(sample);
        List<Unit> data = sample.getData();
        if (!data.isEmpty()) {
            for (Unit unit : data) {
                session.save(unit);
            }
        }
        List<Analysis> analyses = sample.getAnalyses();
        if (!analyses.isEmpty()) {
            for (Analysis analysis : analyses) {
                session.save(analysis);
            }
        }
        return id;
    }

    @Override
    public void updateSample(Sample sample) throws HibernateException {
        session.update(sample);
    }

    @Override
    public void deleteSample(Sample sample) throws HibernateException {
        session.delete(sample);
        List<Unit> data = sample.getData();
        if (!data.isEmpty()) {
            for (Unit unit : data) {
                session.delete(unit);
            }
        }
        List<Analysis> analyses = sample.getAnalyses();
        if (!analyses.isEmpty()) {
            for (Analysis analysis : analyses) {
                session.delete(analysis);
            }
        }
    }
}
