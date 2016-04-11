package com.binarycradle.gir;

import android.content.ActivityNotFoundException;
import android.content.CursorLoader;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.View;
import android.widget.ImageView;

import com.binarycradle.gir.ga.FitnessCalculator;
import com.binarycradle.gir.ga.GAParameters;
import com.binarycradle.gir.ga.Generation;
import com.binarycradle.gir.ga.Individual;
import com.binarycradle.gir.ga.ShapeType;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;
    private static final String CAMERA_IMAGE_FILENAME = "image.jpeg";

    private ImageView originalImageView;
    private ImageView reproducedImageView;
    private View optionLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        originalImageView = ImageView.class.cast(findViewById(R.id.original_image_view));
        reproducedImageView = ImageView.class.cast(findViewById(R.id.reproduced_image_view));
        optionLayout = findViewById(R.id.option_layout);

    }

    public void onTakePhotoClicked(View view) {
        takePhoto();
    }

    public void onChoosePhotoClicked(View view) {
        choosePhoto();
    }

    private void choosePhoto() {
        Intent i = new Intent(Intent.ACTION_PICK,android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(i, REQUEST_IMAGE_PICK);
    }

    private void takePhoto() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, getCameraImageUri());

        try {
            startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
        } catch (ActivityNotFoundException e) {
        }
    }

    public Uri getCameraImageUri() {
        return Uri.fromFile(getCameraImageFile());
    }

    private File getCameraImageFile() {
        String filePath = getCameraImageFilePath();
        return new File(filePath);
    }

    private String getCameraImageFilePath() {
        return getExternalFilesDir(Environment.DIRECTORY_PICTURES).getAbsolutePath() + CAMERA_IMAGE_FILENAME;
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent imageReturnedIntent) {
        super.onActivityResult(requestCode, resultCode, imageReturnedIntent);
        if (resultCode == RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), getCameraImageUri());
                    setOriginalImageAndStartReproducing(bitmap);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            } else if (requestCode == REQUEST_IMAGE_PICK) {
                Uri selectedImageUri = imageReturnedIntent.getData();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImageUri);
                } catch (IOException e) {
                    e.printStackTrace();
                }
//                BitmapFactory.Options options = new BitmapFactory.Options();
//                options.inPreferredConfig = Bitmap.Config.ARGB_8888;
//                Bitmap bitmap = BitmapFactory.decodeFile(getPath(selectedImageUri), options);
                setOriginalImageAndStartReproducing(bitmap);
            }
        }

    }

    private String getPath(Uri uri) {
        String[] projection = { MediaStore.MediaColumns.DATA };
        CursorLoader cursorLoader = new CursorLoader(this,uri, projection, null, null,
                null);
        Cursor cursor =cursorLoader.loadInBackground();
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    private void setOriginalImageAndStartReproducing(Bitmap bitmap) {
        optionLayout.setVisibility(View.GONE);
        bitmap = cropAndScale(bitmap, 1000);
        originalImageView.setImageBitmap(bitmap);
        final Bitmap scaledBitmap = cropAndScale(bitmap, 200);
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                startReproducingImage(scaledBitmap);
            }
        });
        thread.start();
    }

    private Bitmap cropAndScale(Bitmap source, int scale) {
        int factor = source.getHeight() <= source.getWidth() ? source.getHeight() : source.getWidth();
        int longer = source.getHeight() >= source.getWidth() ? source.getHeight() : source.getWidth();
        int x = source.getHeight() >= source.getWidth() ? 0 : (longer - factor) / 2;
        int y = source.getHeight() <= source.getWidth() ? 0 : (longer - factor) / 2;
        source = Bitmap.createBitmap(source, x, y, factor, factor);
        source = Bitmap.createScaledBitmap(source, scale, scale, false);
        return source;
    }

    private void startReproducingImage(Bitmap bitmap) {

        GAParameters parameters = new GAParameters();
        parameters.setDnaLength(2);
        parameters.setMaxGeneSize(20);
        parameters.setElitism(true);
        parameters.setPopulationSize(2);
        parameters.setMatchSampleSize(1);
        parameters.setMutationRatio(0.015);
        parameters.setMaxMutation(0.15);
        parameters.setMaxRoundShapeRatio(0.7);
        parameters.setShapeType(ShapeType.LINEAR);
        parameters.setTargetImage(bitmap);

        FitnessCalculator fitnessCalculator = new FitnessCalculator();
        parameters.setMaxSumSquaredError(fitnessCalculator.getMaxSumSquaredError(parameters.getTargetImage()));

        Generation generation;
        Individual fittestIndividual;
        int count;
        long fitness;

//        parameters.setCurrentImageScale(1);
//        parameters.setScaledTargetImage(parameters.getTargetImage());//fitnessCalculator.getScaledImage(parameters.getTargetImage(), parameters.getCurrentImageScale()));

        generation = new Generation(parameters);
        count = 0;
        fittestIndividual = generation.getFittestIndividual(parameters);
        fitness = fittestIndividual.getFitness(parameters);
        printGeneration(generation, count, fittestIndividual, fitness, 0);
        while (generation.getFittestIndividual(parameters).getFitness(parameters) > 0) { //] && count < 100000) {
            long timestamp = System.currentTimeMillis();
            fittestIndividual = generation.getFittestIndividual(parameters);
            fitness = fittestIndividual.getFitness(parameters);
            generation = new Generation(generation, parameters);
            count++;
            printGeneration(generation, count, fittestIndividual, fitness, System.currentTimeMillis() - timestamp);
            if (count % 10 == 0) {
                final Bitmap fittestIndividualImage = fittestIndividual.getImage(parameters);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        reproducedImageView.setImageBitmap(fittestIndividualImage);
                    }
                });
            }
        }
    }

    private void printGeneration(Generation generation, int count, Individual fittestIndividual, long fitness, long timeSpent) {
        System.out.println("Generation " + count + " : ");
        System.out.println("Fittest Individual : " + fittestIndividual);
        System.out.println("Fitness : " + fitness);
        System.out.println("Took: " + timeSpent + " millis");
        System.out.println();
    }

}
