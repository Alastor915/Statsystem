package com.statsystem.dbservice.DAO.impl;

import com.statsystem.dbservice.DAO.UnitDAO;
import com.statsystem.entity.Unit;
import org.hibernate.HibernateException;
import org.hibernate.Session;

public class UnitDAOImpl implements UnitDAO {

    private Session session;

    public UnitDAOImpl(Session session) {
        this.session = session;
    }


    @Override
    public void updateUnit(Unit unit) throws HibernateException {

    }

    @Override
    public void deleteUnit(Unit unit) throws HibernateException {

    }
}
