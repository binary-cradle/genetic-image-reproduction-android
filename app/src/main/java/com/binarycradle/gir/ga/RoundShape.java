package com.binarycradle.gir.ga;

import java.util.Random;

/**
 * Created by vladfatu on 05/04/2016.
 */
public class RoundShape extends Gene {

    private Point center;
    private int radius;

    public RoundShape(Point center, int radius, int color) {
        super(color);
        this.center = center;
        this.radius = radius;
    }

    @Override
    protected void mutateStructure(Random random, GAParameters parameters) {
        center = getMutatedPoint(random, center, parameters);
        radius = getMutatedRadius(random, radius, parameters);
    }

    @Override
    public Gene clone() {
        return new RoundShape(center, radius, getColor());
    }

    @Override
    public void scale(double ratio) {
        radius *= ratio;
    }

    private int getMutatedRadius(Random random, int previousRadius, GAParameters parameters) {
        int limit = (int) (parameters.getTargetImage().getWidth() * parameters.getMaxRoundShapeRatio());
        int newRadius = previousRadius + getMutation(random, parameters, limit);
        if (newRadius <= 0) {
            return 0;
        } else if (newRadius >= limit) {
            return limit;
        } else {
            return newRadius;
        }
    }

    public Point getCenter() {
        return center;
    }

    public int getRadius() {
        return radius;
    }
}
