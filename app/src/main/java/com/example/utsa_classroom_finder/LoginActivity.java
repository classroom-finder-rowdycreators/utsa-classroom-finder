package com.example.utsa_classroom_finder;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.ComponentActivity;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class LoginActivity extends ComponentActivity {
    //AssetManager assets;
    private Button login_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //assets = getAssets();
        setupButtons();
    }


    private int authenticate( String username, String password ) {
        Scanner scan;
        String str = "";
        String[] arr;
        int id =-1;
        boolean authenticated = false;
        File f = new File(getFilesDir().getAbsolutePath()+ "/login.txt");
        try {
            if(f.exists()) {
                scan = new Scanner(openFileInput("login.txt"));

                while (scan.hasNext()) {
                    str = scan.nextLine();
                    arr = str.split(",");
                    if (username.equalsIgnoreCase(arr[1]) && password.equals(arr[2])) {
                        id = Integer.parseInt(arr[0]);
                        return id;
                    }
                }
                scan.close();
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        return id;
    }

    private void setupButtons(){
        login_button = (Button) findViewById(R.id.login_button);
        Button registerButton =(Button)findViewById(R.id.registerButton);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText uText = (EditText) findViewById(R.id.login_username_input);
                EditText pText = (EditText) findViewById(R.id.login_password_input);
                int id = authenticate(uText.getText().toString(), pText.getText().toString());
                if( id > 0 ){
                    Intent intent = new Intent( LoginActivity.this, Mapview.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
                else{
                    uText.setText(""); // clears username field for user to reenter
                    pText.setText(""); // clears password field for user to reenter

                    uText.setError("Incorrect username and password combination");
                    pText.setError("Incorrect username and password combination");
                }
            }
        });

//        registerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
//                startActivity(intent);
//            }
//        });
    }
}

