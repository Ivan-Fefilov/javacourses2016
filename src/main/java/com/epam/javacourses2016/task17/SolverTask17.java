package com.epam.javacourses2016.task17;

import com.epam.javacourses2016.Point2D;
import com.epam.javacourses2016.Segment;

import java.awt.geom.Line2D;
import java.util.*;

/**
 * На плоскости задано N отрезков.
 * Найти точку (возможно несколько) пересечения двух отрезков, имеющую минимальную абсциссу.
 * Использовать класс TreeMap.
 */
public class SolverTask17 {

    /**
     * Осуществляет анализ переданных отрезков.
     *
     * @param segments Множество отрезков.
     * @return Множество точек пересечения, имеющих минимальную абсциссу.
     */
    public Set<Point2D> analyze(Set<Segment> segments) {
        if (segments.isEmpty() || segments.size() <= 1) {
            return null;
        }

        TreeMap<Point2D, Double> intersectionPoints = new TreeMap<>(new Comparator<Point2D>() {
            @Override
            public int compare(Point2D o1, Point2D o2) {
                double x1 = o1.getX();
                double x2 = o2.getX();
                double y1 = o1.getY();
                double y2 = o2.getY();
                if (x1 == x2) {
                    return Double.compare(y1, y2);
                } else{
                    return Double.compare(x1, x2);
                }
            }

        });

        for (Segment firstSegment : segments) {
            for (Segment secondSegment : segments) {
                if (!firstSegment.equals(secondSegment) && isIntersect(firstSegment, secondSegment)) {
                    Point2D point = findInterstionPoint(firstSegment, secondSegment);
                    intersectionPoints.put(point, point.getX());
                }
            }

        }

        Set minAbscissPoints = new HashSet();
        double minX = intersectionPoints.get(intersectionPoints.firstKey());
        for (Map.Entry<Point2D, Double> e : intersectionPoints.entrySet()) {
            if (e.getValue() == minX) {
                minAbscissPoints.add(e.getKey());
            } else {
                break;
            }
        }
        return minAbscissPoints;

    }

    public boolean isIntersect(Segment firstSegment, Segment secondSegment) {
        boolean isIntersect = Line2D.linesIntersect(firstSegment.getA().getX(), firstSegment.getA().getY(),
                firstSegment.getB().getX(), firstSegment.getB().getY(),
                secondSegment.getA().getX(), secondSegment.getA().getY(),
                secondSegment.getB().getX(), secondSegment.getB().getY());
        return isIntersect;

    }


    public Point2D findInterstionPoint(Segment firstSegment, Segment secondSegment) {
        double dx1 = firstSegment.getB().getX() - firstSegment.getA().getX();
        double dx2 = secondSegment.getB().getX() - secondSegment.getA().getX();
        double dy1 = firstSegment.getB().getY() - firstSegment.getA().getY();
        double dy2 = secondSegment.getB().getY() - secondSegment.getA().getY();

        double d = dy2 * dx1 - dx2 * dy1;

        double u1 = (dx2 * (firstSegment.getA().getY() - secondSegment.getA().getY()) - dy2 * (firstSegment.getA().getX() - secondSegment.getA().getX())) / d;

        double x = firstSegment.getA().getX() + dx1 * u1;
        double y = firstSegment.getB().getY() + dx2 * u1;
        if (u1 >= 0d && u1 <= 1d ) {
            return new Point2D(x, y);
        }
        return null;
    }

}
