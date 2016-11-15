/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.statsystem.dbService.DAO.impl;

import com.statsystem.dbService.DAO.SampleDAO;
import com.statsystem.entity.Sample;
import com.statsystem.dbService.execute.HibernateUtil;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;
import org.hibernate.Session;

/**
 *
 * @author sereg
 */
public class SampleDAOImpl implements SampleDAO {

    public void addSample(Sample sample) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(sample);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public void updateSample(Sample sample) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(sample);
            session.getTransaction().commit();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
    }

    public Sample getSampleById(Integer id) throws SQLException {
        Session session = null;
        Sample sample = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            sample =(Sample) session.load(Sample.class, id);
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return sample;
    }

    public List getAllSamples() throws SQLException {
        Session session = null;
        List<Sample> samples = new ArrayList<Sample>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            samples = session.createCriteria(Sample.class).list();
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, e);
        } finally {
            if (session != null && session.isOpen()){
                session.close();
            }
        }
        return samples;
    }

    public void deleteSample(Sample sample) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(sample);
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
