package com.example.utsa_classroom_finder;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.activity.ComponentActivity;

import com.example.utsa_classroom_finder.model.checkLogin;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends ComponentActivity {
    private AssetManager assetManager;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        setContentView(R.layout.activity_login);

        assetManager = getAssets();
        setupButtons();
    }

    private List<String> readLinesFromAssets(String fileName) {
        List<String> lines = new ArrayList<>();

        try (InputStream is = assetManager.open(fileName);
             BufferedReader reader = new BufferedReader(new InputStreamReader(is))) {

            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);  // Add each line to the list
            }

        }
        catch (IOException e) {
            System.out.println("Error: " + e.getMessage());

            return null;
        }

        return lines;  // Return the list of lines
    }

    private int authenticate( String username, String password ) {

        List<String> readLines; // list of lines read from a file
        String line;
        String[] splitLine;

        int id = -1; // user login id

        // Read Lines from login.txt:
        readLines = readLinesFromAssets("login.txt");
        assert readLines != null;

        // Find Login:
        for(int i=0; i < readLines.size(); i++)
        {
            line = readLines.get(i); // get line from read lines

            splitLine = line.split(","); // split line by comma

            if (username.equalsIgnoreCase(splitLine[1]) && password.equals(splitLine[2])) {
                id = Integer.parseInt(splitLine[0]);
                return id; // return the id of the found login
            }

        }

        return id; // returns -1 if login not found


    }

    private void setupButtons(){
        Button login_button = findViewById(R.id.login_button);
        Button registerButton = findViewById(R.id.registerButton);

        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText uText = findViewById(R.id.login_username_input);
                EditText pText = findViewById(R.id.login_password_input);
                int id = authenticate(uText.getText().toString(), pText.getText().toString());
                if( id > 0 ){
                    checkLogin.setLoggedIn(context, true);
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
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

        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}

