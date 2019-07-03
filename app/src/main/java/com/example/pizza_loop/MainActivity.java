package com.example.pizza_loop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText username;
    EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = findViewById(R.id.username);
        password = findViewById(R.id.password);
    }

    public void login(View view) {
        Intent intent = new Intent(MainActivity.this, PizzaListActivity.class);
        startActivity(intent);
       /* if (username.getText().toString()=="a" && password.getText().toString()=="1"){

        }
        else{
            Toast toast = (Toast) Toast.makeText(this,"Please enter User name-admin password-1234",Toast.LENGTH_LONG);
            toast.show();
        }*/

    }
}
