package com.example.lovelytours;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.Spinner;

import com.example.lovelytours.models.Tour;
import com.google.android.material.textfield.TextInputEditText;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

public class CreateTourActivity extends AppCompatActivity {


    private Bitmap imageBt;
    private TextInputEditText description, startPoint, destination, maxTourists, date, name;
    private AppCompatImageView image, camera, gallery;
    private Spinner time, duration;

    private boolean openByStartPoint = true;
    private Address stratAddress, endAddress;
    private String imageUrl;
    private long dateTimestamp;


    private final ActivityResultLauncher<Intent> takePhotoLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == Activity.RESULT_OK) {
                    imageBt = (Bitmap) result.getData().getExtras().get("data");
                    image.setImageBitmap(imageBt);
                }

            });

    private ActivityResultLauncher<Intent> galleryLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == Activity.RESULT_OK) {
                    Uri uri = result.getData().getData();
                    try {
                        imageBt = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                        image.setImageBitmap(imageBt);
                    } catch (IOException e) {

                    }
                }

            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        startPoint = findViewById(R.id.start_point);
        destination = findViewById(R.id.destination);
        maxTourists = findViewById(R.id.max_tourists);
        date = findViewById(R.id.date);
        time = findViewById(R.id.time);
        duration = findViewById(R.id.duration);
        gallery = findViewById(R.id.gallery);
        camera = findViewById(R.id.take_photo);
        image = findViewById(R.id.image);

        gallery.setOnClickListener(V-> openGallery());
        camera.setOnClickListener(V-> openCamera());
        startPoint.setOnClickListener(V-> {
            openByStartPoint = true;
            openMapScreen();
        });
        destination.setOnClickListener(V-> {
            openByStartPoint = false;
            openMapScreen();
        });

        date.setOnClickListener(v -> openDatePicker());
        Button save = findViewById(R.id.create);
        save.setOnClickListener(v->createTour());
    }

    private void openDatePicker() {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getTodayDate());

        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);
        DatePickerDialog pickerDialog = new DatePickerDialog(this, (view, y, m, d) -> {
        Calendar cal = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        cal.setTimeInMillis(0);
        cal.set(Calendar.DAY_OF_MONTH, d);
        cal.set(Calendar.MONTH, m);
        cal.set(Calendar.YEAR, y);
        onDateSelected(cal.getTimeInMillis());
    }, year, month, day);

        pickerDialog.getDatePicker().setMinDate(System.currentTimeMillis());
        pickerDialog.show();

    }

    public static long getTodayDate() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        Calendar newCalendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        newCalendar.setTimeInMillis(0L);
        newCalendar.set(Calendar.YEAR, year);
        newCalendar.set(Calendar.MONTH, month);
        newCalendar.set(Calendar.DAY_OF_MONTH, day);

        return newCalendar.getTimeInMillis();
    }

    public static String dateToStr(long date) {
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        return dateFormat.format(date);
    }

    private void onDateSelected(long timeInMillis) {
        date.setText(dateToStr(timeInMillis));
        dateTimestamp = timeInMillis;

    }

    private void createTour() {
       // Tour tour = new Tour(maxTourists.getText().toString(), date.getText().toString(), description.getText().toString(),
              //  ,name.getText().toString() ,imageUrl, ti)
    }

    private void openMapScreen() {
        Intent intent = new Intent(this, FindLocationActivity.class);
        startActivityForResult(intent, 1);
    }

    private void openGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        galleryLauncher.launch(intent);

    }

    private void openCamera() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        takePhotoLauncher.launch(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (openByStartPoint) {
                stratAddress = data.getParcelableExtra("address");
                startPoint.setText(stratAddress.getAddressLine(0));
            } else {
                endAddress = data.getParcelableExtra("address");
                destination.setText(endAddress.getAddressLine(0));
            }
        }
    }
}