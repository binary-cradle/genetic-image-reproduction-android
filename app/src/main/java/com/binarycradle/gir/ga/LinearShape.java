package com.binarycradle.gir.ga;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by vladfatu on 05/04/2016.
 */
public class LinearShape extends Gene {

    private List<Point> points;

    public LinearShape(List<Point> points, int color) {
        super(color);
        this.points = points;
    }

    @Override
    protected void mutateStructure(Random random, GAParameters parameters) {
        mutatePoints(random, parameters);
        performPointCountMutation(parameters);
    }

    @Override
    public Gene clone() {
        return new LinearShape(new ArrayList<>(points), getColor());
    }

    @Override
    public void scale(double ratio) {
        List<Point> newPoints = new ArrayList<>();
        for (Point point : points) {
            newPoints.add(new Point((int) (point.getX() * ratio), (int) (point.getY() * ratio)));
        }
    }

    private void performPointCountMutation(GAParameters parameters) {
        Random random = new Random();
        IndividualGenerator generator = new IndividualGenerator();
        if (points.size() < parameters.getMaxGeneSize() && random.nextDouble() <= parameters.getMutationRatio()) {
            points.add(generator.generateRandomPoint(parameters));
        }
        if (points.size() > 1 && random.nextDouble() <= parameters.getMutationRatio()) {
            points.remove(random.nextInt(points.size()));
        }
    }

    private void mutatePoints(Random random, GAParameters parameters) {
        for (int i = 0; i < points.size(); i++) {
            points.set(i, getMutatedPoint(random, points.get(i), parameters));
        }
    }

    public List<Point> getPoints() {
        return points;
    }

}
