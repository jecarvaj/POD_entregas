package com.example.jean.prueba1.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
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

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/*
calendario
 */
public class NuevoActivity extends AppCompatActivity  {

    private SessionManager session;
    private ImageButton volver;
    private Intent intent;
    private TextView textView;
    String usuario;
    private Button btnAgregar;
    private TextView txtAgregar;
    // array para listar las frutas
    private ArrayList<String> frutasList;
    EditText direccion ;
    EditText descripcion;
    EditText envioCiudad;
    ProgressDialog pDialog;
     Spinner tipoCarga;
     Spinner tipoCliente;
    Calendar date;
     Spinner tipo;
     Button fecha2;
   String envioTipo, envioCarga, envioCliente;
     String recibirNombre, recibirCliente, recibirCarga;
    String enviarDireccion, enviarDescripcion, enviarFecha, enviarWaybill, fecha,envioCiudad2;
    TextView tvDateValue;

    private int year;
    private int month;
    private int day;

    private Activity context;

    final String TAG = this.getClass().getSimpleName();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        textView = (TextView) findViewById(R.id.textusuario);

        tipoCarga = (Spinner) findViewById(R.id.spinnerCarga);
        tipo = (Spinner) findViewById(R.id.spinnerTipo);
        tipoCliente = (Spinner) findViewById(R.id.spinnerCliente);
        frutasList = new ArrayList<String>();
        direccion =(EditText) findViewById(R.id.direccion3);
        descripcion =(EditText) findViewById(R.id.descripcionNuevo);
        envioCiudad =(EditText) findViewById(R.id.ciudad3);
        btnAgregar =(Button) findViewById(R.id.btnCarga);
        tvDateValue = (TextView) findViewById(R.id.dateValue);

        usuario = getIntent().getExtras().getString("parametro");
        checkNuevo(usuario);

        context = this;
        // Definición del botón calendar
        fecha2 = (Button) findViewById(R.id.btnCalendar);
        //btnOpenPopup.setOnClickListener(new Button.OnClickListener(){


        fecha2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                final Calendar currentDate = Calendar.getInstance();
                date = Calendar.getInstance();
                new DatePickerDialog(NuevoActivity.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        date.set(year, monthOfYear, dayOfMonth);
                        new TimePickerDialog(NuevoActivity.this, new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                                date.set(Calendar.HOUR_OF_DAY, hourOfDay);
                                date.set(Calendar.MINUTE, minute);
                                // inicial=date.getTime().toString();
                                DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(getApplicationContext());

                                SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//dd/MM/yyyy
                                fecha = sdfDate.format(date.getTime());
                                // inicial = dateFormat.format(date.getTime());
                                Log.v("The choosen one ", "The choosen one " + date.getTime());
                            }
                        }, currentDate.get(Calendar.HOUR_OF_DAY), currentDate.get(Calendar.MINUTE), false).show();
                    }
                }, currentDate.get(Calendar.YEAR), currentDate.get(Calendar.MONTH), currentDate.get(Calendar.DATE)).show();

            }
        });



        textView.setText(recibirNombre);
         session = new SessionManager(getApplicationContext());
        //  volver = (ImageButton)findViewById(R.id.volver);







        // }
    }



    public void checkNuevo(final String usuario) {
        //creo y envio peticion por POST al server para verificar login
        StringRequest peticion = new StringRequest(Request.Method.POST, AppConfig.URL_NUEVO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //la respuesta del servidor
                        Log.d(TAG, response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("CONEXION", jObj.toString());

                            recibirNombre =jObj.getString("nombre1");
                            recibirCliente =jObj.getString("cliente");
                            recibirCarga=jObj.getString("carga");
                            textView.setText(recibirNombre);

                               spinner(recibirCarga,recibirCliente);
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

                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(peticion);
    }



    public void formulario(final String usuario, final String enviarDireccion, final String enviarDescripcion,final String envioCarga, final String envioCliente, final String envioTipo, final String fecha, final String envioCiudad2) {
        //creo y envio peticion por POST al server para verificar login
        StringRequest peticion = new StringRequest(Request.Method.POST, AppConfig.URL_FORMULARIO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { //la respuesta del servidor
                        Log.d(TAG, response);
                        try {
                            JSONObject jObj = new JSONObject(response);
                            Log.d("CONEXION", jObj.toString());

                            Toast.makeText(getApplicationContext(), "Datos ingresados correctamente!", Toast.LENGTH_SHORT).show();
                           // textView.setText(recibirNombre);


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
                params.put("enviarDireccion" ,enviarDireccion);
                params.put("enviarDescripcion" ,enviarDescripcion);
                params.put("recibirCarga" ,envioCarga);
                params.put("recibirCliente" ,envioCliente);
                params.put("envioTipo" ,envioTipo);
                params.put("tvDateValue" ,fecha);
                params.put("envioCiudad2" ,envioCiudad2);

                return params;
            }
        };
        MySingleton.getInstance(this).addToRequestQueue(peticion);
    }
public void spinner(final String recibirCarga, final String recibirCliente)
{
    this.recibirCarga = recibirCarga;
    this.recibirCliente = recibirCliente;


    String[] carga = {"ELIGA EL TIPO DE CARGA:",recibirCarga,"normal","pesado"};
    tipoCarga.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carga));
    tipoCarga.setOnItemSelectedListener(new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
        {
         //  Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            envioCarga=  adapterView.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            // vacio

        }
    });

    String[] valores = {"ELIGA SU DESPACHO:","Express","Normal","Despacho en 24 Horas","Despacho en 48 Horas"};
    tipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
    tipo.setOnItemSelectedListener(new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
        {
           // Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            envioTipo=  adapterView.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            // vacio

        }
    });


    String[] valores2 = {"ELIGA EL CLIENTE:", recibirCliente};
    tipoCliente.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores2));
    tipoCliente.setOnItemSelectedListener(new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
        {
           // Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            envioCliente=  adapterView.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            // vacio

        }
    });
    btnAgregar.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            enviarDireccion= direccion.getText().toString().trim();
            enviarDescripcion= descripcion.getText().toString().trim();
            envioCiudad2= envioCiudad.getText().toString().trim();
            if (enviarDireccion.isEmpty()) {
                Toast.makeText(getApplicationContext(), "Debe ingresar la direccion de la orden!", Toast.LENGTH_SHORT).show();

            }else if(enviarDescripcion.isEmpty()){
                Toast.makeText(getApplicationContext(), "Debe ingresar la descripcion de la orden!", Toast.LENGTH_SHORT).show();
            }
            else if(envioCiudad2.isEmpty()){
                Toast.makeText(getApplicationContext(), "Debe ingresar la ciudad de la orden!", Toast.LENGTH_SHORT).show();
            }
            else if(fecha == null){
                Toast.makeText(getApplicationContext(), "Debe ingresar la fecha de envio de la orden!", Toast.LENGTH_SHORT).show();
            }
            else if(envioCarga == null || envioCarga.equals("ELIGA EL TIPO DE CARGA:")){
                Toast.makeText(getApplicationContext(), "Debe elegir el tipo de carga!", Toast.LENGTH_SHORT).show();
            }
            else if(envioCliente == null || envioCliente.equals("ELIGA EL CLIENTE:")){
                Toast.makeText(getApplicationContext(), "Debe elegir el cliente!", Toast.LENGTH_SHORT).show();
            }
            else if(envioTipo == null || envioTipo.equals("ELIGA SU DESPACHO:")){
                Toast.makeText(getApplicationContext(), "Debe elegir el tipo de despacho !", Toast.LENGTH_SHORT).show();
            }

           else{





                AlertDialog.Builder builder = new AlertDialog.Builder(NuevoActivity.this);
                builder.setMessage("ESTA SEGURO SE SUBIR ESTA ORDEN?")
                        .setCancelable(false)
                        .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                formulario( usuario,  enviarDireccion,  enviarDescripcion, envioCarga,  envioCliente, envioTipo, fecha, envioCiudad2);
                                // termina la actividad y la recarga
                                finish();
                                startActivity(getIntent());

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
           }
        }
    });




}

}

