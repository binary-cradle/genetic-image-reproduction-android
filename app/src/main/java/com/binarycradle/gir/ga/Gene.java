package com.binarycradle.gir.ga;

import android.graphics.Color;

import java.util.Random;

/**
 * Created by Vlad on 13-Dec-15.
 */
public abstract class Gene implements MutateableEntity {

    private int color;

    public Gene(int color) {
        this.color = color;
    }

    @Override
    public void mutate(GAParameters parameters) {
        Random random = new Random();
        mutateColor(random, parameters);
        mutateStructure(random, parameters);
    }

    protected abstract void mutateStructure(Random random, GAParameters parameters);

    public abstract Gene clone();

    public abstract void scale(double ratio);

    protected Point getMutatedPoint(Random random, Point point, GAParameters parameters) {
        int x = point.getX();
        int y = point.getY();
        boolean changed = false;
        if (random.nextDouble() <= parameters.getMutationRatio()) {
            x = getNewCoordinate(random, parameters, x, parameters.getTargetImage().getWidth() + 1);
            changed = true;
        }
        if (random.nextDouble() <= parameters.getMutationRatio()) {
            y = getNewCoordinate(random, parameters, x, parameters.getTargetImage().getHeight() + 1);
            changed = true;
        }
        if (changed) {
            return new Point(x, y);
        } else {
            return point;
        }
    }

    private int getNewCoordinate(Random random, GAParameters parameters, int previousCoordinate, int limit) {
        int newCoordinate = previousCoordinate + getMutation(random, parameters, limit);
        if (newCoordinate < 0) {
            return limit + newCoordinate;
        } else {
            return newCoordinate % limit;
        }
    }

    private void mutateColor(Random random, GAParameters parameters) {
        int newRed = Color.red(color);
        int newGreen = Color.green(color);
        int newBlue = Color.blue(color);
        int newAlpha = Color.alpha(color);
        boolean changed = false;
        if (random.nextDouble() <= parameters.getMutationRatio()) {
            newRed = getNewColorProperty(random, parameters, Color.red(color));
            changed = true;
        }
        if (random.nextDouble() <= parameters.getMutationRatio()) {
            newGreen = getNewColorProperty(random, parameters, Color.green(color));
            changed = true;
        }
        if (random.nextDouble() <= parameters.getMutationRatio()) {
            newBlue = getNewColorProperty(random, parameters, Color.blue(color));
            changed = true;
        }
        if (random.nextDouble() <= parameters.getMutationRatio()) {
            newAlpha = getNewColorProperty(random, parameters, Color.alpha(color));
            changed = true;
        }
        if (changed) {
            color = Color.argb(newAlpha, newRed, newGreen, newBlue);
        }
    }

    private int getNewColorProperty(Random random, GAParameters parameters, int previousColorProperty) {
        int newColorProperty = previousColorProperty + getMutation(random, parameters, 255);
        if (newColorProperty <= 0) {
            return 0;
        } else if (newColorProperty >= 255) {
            return 255;
        } else {
            return newColorProperty;
        }
    }

    protected int getMutation(Random random, GAParameters parameters, int limit) {
        int maxMutation = parameters.getMaxMutation(limit);
        return random.nextInt(maxMutation * 2) - maxMutation;
    }

    public int getColor() {
        return color;
    }
}
