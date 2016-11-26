package com.statsystem.dbservice.DAO;

import com.statsystem.entity.Unit;
import org.hibernate.HibernateException;

public interface UnitDAO {
    void updateUnit(Unit unit) throws HibernateException;
}
