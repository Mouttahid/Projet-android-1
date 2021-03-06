package com.example.gestiondestches_cigma;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import shared.ApiLinks;

public class LoginActivity extends AppCompatActivity {
    EditText email;
    EditText password;
    Button loginBtn;
    Intent mainIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        loginBtn = (Button) findViewById(R.id.login_button);
        mainIntent = new Intent(this,MainActivity.class);
        loginBtn.setOnClickListener(
                new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                login(view);
                }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();

    }
    public void login(View view){
        final String emailValue = this.email.getText().toString().trim();
        final String passwordValue = this.password.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,ApiLinks.loginLink, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jso = new JSONObject(response);
                    JSONObject user = jso.getJSONObject("user");
                    if("true".equalsIgnoreCase(jso.getString("success"))){
                        SharedPreferences preferences = getSharedPreferences("authPrefs",MODE_PRIVATE);
                        preferences.edit().putString("name",user.getString("name"));
                        preferences.edit().putString("email",user.getString("email"));
                        preferences.edit().putString("role",jso.getString("role"));
                        preferences.edit().putString("auth_token",jso.getString("token_type")+" "+jso.getString("access_token"));
                        startActivity(mainIntent);
                    }
                    Log.d("Success",jso.getString("success"));
                    Log.d(MainActivity.class.getSimpleName(),"response"+response);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d(MainActivity.class.getSimpleName(),"error"+error);

            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String , String > params = new HashMap<>();
                params.put("email",emailValue);
                params.put("password",passwordValue);
                return params;
            }
        };
        RequestQueue queue = Volley.newRequestQueue(this);
        queue.add(stringRequest);
    }
}
