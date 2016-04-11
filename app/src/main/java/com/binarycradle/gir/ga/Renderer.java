package com.binarycradle.gir.ga;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Vlad on 13-Dec-15.
 */
public class Renderer {

    public Bitmap generateImageFromDna(List<Gene> dna, GAParameters parameters) {
        long timestamp = System.currentTimeMillis();
        Bitmap image = Bitmap.createBitmap((int)(parameters.getTargetImage().getWidth()), (int)(parameters.getTargetImage().getHeight()),
                Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(image);
//        graphics.setRenderingHint(
//                RenderingHints.KEY_ANTIALIASING,
//                RenderingHints.VALUE_ANTIALIAS_ON);
//        graphics.setRenderingHint(
//                RenderingHints.KEY_COLOR_RENDERING,
//                RenderingHints.VALUE_COLOR_RENDER_SPEED);

        drawBlackBackground(canvas, parameters);

        for (Gene gene : dna) {
            if (gene instanceof LinearShape) {
                drawLinearShape(canvas, LinearShape.class.cast(gene));
            } else {
                drawRoundShape(canvas, RoundShape.class.cast(gene));
            }
        }
//        drawLinearShape(canvas, LinearShape.class.cast(dna.get(0)));

        System.out.println("rendering took : " + (System.currentTimeMillis() - timestamp));
        return image;
    }

    private void drawLinearShape(Canvas canvas, LinearShape linearShape) {
        Path path = new Path();
        List<Point> points = linearShape.getPoints();
        path.moveTo((int) points.get(0).getX(), (int)(points.get(0).getY()));

        for (int i=1; i< points.size(); i++) {
            path.lineTo((int)(points.get(i).getX()), (int)(points.get(i).getY()));
        }

        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(linearShape.getColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawPath(path, paint);
    }

    private void drawRoundShape(Canvas canvas, RoundShape roundShape) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(roundShape.getColor());
        paint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(roundShape.getCenter().getX(), roundShape.getCenter().getY(), (int) (roundShape.getRadius()), paint);
    }

    private void drawBlackBackground(Canvas canvas, GAParameters parameters) {
        Paint paint = new Paint();
        paint.setAntiAlias(true);
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        canvas.drawRect(0, 0, (int) (parameters.getTargetImage().getWidth()), (int) (parameters.getTargetImage().getHeight()), paint);
    }

    private List<Point> getTestPath() {
        List<Point> path = new ArrayList<>();
        Point point = new Point(0, 0);
        path.add(point);
        point = new Point(1, 0);
        path.add(point);
        point = new Point(0, 1);
        path.add(point);
        return path;
    }

    private int getTestColor() {
        return Color.argb(50, 100, 100, 100);
    }

}
