package com.statsystem.dbservice.DAO.impl;

import com.statsystem.dbservice.DAO.SampleDAO;
import com.statsystem.entity.Sample;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class SampleDAOImpl implements SampleDAO {

    private Session session;

    public SampleDAOImpl(Session session) {
        this.session = session;
    }

    @Override
    public long addSample(Sample sample) throws HibernateException {
        return 0;
    }

    @Override
    public void updateSample(Sample sample) throws HibernateException {

    }

    @Override
    public void deleteSample(Sample sample) throws HibernateException {

    }
}
