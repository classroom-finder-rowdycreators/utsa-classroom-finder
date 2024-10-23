package com.example.utsa_classroom_finder;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.utsa_classroom_finder.model.LocationMap;
import com.example.utsa_classroom_finder.model.checkLogin;

public class MainActivity extends AppCompatActivity {

    //hashmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!checkLogin.isLoggedIn(this)) {
            // User is not logged in, start LoginActivity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
        } else {
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_main);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });

            Button nextscreen = findViewById(R.id.button);
            nextscreen.setOnClickListener(
                    e -> {
                        Intent intent = new Intent(this, Mapview.class);
                        startActivity(intent);
                    }
            );


            //8 buttons to go to next screen
            Button MH, NPB, MS, BB, MB, JPL, FLN, ART;

            LocationMap map = new LocationMap();

            MH = findViewById(R.id.back);
            MH.setOnClickListener(
                    e -> {
                        //send intent tezxt to mapview with location name equal to MH
                        String locationName = "MH";
                        checkLogin.setLoggedIn(this, false);
                        Intent intent = new Intent(this, LoginActivity.class);
                        startActivity(intent);

                    }
            );




        }
    }


}