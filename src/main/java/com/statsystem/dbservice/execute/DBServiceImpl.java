package com.statsystem.dbservice.execute;


import com.statsystem.dbservice.DAO.AnalysisDAO;
import com.statsystem.dbservice.DAO.ProjectDAO;
import com.statsystem.dbservice.DAO.SampleDAO;
import com.statsystem.dbservice.DAO.UnitDAO;
import com.statsystem.dbservice.DAO.impl.AnalysisDAOImpl;
import com.statsystem.dbservice.DAO.impl.ProjectDAOImpl;
import com.statsystem.dbservice.DAO.impl.SampleDAOImpl;
import com.statsystem.dbservice.DAO.impl.UnitDAOImpl;
import com.statsystem.entity.Analysis;
import com.statsystem.entity.Project;
import com.statsystem.entity.Sample;
import com.statsystem.entity.Unit;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public class DBServiceImpl implements DBService {
    private static final String hibernate_show_sql = "true";
    private static final String hibernate_hbm2ddl_auto = "validate";

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
            throw new DBException("Не удалось загрузить проект с идентификатором " + id, e);
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
            throw new DBException("Не удалось получить список проектов ", e);
        } finally {
            session.close();
        }
    }

    public long insertProject(Project project) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ProjectDAO dao = new ProjectDAOImpl(session);
            long result = dao.insertProject(project);
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при добавлении проекта: "+ project.toString(), e);
        } finally {
            session.close();
        }
    }

    public long insertSample(Sample sample) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SampleDAO dao = new SampleDAOImpl(session);
            long result = dao.insertSample(sample);
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при добавлении выборки: "+ sample.toString(), e);
        } finally {
            session.close();
        }
    }

    public long insertAnalysis(Analysis analysis) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            AnalysisDAO dao = new AnalysisDAOImpl(session);
            long result = dao.insertAnalysis(analysis);
            transaction.commit();
            return result;
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при добавлении результатов расчета: "+ analysis.toString(), e);
        } finally {
            session.close();
        }
    }

    public void updateProject(Project project) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ProjectDAO dao = new ProjectDAOImpl(session);
            dao.updateProject(project);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при обновлении проекта: "+ project.toString(), e);
        } finally {
            session.close();
        }
    }

    public void updateSample(Sample sample) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SampleDAO dao = new SampleDAOImpl(session);
            dao.updateSample(sample);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при обновлении выборки: "+ sample.toString(), e);
        } finally {
            session.close();
        }
    }

    public void updateAnalysis(Analysis analysis) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            AnalysisDAO dao = new AnalysisDAOImpl(session);
            dao.updateAnalysis(analysis);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при обновлении результатов расчета: "+ analysis.toString(), e);
        } finally {
            session.close();
        }
    }

    public void updateUnit(Unit unit) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            UnitDAO dao = new UnitDAOImpl(session);
            dao.updateUnit(unit);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при обновлении элемента выборки: "+ unit.toString(), e);
        } finally {
            session.close();
        }
    }


    public void deleteProject(Project project) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            ProjectDAO dao = new ProjectDAOImpl(session);
            dao.deleteProject(project);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при удалении проекта: "+ project.toString(), e);
        } finally {
            session.close();
        }
    }

    public void deleteSample(Sample sample) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            SampleDAO dao = new SampleDAOImpl(session);
            dao.deleteSample(sample);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при удалении выборки: "+ sample.toString(), e);
        } finally {
            session.close();
        }
    }

    public void deleteAnalysis(Analysis analysis) throws DBException{
        Session session = sessionFactory.openSession();
        Transaction transaction = null;
        try {
            transaction = session.beginTransaction();
            AnalysisDAO dao = new AnalysisDAOImpl(session);
            dao.deleteAnalysis(analysis);
            transaction.commit();
        } catch (HibernateException e) {
            if (transaction != null)
                transaction.rollback();
            throw new DBException("Ошибка при удалении результатов расчета: "+ analysis.toString(), e);
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