package com.example.gestiondestches_cigma;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    EditText username,password;
    Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        username = this.findViewById(R.id.login_username);
        password = this.findViewById(R.id.login_password);
        loginButton = this.findViewById(R.id.login_button);

        loginButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(!username.getText().toString().isEmpty() && !password.getText().toString().isEmpty())
        {
            Toast.makeText(this,username.getText().toString()+" - "+password.getText().toString(),Toast.LENGTH_LONG).show();
            return;
        }
        Toast.makeText(this,"Username & password required",Toast.LENGTH_LONG).show();

    }
}
