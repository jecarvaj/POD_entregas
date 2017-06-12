package com.example.jean.prueba1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jean.prueba1.R;
import com.example.jean.prueba1.app.AppConfig;
import com.example.jean.prueba1.helper.Helper;
import com.example.jean.prueba1.helper.SessionManager;
import com.example.jean.prueba1.helper.MySingleton;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtUsuario;
    private TextView txtCuenta;
    private Button btnLogout;
    private ImageButton entrega;
    private ImageButton nuevo;
    private ImageButton consulta;
    private ImageButton estadistica;
    private ImageButton retiro;
    private TextView textNuevo;
    private TextView textConsulta;
    private TextView textRetiro;
    private TextView textEntrega;
    private TextView textEstadistica;

    final String TAG = this.getClass().getSimpleName();
    private SessionManager session;
    public boolean permiso = false;
    String retiros,nuevos,estadisticas,consultas,entregas;
    String usuario;
    String create;
    String cuenta;
    String deli;
    String consu;
    String esta;
    String reti;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtName = (TextView) findViewById(R.id.name);
        txtUsuario = (TextView) findViewById(R.id.usuario);
        btnLogout = (Button) findViewById(R.id.btnLogout);
        entrega =(ImageButton) findViewById(R.id.entrega);
        nuevo =(ImageButton) findViewById(R.id.nuevo);
        consulta =(ImageButton) findViewById(R.id.consulta);
        estadistica =(ImageButton) findViewById(R.id.estadisticas);
        retiro =(ImageButton) findViewById(R.id.retiro);
        textNuevo= (TextView) findViewById(R.id.textnuevo);
        textConsulta= (TextView) findViewById(R.id.textconsulta);
        textEntrega= (TextView) findViewById(R.id.textentrega);
        textEstadistica= (TextView) findViewById(R.id.textestadistica);
        textRetiro= (TextView) findViewById(R.id.textretiro);
        txtCuenta = (TextView)findViewById(R.id.cuenta);



        // recibo el dato del usuario de la session
        usuario =getIntent().getExtras().getString("parametro");
        // llamada el metodo para chequear el tipo de menu del usuario logeado
        checkMenu(usuario);
        // mostramos el nombre de usuario
        txtName.setText(usuario);
        txtName.setTextColor(Color.BLACK);


        // el icono consulta lo dejo siempre visible
            consulta.setVisibility(View.VISIBLE);
            textConsulta.setVisibility(View.VISIBLE);

        // Administrador de sesiones
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        //  llamadas a las activitys consulta y nuevo
        consulta.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v) {
            Intent intent1 = new Intent (MainActivity.this, ConsultaActivity.class);
            intent1.putExtra("parametro",usuario);
            startActivity(intent1);
            } });

        nuevo.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v) {
            Intent intent2 = new Intent (MainActivity.this, NuevoActivity.class);
            intent2.putExtra("parametro",usuario);
            startActivity(intent2);
            } });

        // Botón de cierre de sesión haga clic en evento
        btnLogout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                logoutUser();
            }
        });
    }


    //Cerrar la sesión del usuario. Se establecerá el indicador isLoggedIn en false en shared

    private void logoutUser() {
        session.setLogin(false);
        // Lanzamiento de la actividad de inicio de sesión
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        startActivity(intent);
        finish();
    }

    private void checkMenu(final String usuario) {
        //creo y envio peticion por POST al server para verificar login
        StringRequest peticion1 = new StringRequest(Request.Method.POST, AppConfig.URL_MENU,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //la respuesta del servidor
                        Log.d(TAG, response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            // extraemos los datos necesarios para armar el menu
                            cuenta =jObj.getString("cuenta");
                            create=jObj.getString("create");
                            deli=jObj.getString("deli");
                            reti=jObj.getString("reti");
                            esta =jObj.getString("esta");
                            // mostramos la cuenta del usuario
                            txtCuenta.setText(cuenta);
                            txtCuenta.setTextColor(Color.BLACK);
                            txtCuenta.setVisibility(View.VISIBLE);
                         // comprobamos por la visibilidad del icono "nuevo"
                            switch (create) {
                              /*  case "1":
                                    nuevo.setVisibility(View.VISIBLE);
                                    textNuevo.setVisibility(View.VISIBLE);

                                    break;
                                    */
                                case "0":
                                    nuevo.setVisibility(View.INVISIBLE);
                                    textNuevo.setVisibility(View.INVISIBLE);
                                    break;
                                default:
                                    break;
                            }
                            // comprobamos por la visibilidad del icono "entregas"
                            switch (deli) {
                                /*
                                case "1":
                                    entrega.setVisibility(View.VISIBLE);
                                    textEntrega.setVisibility(View.VISIBLE);

                                    break;
                                    */
                                case "0":
                                    entrega.setVisibility(View.INVISIBLE);
                                    textEntrega.setVisibility(View.INVISIBLE);
                                    break;
                                default:
                                    break;
                            }
                            // comprobamos por la visibilidad del icono "retiros"
                            switch (reti) {
                                /*
                                case "1":
                                    retiro.setVisibility(View.VISIBLE);
                                    textRetiro.setVisibility(View.VISIBLE);

                                    break;
                                    */
                                case "0":
                                    retiro.setVisibility(View.INVISIBLE);
                                    textRetiro.setVisibility(View.INVISIBLE);
                                    break;
                                default:
                                    break;
                            }
                            // comprobamos por la visibilidad del icono "estadisticas"
                            switch (esta) {
                                /*
                                case "1":
                                    estadistica.setVisibility(View.VISIBLE);
                                    textEstadistica.setVisibility(View.VISIBLE);

                                    break;
                                    */
                                case "0":
                                    estadistica.setVisibility(View.INVISIBLE);
                                    textEstadistica.setVisibility(View.INVISIBLE);
                                    break;
                                default:
                                    break;
                            }


                        } catch (JSONException e) {
                            // JSON error
                            e.printStackTrace();
                          // Toast.makeText(getApplicationContext(), "Json error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
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

                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(peticion1);
    }





}
