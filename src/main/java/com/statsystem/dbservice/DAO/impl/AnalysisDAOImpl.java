/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbservice.DAO.impl;

import com.statsystem.dbservice.DAO.AnalysisDAO;
import com.statsystem.entity.Analysis;
import com.statsystem.dbservice.execute.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author sereg
 */
public class AnalysisDAOImpl implements AnalysisDAO {

    public void addAnalysis(Analysis analysis) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(analysis);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }    

    public void updateAnalysis(Analysis analysis) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(analysis);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public Analysis getAnalysisById(Integer id) throws SQLException {
        Session session = null;
        Analysis analysis = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            analysis = session.load(Analysis.class, id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return analysis;
    }

    public List<Analysis> getAllAnalyses() throws SQLException {
        Session session = null;
        List<Analysis> analyses = new ArrayList<>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            analyses = session.createCriteria(Analysis.class).list();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return analyses;
    }

    public void deleteAnalysis(Analysis analysis) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(analysis);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }    
    }
}
