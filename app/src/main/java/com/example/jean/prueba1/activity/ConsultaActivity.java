package com.example.jean.prueba1.activity;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ScrollView;
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

    private TextView nombre2;
    private TextView nombre_cliente2;
    private TextView numero2;
    private TextView estado2;
    private TextView observacion2;
    private TextView descripcion2;
    private TextView fecha2;
    private TextView manifiesto2;
    private TextView ciudad2;
    private TextView direccion2;
    private ScrollView scrollviewConsulta;
    private TextView ciudad;
    private TextView direccion;
    String waybill_orden;
    final String TAG = this.getClass().getSimpleName();
    String usuario ,nombre1,nombre_cliente1,numero1,estado1,logistico1,observacion1,descripcion1,fecha1,manifiesto1,ciudad1,direccion1;
   String nombre3,numero_cliente3,numero3,estado3,direccion3,observacion3,descripcion3,fecha3,manifiesto3,ciudad3,nombre_cliente3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        text= (TextView)findViewById(R.id.textView2) ;
        buscar_waybill=(ImageButton) findViewById(R.id.boton_buscar) ;
        waybill= (EditText) findViewById(R.id.numero_orden) ;
        text= (TextView)findViewById(R.id.textView2) ;
        scrollviewConsulta=(ScrollView) findViewById(R.id.scrollviewConsulta);
        // enlazamos nuestro parametros
        nombre= (TextView)findViewById(R.id.nombre) ;
        nombre_cliente= (TextView)findViewById(R.id.nombre_cliente) ;
        numero= (TextView)findViewById(R.id.numero) ;
        estado= (TextView)findViewById(R.id.estado) ;

        observacion= (TextView)findViewById(R.id.observacion) ;
        descripcion= (TextView)findViewById(R.id.descripcion) ;
        direccion= (TextView)findViewById(R.id.direccion) ;
        fecha= (TextView)findViewById(R.id.fecha) ;
        manifiesto= (TextView)findViewById(R.id.manifiesto) ;
        ciudad= (TextView)findViewById(R.id.ciudad) ;

        nombre2= (TextView)findViewById(R.id.nombre2) ;
        nombre_cliente2= (TextView)findViewById(R.id.nombre_cliente2) ;
        numero2= (TextView)findViewById(R.id.numero2) ;
        estado2= (TextView)findViewById(R.id.estado2) ;

        observacion2= (TextView)findViewById(R.id.observacion2);
        descripcion2= (TextView)findViewById(R.id.descripcion2) ;
        direccion2= (TextView)findViewById(R.id.direccion2) ;
        fecha2= (TextView)findViewById(R.id.fecha2) ;
        manifiesto2= (TextView)findViewById(R.id.manifiesto2) ;
        ciudad2= (TextView)findViewById(R.id.ciudad2) ;

        // ocultamos hasta comprobar los datos que puede visualizar el cliente
        nombre.setVisibility(View.INVISIBLE);
        nombre_cliente.setVisibility(View.INVISIBLE);
        numero.setVisibility(View.INVISIBLE);
        estado.setVisibility(View.INVISIBLE);

        observacion.setVisibility(View.INVISIBLE);
        descripcion.setVisibility(View.INVISIBLE);
        fecha.setVisibility(View.INVISIBLE);
        manifiesto.setVisibility(View.INVISIBLE);
        ciudad.setVisibility(View.INVISIBLE);
        direccion.setVisibility(View.INVISIBLE);


        nombre2.setVisibility(View.INVISIBLE);
        nombre_cliente2.setVisibility(View.INVISIBLE);
        numero2.setVisibility(View.INVISIBLE);
        estado2.setVisibility(View.INVISIBLE);
        observacion2.setVisibility(View.INVISIBLE);
        descripcion2.setVisibility(View.INVISIBLE);
        fecha2.setVisibility(View.INVISIBLE);
        manifiesto2.setVisibility(View.INVISIBLE);
        ciudad2.setVisibility(View.INVISIBLE);
        direccion2.setVisibility(View.INVISIBLE);


        usuario =getIntent().getExtras().getString("parametro");
        text.setText(usuario);
        session = new SessionManager(getApplicationContext());


        /* lo elimino por mientras porque echa a perder toda la cuestion
        volver = (ImageButton)findViewById(R.id.volver);
        // volvemos al main
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent1 = new Intent (ConsultaActivity.this, MainActivity.class);
                intent1.putExtra("parametro",usuario);
                startActivity(intent1);

            }
        });*/
        buscar_waybill.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                waybill_orden= waybill.getText().toString().trim();

                if (waybill_orden.isEmpty()) {
                    Toast.makeText(getApplicationContext(), "Debe ingresar el numero de orden!", Toast.LENGTH_SHORT).show();

                }else{
                    checkWaybill(waybill_orden,usuario);
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
                        Log.d("CONEXION", response);
                        try {


                            JSONObject jObj = new JSONObject(response);
                            Log.d("CONEXION", jObj.toString());
                            // extraemos los datos necesarios para armar el menu
                            descripcion1="1";
                            nombre1 =jObj.getString("nombre1");
                            nombre3 =jObj.getString("nombre3");
                            numero1=jObj.getString("numero1");
                           numero3=jObj.getString("numero3");
                         //  direccion1=jObj.getString("direccion1");
                            nombre_cliente1=jObj.getString("nombre_cliente1");
                            nombre_cliente3=jObj.getString("nombre_cliente3");
                            estado1=jObj.getString("estado1");
                            observacion1 =jObj.getString("observacion1");
                            observacion3 =jObj.getString("observacion3");
                           descripcion3=jObj.getString("descripcion3");
                            fecha1=jObj.getString("fecha1");
                            fecha3=jObj.getString("fecha3");
                            manifiesto1=jObj.getString("manifiesto1");
                            manifiesto3=jObj.getString("manifiesto3");
                            ciudad1 =jObj.getString("ciudad1");
                            ciudad3 =jObj.getString("ciudad3");
                            direccion1 =jObj.getString("direccion1");
                            direccion3 =jObj.getString("direccion3");
                            estado1=jObj.getString("estado1");
                            estado3=jObj.getString("estado3");
                          //  direccion1 =jObj.getString("direccion1");
                            // mostramos la cuenta del usuario
                           // txtCuenta.setText(cuenta);
                          //  txtCuenta.setTextColor(Color.BLACK);
                          //  txtCuenta.setVisibility(View.VISIBLE);*/
                            // comprobamos por la visibilidad del icono "nuevo"
                            if(numero3.equals(waybill_orden)) {
                                //se visualiza el view
                                scrollviewConsulta.setVisibility(View.VISIBLE);

                                switch (nombre1) {
                                    case "1":
                                        nombre.setVisibility(View.VISIBLE);
                                      nombre2.setText(nombre3);
                                       nombre2.setVisibility(View.VISIBLE);


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
                                        numero2.setText(numero3);
                                        numero2.setVisibility(View.VISIBLE);

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
                                        estado2.setText(estado3);
                                        estado2.setVisibility(View.VISIBLE);


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
                                        observacion2.setText(observacion3);
                                        observacion2.setVisibility(View.VISIBLE);



                                        break;

                                    case "0":
                                        observacion.setVisibility(View.INVISIBLE);


                                        break;
                                    default:
                                        break;
                                }
                           switch (descripcion1) {

                               case "1":
                                   descripcion.setVisibility(View.VISIBLE);
                                   descripcion2.setText(descripcion3);

                                   descripcion2.setVisibility(View.VISIBLE);


                                   break;

                               case "0":
                                   descripcion.setVisibility(View.INVISIBLE);

                                   break;
                               default:
                                   break;
                           }
                                switch (fecha1) {

                                    case "1":
                                        fecha.setVisibility(View.VISIBLE);
                                        fecha2.setText(fecha3);
                                        fecha2.setVisibility(View.VISIBLE);


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
                                        manifiesto2.setText(manifiesto3);

                                        manifiesto2.setVisibility(View.VISIBLE);


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
                                        ciudad2.setText(ciudad3);

                                        ciudad2.setVisibility(View.VISIBLE);

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
                                        nombre_cliente2.setText(nombre_cliente3);

                                        nombre_cliente2.setVisibility(View.VISIBLE);


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
                                        direccion2.setText(direccion3);
                                        direccion2.setVisibility(View.VISIBLE);


                                        break;

                                    case "0":
                                        direccion.setVisibility(View.INVISIBLE);

                                        break;
                                    default:
                                        break;
                                }
                            }// fin de if
                            else{
                                Toast.makeText(getApplicationContext(), "Debe ingresar un orden de servicio/orden de trabajo, valido!", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            // JSON error
                            Log.d("CONEXION", e.toString());
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
