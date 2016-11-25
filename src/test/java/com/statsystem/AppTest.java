package com.statsystem;

import com.statsystem.controller.MainController;
import com.statsystem.dbservice.DAO.impl.ProjectDAOImpl;
import com.statsystem.dbservice.execute.DBService;
import com.statsystem.dbservice.execute.HibernateUtil;
import com.statsystem.entity.*;
import com.statsystem.entity.impl.SplineAnalysisData;
import com.statsystem.logic.interpolation.SplineInterpolation;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import org.apache.commons.math3.analysis.polynomials.PolynomialFunction;
import org.apache.commons.math3.analysis.polynomials.PolynomialSplineFunction;
import org.hibernate.Session;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    public void testAddAndGetProject()
    {
        DBService dbService = DBService.getInstance();
        Project project = new Project("Первый проект");
        List<Project> projects = null;
        try {
            dbService.getProjectDAO().addProject(project);
            projects = dbService.getProjectDAO().getAllProjects();
        } catch (SQLException e) {
            assertTrue( false );
        }
        assertEquals( projects.get(projects.size()-1).getName(), "Первый проект");
    }

    public void testAddAndGetAll() throws SQLException {
        DBService dbService = DBService.getInstance();
        Project project = new Project("Первый проект");
        Analysis analysis = new Analysis("Первый расчет", AnalysisType.SPLINE);
        Sample sample = MainController.hardcode();
        sample.setName("Первая выборка");
        sample.setProject(project);
        PolynomialSplineFunction f = SplineInterpolation.interpolite(sample);
        PolynomialFunction polynomials[] = f.getPolynomials();
        ArrayList<double[]> fcoeff = new ArrayList<>();
        for (PolynomialFunction function : polynomials){
            fcoeff.add(function.getCoefficients());
        }
        ArrayList<Unit> results = new ArrayList<>();
        results.add(new Unit(1365441275000d, f.value(1365441275000d)));
        results.add(new Unit(1365441075000d, f.value(1365441075000d)));
        SplineAnalysisData data = new SplineAnalysisData(f.getKnots(), fcoeff, results);
        analysis.setData(data);
        analysis.setSample(sample);
        sample.getAnalyses().add(analysis);
        project.getSamples().add(sample);
        List<Project> projects = null;
        List<Sample> samples = null;
        List<Analysis> analyses = null;
        try {
            dbService.getProjectDAO().addProject(project);
            dbService.getSampleDAO().addSample(sample);
            dbService.getAnalysisDAO().addAnalysis(analysis);
            projects = dbService.getProjectDAO().getAllProjects();
            samples = dbService.getSampleDAO().getAllSamples();
            analyses = dbService.getAnalysisDAO().getAllAnalyses();

        } catch (SQLException e) {
            assertTrue( false );
        }
        assertEquals( projects.get(projects.size()-1).getName(), "Первый проект");
        assertEquals( samples.get(analyses.size()-1).getName(), "Первая выборка");
        assertEquals( analyses.get(analyses.size()-1).getName(), "Первый расчет");
        SplineAnalysisData dataFromDb =(SplineAnalysisData) analyses.get(0).getData();
        assertEquals(data.getKnots()[0], dataFromDb.getKnots()[0]);
        Project pr = dbService.getProjectByIdWithDetails(project.getId());
        HashMap testmap = dbService.getData(project.getId());
        System.out.print("mchf");
    }

    public void testAddAndGetSampleInProject()
    {
        DBService dbService = DBService.getInstance();
        Project project = new Project("Первый проект");
        Sample sample = new Sample("Супервыборка");
        sample.getData().add(new Unit(new Long(System.currentTimeMillis()).doubleValue(),2.345));
        project.getSamples().add(sample);
        List<Project> projects = null;
        try {
            dbService.getProjectDAO().addProject(project);
            projects = dbService.getProjectDAO().getAllProjects();
            project = dbService.getProjectDAO().getProjectById(projects.get(0).getId());
        } catch (SQLException e) {
            assertTrue( false );
        }
        assertFalse(project.getId() != -1L);
        assertEquals( project.getName(), "Первый проект");
        assertEquals( project.getSamples().get(0).getName(), "Супервыборка");
    }

    public void testGetDataById() throws SQLException {
        testAddAndGetAll();
        DBService dbService = DBService.getInstance();
        HashMap hashtest = dbService.getData((long) 1);
    }
}
