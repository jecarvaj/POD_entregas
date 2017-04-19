package com.example.jean.prueba1.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jean.prueba1.R;
import com.example.jean.prueba1.helper.MySingleton;
import com.example.jean.prueba1.helper.SessionManager;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText inputUsuario, inputPassword;
    private ProgressDialog pDIalog;
    private SessionManager session;
    final String TAG = this.getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsuario=(EditText) findViewById(R.id.usuario);
        inputPassword=(EditText) findViewById(R.id.password);
        btnLogin=(Button) findViewById(R.id.btnLogin);

        //Dialogo de procesos
        pDIalog=new ProgressDialog(this);
        pDIalog.setCancelable(false);

        //Administrador de session
        session=new SessionManager(getApplicationContext());

        //Comprobamos usuario registrado
        if(session.isLoggedIn()){
            // El usuario ya está identificado. Tómelo a la actividad principal
            ingresarMenu();
        }

        // Botón de inicio de sesión
        btnLogin.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                String usuario = inputUsuario.getText().toString().trim();
                String password = inputPassword.getText().toString().trim();

                // Buscar datos vacíos en el formulario
                if (!usuario.isEmpty() && !password.isEmpty()) {
                    // usuario de inicio de sesión
                    checkLogin(usuario, password);
                } else {
                    // Solicitar al usuario que introduzca sus credenciales
                    Toast.makeText(getApplicationContext(),
                            "Por favor ingrese sus datos!", Toast.LENGTH_LONG).show();
                }
            }

        });
    }

    private void checkLogin(String usuario, String password) {
       // Toast.makeText(getApplicationContext(), getImei(this), Toast.LENGTH_LONG).show();

        String url="http://192.168.1.109/prueba1";

        StringRequest peticion=new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(TAG, response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getApplicationContext(), "MALO INTERNET", Toast.LENGTH_LONG).show();
                    }
                });
        MySingleton.getInstance(this).addToRequestQueue(peticion);
    }

    private void ingresarMenu() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        startActivity(intent);
        finish();
    }

    private static String getImei(Context c) {
        TelephonyManager telephonyManager = (TelephonyManager) c
                .getSystemService(Context.TELEPHONY_SERVICE);
        return telephonyManager.getDeviceId();
    }
}
