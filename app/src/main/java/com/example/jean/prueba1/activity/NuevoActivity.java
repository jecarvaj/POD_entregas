package com.example.jean.prueba1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemSelectedListener;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import android.widget.Spinner;
import android.app.ProgressDialog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/*
calendario
 */
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.widget.DatePicker;
import android.os.Bundle;
import android.app.Activity;
import android.view.View;

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
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
public class NuevoActivity extends AppCompatActivity  {

    private SessionManager session;
    private ImageButton volver;
    private Intent intent;
    private TextView textView;
    String usuario;
    private ImageButton btnAgregar;
    private TextView txtAgregar;
    // array para listar las frutas
    private ArrayList<String> frutasList;
    EditText direccion ;
    EditText descripcion;
    EditText envioCiudad;
    ProgressDialog pDialog;
     Spinner tipoCarga;
     Spinner tipoCliente;
     Spinner tipo;
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
        btnAgregar =(ImageButton) findViewById(R.id.btnCarga);
        tvDateValue = (TextView) findViewById(R.id.dateValue);

        usuario = getIntent().getExtras().getString("parametro");
        checkNuevo(usuario);

        context = this;
        // Definición del botón calendar
        final ImageButton btnOpenPopup = (ImageButton) findViewById(R.id.btnCalendar);
        btnOpenPopup.setOnClickListener(new Button.OnClickListener(){

            @Override
            /**
             * Al pulsar sobre el boton se abre la ventana modal con el componente DatePicker
             */
            public void onClick(View arg0) {
                showDatePickerDialog(arg0);
            }
        });



        textView.setText(recibirNombre);
         session = new SessionManager(getApplicationContext());
        //  volver = (ImageButton)findViewById(R.id.volver);







        // }
    }

    /**
     * Abre la ventana modal
     * @param v
     */
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(context.getFragmentManager(), "datePicker");
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



    public void formulario(final String usuario, final String enviarDireccion, final String enviarDescripcion,final String recibirCarga, final String recibirCliente, final String envioTipo, final String fecha, final String envioCiudad2) {
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
                params.put("recibirCarga" ,recibirCarga);
                params.put("recibirCliente" ,recibirCliente);
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
    Toast.makeText(getApplicationContext(),recibirCliente, Toast.LENGTH_SHORT).show();
    Toast.makeText(getApplicationContext(), recibirCarga, Toast.LENGTH_SHORT).show();

    String[] carga = {recibirCarga,"normal","pesado"};
    tipoCarga.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, carga));
    tipoCarga.setOnItemSelectedListener(new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
        {
           Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            envioCarga=  adapterView.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            // vacio

        }
    });

    String[] valores = {"Express","Normal","Despacho en 24 Horas","Despacho en 48 Horas"};
    tipo.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores));
    tipo.setOnItemSelectedListener(new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
        {
            Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
            envioTipo=  adapterView.getItemAtPosition(position).toString();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent)
        {
            // vacio

        }
    });


    String[] valores2 = {recibirCliente};
    tipoCliente.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, valores2));
    tipoCliente.setOnItemSelectedListener(new OnItemSelectedListener() {

        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id)
        {
            Toast.makeText(adapterView.getContext(), (String) adapterView.getItemAtPosition(position), Toast.LENGTH_SHORT).show();
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
            else if(fecha.isEmpty()){
                Toast.makeText(getApplicationContext(), "Debe ingresar la fecha de envio de la orden!", Toast.LENGTH_SHORT).show();
            }

           else{
                formulario( usuario,  enviarDireccion,  enviarDescripcion, recibirCarga,  recibirCliente, envioTipo, fecha, envioCiudad2);

           }
        }
    });




}
    public class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Usar del defecto la fecha actual
            final Calendar c = Calendar.getInstance();
            try {
                // Si en algun momento se ha informado la fecha se recupera
                String format = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                c.setTime(sdf.parse(String.valueOf(tvDateValue.getText())));
            } catch (ParseException e) {
                // Si falla utilizaremos la fecha actual
            }

            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of TimePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        /**
         * Recupera el valor seleccionado en el componente DatePicker e inserta el valor en el
         * TextView tvDate
         *
         * @param view
         * @param year
         * @param month
         * @param day
         */
        public void onDateSet(DatePicker view, int year, int month, int day) {
            try{
                final Calendar c = Calendar.getInstance();
                c.set(year, month, day);
                String format = "yyyy-MM-dd";
                SimpleDateFormat sdf = new SimpleDateFormat(format, Locale.US);
                tvDateValue.setText(sdf.format(c.getTime()));
                fecha = tvDateValue.getText().toString().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }*/

