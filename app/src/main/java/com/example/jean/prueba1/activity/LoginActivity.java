package com.example.jean.prueba1.activity;
// aweonao 
//WEASDAS

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.location.LocationManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jean.prueba1.R;
import com.example.jean.prueba1.app.AppConfig;
import com.example.jean.prueba1.helper.Helper;
import com.example.jean.prueba1.helper.MySingleton;
import com.example.jean.prueba1.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private Button btnLogin;
    private EditText inputUsuario, inputPassword;
    private SessionManager session;
    final String TAG = this.getClass().getSimpleName(); //para el log
    final Activity activity=this; //Lo ocupo para llamar a getImeiVersion()
        String usuario = null;
    String password = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        inputUsuario=(EditText) findViewById(R.id.usuario);
        inputPassword=(EditText) findViewById(R.id.password);
        btnLogin=(Button) findViewById(R.id.btnLogin);

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
                usuario = inputUsuario.getText().toString().trim();
               password = inputPassword.getText().toString().trim();

                // Si no hay datos vacios
                if (!usuario.isEmpty() && !password.isEmpty()) {
                   //Verifico primero si hay internet
                    if(Helper.isNetDisponible(getApplicationContext())){
                        if(Helper.permisoImei(getApplicationContext(), activity)){
                            checkLogin(usuario, password);
                        }else{
                            Toast.makeText(getApplicationContext(), "Debe otorgar permisos para iniciar sesion!", Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        Toast.makeText(getApplicationContext(), "SIN ACCESO A INTERNET!", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    // Solicitar al usuario que introduzca sus credenciales
                    Toast.makeText(getApplicationContext(), "Ingrese sus datos!", Toast.LENGTH_SHORT).show();
                }
            }

        });

    }

    private void checkLogin(final String usuario, final String password) {
        //creo y envio peticion por POST al server para verificar login
        StringRequest peticion = new StringRequest(Request.Method.POST, AppConfig.URL_LOGIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //la respuesta del servidor
                        Log.d(TAG, response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            String cod = jObj.getString("cod");
                            String msg=jObj.getString("msg");
                            /* los codigos son:
                             *0 => Usuario o contraseña inválida
                             *1 => Datos correctos
                             *2 => Datos correctos pero imei inválida
                            */
                            switch (cod) {
                                case "1":
                                    if (Helper.gpsActivo(getApplicationContext())) { //verifico si el gps está activo
                                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                        session.setLogin(true); //inicio session en celu con sessionmanager
                                        ingresarMenu(); //Cambio al activity del menú
                                    } else { //cuando no está activo del pgs
                                        Toast.makeText(getApplicationContext(), "GPS ESTÁ DESACTIVADO!", Toast.LENGTH_SHORT).show();
                                    }
                                    break;
                                case "2":
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                                    Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                                    break;
                            }
                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                            Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "ERRRRRRRRRRORRRRRRR EN RESPONSE ->"+error);
                        Toast.makeText(getApplicationContext(), error.toString(), Toast.LENGTH_SHORT).show();
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params=new HashMap<>();
                params.put("usuario" ,usuario);
                params.put("password", password);
                params.put("imei", Helper.getImei(getApplicationContext()));
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(peticion);
    }



    private void ingresarMenu() {
        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
        intent.putExtra("parametro",usuario);
        startActivity(intent);
        finish();
    }
}
