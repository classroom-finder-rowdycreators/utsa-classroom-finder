package com.example.utsa_classroom_finder;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.ComponentActivity;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class RegisterActivity extends ComponentActivity {
    @Override
    public void onCreate( Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        setUpButtons();
    }


    private void setUpButtons(){
        Button register = (Button) findViewById(R.id.createAccountButton);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int id = -1;
                if(validation()){
                    id = createLogin();
                    if(id>0){
                        createAccount(id);
                    }
                    finish();
                }else{
                    Toast.makeText(getBaseContext(),"One or more values is missing",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validation(){
        EditText nameInput = (EditText) findViewById(R.id.rNameField);
        EditText ageInput = (EditText) findViewById(R.id.rAgefield);
        EditText heightInput = (EditText) findViewById(R.id.rHeightField);
        EditText weightInput = (EditText) findViewById(R.id.rWeightField);
        EditText emailInput = (EditText) findViewById(R.id.rEmailField);
        EditText passwordInput = (EditText) findViewById(R.id.rPasswordField);

        return !nameInput.getText().toString().isEmpty() &&
                !ageInput.getText().toString().isEmpty() &&
                !heightInput.getText().toString().isEmpty() &&
                !weightInput.getText().toString().isEmpty() &&
                !emailInput.getText().toString().isEmpty() &&
                !passwordInput.getText().toString().isEmpty();

    }

    private void createAccount(int id){
        EditText nameInput = (EditText) findViewById(R.id.rNameField);
        EditText ageInput = (EditText) findViewById(R.id.rAgefield);
        EditText heightInput = (EditText) findViewById(R.id.rHeightField);
        EditText weightInput = (EditText) findViewById(R.id.rWeightField);
        EditText emailInput = (EditText) findViewById(R.id.rEmailField);

        String name = nameInput.getText().toString();
        int age = Integer.parseInt(ageInput.getText().toString());
        String height = heightInput.getText().toString();
        int weight = Integer.parseInt(weightInput.getText().toString());
        String emails = emailInput.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath()+"/accounts.txt");
        if(!f.exists()){
            try {
                OutputStreamWriter w = new OutputStreamWriter(openFileOutput("accounts.txt",MODE_PRIVATE));
                w.write(id +"," + name+"," + age + ","+ height + ","+ weight +","+ emails);
                w.close();
            }catch(IOException e){
                Toast.makeText(getBaseContext(),"IOException" + e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }else{
            try{
                OutputStreamWriter w = new OutputStreamWriter(openFileOutput("accounts.txt",MODE_APPEND));
                w.append("\n" + id +"," + name+"," + age + ","+ height + ","+ weight +","+ emails);
                w.close();
            }catch (IOException e){
                Toast.makeText(getBaseContext(),"IOException" + e.getMessage(),Toast.LENGTH_SHORT).show();

            }
        }


        ;
    }

    private int createLogin(){
        EditText nameInput = (EditText) findViewById(R.id.rNameField);
        EditText passwordInput = (EditText) findViewById(R.id.rPasswordField);
        String uName = nameInput.getText().toString();
        String pWord = passwordInput.getText().toString();

        File f = new File(getFilesDir().getAbsolutePath()+"/login.txt");
        Scanner scan;
        int id =-1;
        String str = null;
        String [] arr;
        if(!f.exists()){
            id = 1;
            try {
                OutputStreamWriter w = new OutputStreamWriter(openFileOutput("login.txt",MODE_PRIVATE));
                w.write(id +"," + uName+"," + pWord);
                w.close();
            }catch(IOException e){
                Toast.makeText(getBaseContext(),"IOException" + e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }else{
            try {
                scan = new Scanner(openFileInput("login.txt"));
                while (scan.hasNextLine()){
                    str = scan.nextLine();
                }
                if(str!= null){
                    arr = str.split(",");
                    if(arr.length == 3){
                        id = Integer.parseInt(arr[0])+1;
                    }
                }
                scan.close();
                OutputStreamWriter w = new OutputStreamWriter(openFileOutput("login.txt",MODE_APPEND));
                w.append("\n"+id +"," + uName+"," + pWord);
                w.close();


            }catch (IOException e){
                Toast.makeText(getBaseContext(),"IOException" + e.getMessage(),Toast.LENGTH_SHORT).show();
            }

        }
        return id;
    }
}
