package com.statsystem.dbservice.execute;


import com.statsystem.dbservice.DAO.ProjectDAO;
import com.statsystem.dbservice.DAO.impl.ProjectDAOImpl;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class DBServiceImpl implements DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "create";

    private final SessionFactory sessionFactory;

    public DBServiceImpl() {
        Configuration configuration;
        configuration = getH2Configuration(false, hibernate_hbm2ddl_auto);
        sessionFactory = createSessionFactory(configuration);
    }

    public DBServiceImpl(Boolean test,String auto) {
        Configuration configuration;
        configuration = getH2Configuration(test, auto);
        sessionFactory = createSessionFactory(configuration);
    }

    private Configuration getH2Configuration(Boolean test, String auto) {
        Configuration configuration = new Configuration();
        configuration.addAnnotatedClass(Analysis.class);
        configuration.addAnnotatedClass(Project.class);
        configuration.addAnnotatedClass(Sample.class);
        configuration.addAnnotatedClass(Unit.class);

        configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
        configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
        if (test) {
            configuration.setProperty("hibernate.connection.url", "jdbc:h2:./test");
        } else {
            configuration.setProperty("hibernate.connection.url", "jdbc:h2:./h2db");
        }
        configuration.setProperty("hibernate.connection.username", "stat");
        configuration.setProperty("hibernate.connection.password", "stat");
        configuration.setProperty("hibernate.show_sql", hibernate_show_sql);
        configuration.setProperty("hibernate.hbm2ddl.auto", auto);
        return configuration;
    }

    public Project getProject(long id) throws DBException {
        Session session = sessionFactory.openSession();
        try {
            ProjectDAO dao = new ProjectDAOImpl(session);
            return dao.getProject(id);
        } catch (HibernateException e) {
            throw new DBException(e);
        } finally {
            session.close();
        }
    }

    public List<Project> getAllProjects() throws DBException{
        Session session = sessionFactory.openSession();
        try {
            ProjectDAO dao = new ProjectDAOImpl(session);
            return dao.getAllProjects();
        } catch (HibernateException e) {
            throw new DBException(e);
        } finally {
            session.close();
        }
    }

    private static SessionFactory createSessionFactory(Configuration configuration) {
        StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder();
        builder.applySettings(configuration.getProperties());
        ServiceRegistry serviceRegistry = builder.build();
        return configuration.buildSessionFactory(serviceRegistry);
    }
}