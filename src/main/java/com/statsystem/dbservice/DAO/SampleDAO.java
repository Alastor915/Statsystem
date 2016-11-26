package com.statsystem.dbservice.DAO;

import com.statsystem.entity.Sample;
import org.hibernate.HibernateException;

public interface SampleDAO {
    long insertSample(Sample sample) throws HibernateException;
    void updateSample(Sample sample) throws HibernateException;
    void deleteSample(Sample sample) throws HibernateException;
}
