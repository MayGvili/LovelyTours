package com.example.lovelytours;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatImageView;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Address;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.lovelytours.models.Location;
import com.example.lovelytours.models.Tour;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.UUID;

public class CreateTourActivity extends AppCompatActivity {


    private Bitmap imageBt;
    private TextInputEditText description, startPoint, destination, maxTourists, date, name, startTime, endTime;
    private AppCompatImageView image, camera, gallery, plus, minus;
    private Spinner time, duration;
    private DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
    LinearLayout registerContainer;
    Button save, limit;
    private boolean openByStartPoint = true;
    private Address stratAddress, endAddress;
    private String imageUrl;
    private long dateTimestamp;
    private Tour tour;


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

    private final ActivityResultLauncher<Intent> getLocationLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result ->{
                if (result.getResultCode() == Activity.RESULT_OK) {
                    if (openByStartPoint) {
                        stratAddress = result.getData().getParcelableExtra("address");
                        startPoint.setText(stratAddress.getAddressLine(0));
                    } else {
                        endAddress = result.getData().getParcelableExtra("address");
                        destination.setText(endAddress.getAddressLine(0));
                    }
                }
            });


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);
        tour = (Tour) getIntent().getSerializableExtra("tour");
        name = findViewById(R.id.name);
        description = findViewById(R.id.description);
        startPoint = findViewById(R.id.start_point);
        destination = findViewById(R.id.destination);
        maxTourists = findViewById(R.id.max_tourists);
        date = findViewById(R.id.date);
        startTime = findViewById(R.id.start_time);
        endTime = findViewById(R.id.end_time);
        gallery = findViewById(R.id.gallery);
        camera = findViewById(R.id.take_photo);
        image = findViewById(R.id.image);
        registerContainer = findViewById(R.id.register_tour_container);
        plus = findViewById(R.id.plus);
        minus = findViewById(R.id.minus);
        limit = findViewById(R.id.limit);

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
        save = findViewById(R.id.create);
        save.setOnClickListener(v->createTour());
        
        startTime.setOnClickListener(v-> openTimePicker(startTime));
        endTime.setOnClickListener(v-> openTimePicker(endTime));
        
        initilizeScreen();
    }

    private void initilizeScreen() {
        if(tour != null) {
            save.setVisibility(View.GONE);
            name.setText(tour.getName());
            name.setEnabled(false);
            startPoint.setText(tour.getStartLocation().getTitle());
            startPoint.setEnabled(false);
            destination.setText(tour.getDestination().getTitle());
            destination.setEnabled(false);
            description.setText(tour.getDescription());
            description.setEnabled(false);
            maxTourists.setText(String.valueOf(tour.getLimPeople()));
            maxTourists.setHint(getString(R.string.available_tickets));
            maxTourists.setEnabled(false);
            date.setText(dateFormat.format(tour.getDate()));
            date.setEnabled(false);
            startTime.setText(tour.getStartTime());
            startTime.setEnabled(false);
            endTime.setText(tour.getEndTime());
            endTime.setEnabled(false);
            Picasso.get().load(tour.getImage())
                    .centerCrop()
                    .fit()
                    .into(image);
            camera.setVisibility(View.GONE);
            gallery.setVisibility(View.GONE);
            save.setText(R.string.register_to_tour);
            save.setOnClickListener(v -> onRegisterToTourClicked());
            save.setEnabled(tour.getLimPeople() > 0);
            registerContainer.setVisibility(View.VISIBLE);
            limit.setText("0");
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int number = Integer.parseInt(limit.getText().toString());
                    if (number < tour.getLimPeople())
                    limit.setText(String.valueOf(number + 1));
                }
            });

            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int number = Integer.parseInt(limit.getText().toString() );
                    if (number > 0) {
                        limit.setText(String.valueOf(number - 1));
                    }
                }
            });

        }
    }

    private void onRegisterToTourClicked() {
        tour.setLimPeople(tour.getLimPeople() - 1);
        DataBaseManager.saveTour(tour, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CreateTourActivity.this, getText(R.string.registration_successfully), Toast.LENGTH_LONG).show();
                setResult(Activity.RESULT_OK);
                finish();
            }
        });
    }

    private void openTimePicker(TextInputEditText et) {
        Calendar calendar = Calendar.getInstance();

        // on below line we are getting our hour, minute.
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int minute = calendar.get(Calendar.MINUTE);

        // on below line we are initializing
        // our Time Picker Dialog
        TimePickerDialog timePickerDialog = new TimePickerDialog(
                this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hour, int min) {
                et.setText(hour + ":" + min);
            }},
                hour,
                minute,
                false);
        timePickerDialog.show();
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
        String id = UUID.randomUUID().toString();
        StorageReference imageRef = FirebaseStorage.getInstance().getReference().child("tours/" + id +".jpg");
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        imageBt.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        UploadTask uploadTask = imageRef.putBytes(imageBytes);
        uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {
                if (task.isSuccessful()){
                    imageRef.getDownloadUrl().addOnSuccessListener(uri->{
                        imageUrl = uri.toString();
                        saveTourTODataBase(id);
                    });
                } else {
                    AlertDialogManager.showMessage(CreateTourActivity.this, getString(R.string.error), task.getException().getMessage());
                }
            }
        });

    }

    private void saveTourTODataBase(String id) {
        Tour tour = new Tour(id, Integer.parseInt(maxTourists.getText().toString()), dateTimestamp, description.getText().toString(),
                imageUrl ,name.getText().toString() , startTime.getText().toString(),
                endTime.getText().toString(), new Location(stratAddress.getLatitude(), stratAddress.getLongitude(),startPoint.getText().toString()),
                new Location(endAddress.getLatitude(), endAddress.getLongitude(), destination.getText().toString()));

        DataBaseManager.saveTour(tour, new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(CreateTourActivity.this, getText(R.string.tour_created), Toast.LENGTH_SHORT).show();
                finish();
            }
        });
    }

    private void openMapScreen() {
        Intent intent = new Intent(this, FindLocationActivity.class);
        getLocationLauncher.launch(intent);
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
}