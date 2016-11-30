package com.epam.javacourses2016.task15;

import org.testng.annotations.Test;

/**
 * Created by kodoo on 13.11.16.
 */
public class SolverTask15Test {
    @Test
    public void testAnalyze(Double[] arr) throws Exception {
        SolverTask15 task15 = new SolverTask15();
        Set<Point2D> points = convertToSet(arr);
        File out = Files.createTempFile("task15", "out").toFile();
        SolverTask15.IFileWithLines analyze = task15.analyze(points, out);
        Assert.assertEquals(analyze.getLines(), readFile(out));
        //TODO: test
    }

    private Set<SolverTask15.ILine> readFile(File file) {
        Set<SolverTask15.ILine> lines = new TreeSet<>();
        if (file.length() != 0) {
            try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(file))) {
                lines = (Set<SolverTask15.ILine>) objectInputStream.readObject();
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return lines;
    }

    private Set<Point2D> convertToSet(Double[] arr) {
        Set<Point2D> set = new HashSet<>();
        for (int i = 0; i < arr.length - 1; i++) {
            set.add(new Point2D(arr[i], arr[i + 1]));
            i++;
        }
        return set;
    }

    @DataProvider(name = "points")
    private Object[][] points() {
        return new Object[][]{
                {new Double[]{1.0, 1.0, 3.0, 1.0, 0.0, 0.0, 4.0, 2.0}, new Double[]{2.0, 1.0}},
                {new Double[]{1.0, 1.0, 3.0, 1.0, 0.0, 0.0, 4.0, 2.0}, new Double[]{2.0, 1.0}},
        };
    }

}