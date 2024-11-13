package com.example.utsa_classroom_finder;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class ScheduleUploaderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_schedule_uploader);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        EditText className = findViewById(R.id.className);
        className.setOnClickListener(
                e->{
                    String userInput = className.getText().toString();
                    Log.d("Debug","Class Accepted");
                }
        );

        EditText buildingName = findViewById(R.id.buildingName);
        buildingName.setOnClickListener(
                e->{
                    String userInput1 = buildingName.getText().toString();
                    Log.d("Debug","Building Accepted");
                }
        );

        EditText roomNumber = findViewById(R.id.roomNumber);
        roomNumber.setOnClickListener(
                e->{
                    String userInput2 = roomNumber.getText().toString();
                    Log.d("Debug","Room Number Accepted");
                }
        );

        Button submitSchedule = findViewById(R.id.submitSchedule);
        submitSchedule.setOnClickListener(
                e ->{
                    Log.d("Debug","Schedule Submitted");
                    className.setText("");
                    buildingName.setText("");
                    roomNumber.setText("");
                }
        );
    }

}