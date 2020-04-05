package com.company.game.misc;

import java.util.Arrays;


public class Vector3 {
    double[] val = new double[3];

    Vector3() {
        this(0, 0, 0);
    }

    Vector3(double x, double y, double z) {
        val[0] = x;
        val[1] = y;
        val[2] = z;
    }

    public Vector3 add(Vector3 vector) {
        val[0] += vector.X();
        val[1] += vector.Y();
        val[2] += vector.Z();
        return this; // method chaining would be very useful
    }

    @Override
    public String toString() {
        return "" + val[0] + "," + val[1] + "," + val[2];
    }

    public static Vector3 ParseVector(String strVector) {
        String[] nums = strVector.split(",");
        return new Vector3(Double.parseDouble(nums[0]), Double.parseDouble(nums[1]), Double.parseDouble(nums[2]));
    }

    public double X() {
        return val[0];
    }

    public double Y() {
        return val[1];
    }

    public double Z() {
        return val[2];
    }
}
