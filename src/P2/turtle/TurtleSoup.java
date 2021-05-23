/* Copyright (c) 2007-2016 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package P2.turtle;

import java.util.*;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for(int i = 0; i < 4; i++)
        {
            turtle.turn(90);
            turtle.forward(sideLength);
        }
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return 180.0 * (sides - 2) / sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees, where 0 < angle < 180
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        throw new RuntimeException("implement me!");
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
        double angle = 180.0 - TurtleSoup.calculateRegularPolygonAngle(sides);
        for(int i = 0; i < sides; i++)
        {
            turtle.turn(angle);
            turtle.forward(sideLength);
        }
    }

    /**
     * Given the current direction, current location, and a target location, calculate the Bearing
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentBearing. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentBearing current direction as clockwise from north
     * @param currentX current location x-coordinate
     * @param currentY current location y-coordinate
     * @param targetX target point x-coordinate
     * @param targetY target point y-coordinate
     * @return adjustment to Bearing (right turn amount) to get to target point,
     *         must be 0 <= angle < 360
     */
    public static double calculateBearingToPoint(double currentBearing, int currentX, int currentY,
                                                 int targetX, int targetY) {
        double ret;
        if(targetX == currentX)
        {
            if(targetY == currentY)
            {
                ret = 0;
            }
            else if(targetY > currentY)
            {
                ret = 0 - currentBearing;
            }
            else
            {
                ret = 180 - currentBearing;
            }
        }
        else if(targetX > currentX)
        {
            ret = 90 - currentBearing - 180 * Math.atan((double)(targetY-currentY) / (targetX-currentX)) / Math.PI;
        }
        else
        {
            ret = 270 - currentBearing - 180 * Math.atan((double)(targetY-currentY) / (targetX-currentX)) / Math.PI;
        }

        while(ret >= 360 || ret < 0)
        {
            if(ret >= 360)
            {
                ret -= 360;
            }
            else
            {
                ret += 360;
            }
        }

        return ret;

    }

    /**
     * Given a sequence of points, calculate the Bearing adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateBearingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of Bearing adjustments between points, of size 0 if (# of points) == 0,
     *         otherwise of size (# of points) - 1
     */
    public static List<Double> calculateBearings(List<Integer> xCoords, List<Integer> yCoords) {
        if(xCoords.size() != yCoords.size())
        {
            throw new RuntimeException("xCoords.size() != yCoords.size()");
        }

        List<Double> ret = new ArrayList<Double>(xCoords.size());
        double currentBearing = 0;

        for(int i = 0; i < xCoords.size() - 1; i++)
        {
            double Bearing = calculateBearingToPoint(currentBearing,xCoords.get(i),yCoords.get(i),xCoords.get(i+1),yCoords.get(i+1));
            ret.add(Bearing);
            currentBearing += Bearing;
            while(currentBearing >= 360 || currentBearing < 0)
            {
                if(currentBearing >= 360)
                {
                    currentBearing -= 360;
                }
                else
                {
                    currentBearing += 360;
                }
            }
        }

        return ret;
    }
    
    /**
     * Given a set of points, compute the convex hull, the smallest convex set that contains all the points 
     * in a set of input points. The gift-wrapping algorithm is one simple approach to this problem, and 
     * there are other algorithms too.
     * 
     * @param points a set of points with xCoords and yCoords. It might be empty, contain only 1 point, two points or more.
     * @return minimal subset of the input points that form the vertices of the perimeter of the convex hull
     */
    public static Set<Point> convexHull(Set<Point> points) {
        LinkedHashSet<Point> convexHull = new LinkedHashSet<Point>();

        if(points.size() <= 2)
        {
            return points;
        }

        Point xmin = new Point(Double.MAX_VALUE, Double.MAX_VALUE);
        for(Point item : points)
        {
            if (item.x() < xmin.x() || (item.x() == xmin.x() && item.y() < xmin.y()))
                xmin = item;
        }
        Point nowPoint = xmin, tempPoint = xmin;
        double nowAngle = 0, minAngle = 360, tempAngle = 0;
        double distance;
        double maxdistance = -1;
        do{
            convexHull.add(nowPoint);
            for(Point item : points)
            {
                if (!convexHull.contains(item) || item == xmin)
                {
                    tempAngle = calculateBearingToPoint(nowAngle, (int)Math.round(nowPoint.x()), (int)Math.round(nowPoint.y()), (int)Math.round(item.x()), (int)Math.round(item.y()));
                    distance = (item.x() - nowPoint.x())*(item.x() - nowPoint.x()) + (item.y() - nowPoint.y())*(item.y() - nowPoint.y());
                    if(tempAngle < minAngle || ((tempAngle == minAngle) && (distance > maxdistance)))
                    {
                        minAngle = tempAngle;
                        tempPoint = item;
                        maxdistance = distance;
                    }
                }
            }
            minAngle = 360;
            nowAngle = minAngle;
            nowPoint = tempPoint;
        }while(nowPoint != xmin);

        return convexHull;
    }
    
    /**
     * Draw your personal, custom art.
     * 
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        for(int i = 1; i <= 20; i++)
        {
            turtle.turn(90);
            drawSquare(turtle,10*i);
        }
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     * 
     * @param args unused
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();

        drawPersonalArt(turtle);

        // draw the window
        turtle.draw();
    }
}
