package com.statsystem;

import com.statsystem.db.ProjectTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({ ProjectTest.class })
public class AppTest {

//    public void testGetAll() throws SQLException {
//        DBServiceImpl dbService = DBServiceImpl.getInstance();
//        Project project = new Project("Первый проект");
//        Analysis analysis = new Analysis("Первый расчет", AnalysisType.SPLINE);
//        Sample sample = MainController.hardcode();
//        sample.setName("Первая выборка");
//        sample.setProject(project);
//        PolynomialSplineFunction f = SplineInterpolation.interpolite(sample);
//        PolynomialFunction polynomials[] = f.getPolynomials();
//        ArrayList<double[]> fcoeff = new ArrayList<>();
//        for (PolynomialFunction function : polynomials){
//            fcoeff.add(function.getCoefficients());
//        }
//        ArrayList<Unit> results = new ArrayList<>();
//        results.add(new Unit(1365441275000d, f.value(1365441275000d)));
//        results.add(new Unit(1365441075000d, f.value(1365441075000d)));
//        SplineAnalysisData data = new SplineAnalysisData(f.getKnots(), fcoeff, results);
//        analysis.setData(data);
//        analysis.setSample(sample);
//        sample.getAnalyses().add(analysis);
//        project.getSamples().add(sample);
//        List<Project> projects = null;
//        List<Sample> samples = null;
//        List<Analysis> analyses = null;
//        try {
//            dbService.getProjectDAO().addProject(project);
//            dbService.getSampleDAO().addSample(sample);
//            dbService.getAnalysisDAO().addAnalysis(analysis);
//            projects = dbService.getProjectDAO().getAllProjects();
//            samples = dbService.getSampleDAO().getAllSamples();
//            analyses = dbService.getAnalysisDAO().getAllAnalyses();
//
//        } catch (SQLException e) {
//            assertTrue( false );
//        }
//        assertEquals( projects.get(projects.size()-1).getName(), "Первый проект");
//        assertEquals( samples.get(analyses.size()-1).getName(), "Первая выборка");
//        assertEquals( analyses.get(analyses.size()-1).getName(), "Первый расчет");
//        SplineAnalysisData dataFromDb =(SplineAnalysisData) analyses.get(0).getData();
//        assertEquals(data.getKnots()[0], dataFromDb.getKnots()[0]);
//    }
//
//    public void testAddAndGetSampleInProject()
//    {
//        DBServiceImpl dbService = DBServiceImpl.getInstance();
//        Project project = new Project("Первый проект");
//        Sample sample = new Sample("Супервыборка");
//        sample.getData().add(new Unit(new Long(System.currentTimeMillis()).doubleValue(),2.345));
//        project.getSamples().add(sample);
//        List<Project> projects = null;
//        try {
//            dbService.getProjectDAO().addProject(project);
//            projects = dbService.getProjectDAO().getAllProjects();
//            project = dbService.getProjectDAO().getProjectById(1L);
//        } catch (SQLException e) {
//            assertTrue( false );
//        }
//        assertEquals( project.getName(), "Первый проект");
//        assertFalse(project.getSamples().isEmpty());
//        assertEquals( project.getSamples().get(0).getName(), "Первая выборка");
//        assertEquals(project.getSamples().get(0).getData().get(0).getDate(), 2134.0);
//    }
}
