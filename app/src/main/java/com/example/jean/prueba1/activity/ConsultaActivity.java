package com.example.jean.prueba1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.jean.prueba1.R;
import com.example.jean.prueba1.app.AppConfig;
import com.example.jean.prueba1.helper.MySingleton;
import com.example.jean.prueba1.helper.SessionManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ConsultaActivity extends AppCompatActivity {

    private SessionManager session;
    private ImageButton volver;
    private ImageButton buscar_waybill;

    private Intent intent;
    private TextView text;
    private EditText waybill;
    private TextView nombre;
    private TextView nombre_cliente;
    private TextView numero;
    private TextView estado;
    private TextView logistico;
    private TextView observacion;
    private TextView descripcion;
    private TextView fecha;
    private TextView manifiesto;


    private TextView ciudad;
    private TextView direccion;
    String waybill_orden;
    final String TAG = this.getClass().getSimpleName();
    String usuario ,nombre1,nombre_cliente1,numero1,estado1,logistico1,observacion1,descripcion1,fecha1,manifiesto1,ciudad1,direccion1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        text= (TextView)findViewById(R.id.textView2) ;
        buscar_waybill=(ImageButton) findViewById(R.id.boton_buscar) ;
        waybill= (EditText) findViewById(R.id.numero_orden) ;
        text= (TextView)findViewById(R.id.textView2) ;
        // enlazamos nuestro parametros
        nombre= (TextView)findViewById(R.id.nombre) ;
        nombre_cliente= (TextView)findViewById(R.id.nombre_cliente) ;
        numero= (TextView)findViewById(R.id.numero) ;
        estado= (TextView)findViewById(R.id.estado) ;
        logistico= (TextView)findViewById(R.id.logistico) ;
        observacion= (TextView)findViewById(R.id.observacion) ;
        direccion= (TextView)findViewById(R.id.direccion) ;
        fecha= (TextView)findViewById(R.id.fecha) ;
        manifiesto= (TextView)findViewById(R.id.manifiesto) ;
        ciudad= (TextView)findViewById(R.id.ciudad) ;


        // ocultamos hasta comprobar los datos que puede visualizar el cliente
        nombre.setVisibility(View.INVISIBLE);
        nombre_cliente.setVisibility(View.INVISIBLE);
        numero.setVisibility(View.INVISIBLE);
        estado.setVisibility(View.INVISIBLE);
        logistico.setVisibility(View.INVISIBLE);
        observacion.setVisibility(View.INVISIBLE);
        fecha.setVisibility(View.INVISIBLE);
        manifiesto.setVisibility(View.INVISIBLE);
        ciudad.setVisibility(View.INVISIBLE);
        direccion.setVisibility(View.INVISIBLE);



        usuario =getIntent().getExtras().getString("parametro");
        text.setText(usuario);
        session = new SessionManager(getApplicationContext());


        volver = (ImageButton)findViewById(R.id.volver);
        // volvemos al main
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (ConsultaActivity.this, MainActivity.class);
                intent1.putExtra("parametro",usuario);
                startActivity(intent1);

            }
        });
        buscar_waybill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waybill_orden= waybill.getText().toString().trim();
                checkWaybill(waybill_orden,usuario);
                if (waybill_orden.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar el numero de orden!", Toast.LENGTH_SHORT).show();

                }


            }
        });

        session = new SessionManager(getApplicationContext());


    }
    private void checkWaybill(final String waybill_orden,final String usuario) {
        //creo y envio peticion por POST al server para verificar login
        StringRequest peticion2 = new StringRequest(Request.Method.POST, AppConfig.URL_WAYBILL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //la respuesta del servidor
                        Log.d(TAG, response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            // extraemos los datos necesarios para armar el menu
                            nombre1 =jObj.getString("nombre1");
                            numero1=jObj.getString("numero1");
                         //  direccion1=jObj.getString("direccion1");
                            nombre_cliente1=jObj.getString("nombre_cliente1");
                            estado1=jObj.getString("estado1");
                            observacion1 =jObj.getString("observacion1");
                           // descripcion1=jObj.getString("descripcion1");
                            fecha1=jObj.getString("fecha1");
                            manifiesto1=jObj.getString("manifiesto1");
                            ciudad1 =jObj.getString("ciudad1");
                            direccion1 =jObj.getString("direccion1");
                          //  direccion1 =jObj.getString("direccion1");
                            // mostramos la cuenta del usuario
                           // txtCuenta.setText(cuenta);
                          //  txtCuenta.setTextColor(Color.BLACK);
                          //  txtCuenta.setVisibility(View.VISIBLE);*/
                            // comprobamos por la visibilidad del icono "nuevo"
                            switch (nombre1) {
                               case "1":
                                    nombre.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    nombre.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }
                            // comprobamos por la visibilidad del icono "entregas"

                            // comprobamos por la visibilidad del icono "retiros"
                            switch (numero1) {

                                case "1":
                                    numero.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    numero.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }
                            // comprobamos por la visibilidad del icono "estadisticas"
                            switch (estado1) {

                                case "1":
                                    estado.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    estado.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }
                         /*   switch (logistico1) {

                                case "1":
                                   logistico.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    logistico.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }*/
                            switch (observacion1) {

                                case "1":
                                    observacion.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    observacion.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }
                          /*  switch (descripcion1) {

                                case "1":
                                    descripcion.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    descripcion.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }*/
                            switch (fecha1) {

                                case "1":
                                    fecha.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    fecha.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }
                            switch (manifiesto1) {

                                case "1":
                                    manifiesto.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    manifiesto.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }
                            switch (ciudad1) {

                                case "1":
                                    ciudad.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    ciudad.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }
                            switch (nombre_cliente1) {

                                case "1":
                                    nombre_cliente.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    nombre_cliente.setVisibility(View.INVISIBLE);

                                    break;
                                default:
                                    break;
                            }
                          switch (direccion1) {

                                case "1":
                                    direccion.setVisibility(View.VISIBLE);


                                    break;

                                case "0":
                                    direccion.setVisibility(View.INVISIBLE);

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
                params.put("waybill_orden", waybill_orden);
                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(peticion2);
    }


}
