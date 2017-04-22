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
    int retiros,nuevos,estadisticas,consultas,entregas;
    String usuario;

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


        /* ejemplo burdo de como desparecer un icono, odviamente tedremos que consultar a las base
        de datos para crear los menus dependiendo de los servicio contratados por el usuario */
        // recibo el dato del usuario de la session
       usuario =getIntent().getExtras().getString("parametro");

        // obtener los parametros de la base de datos para reemplazar

        retiros = 1;
        nuevos= 0;
        estadisticas=1;
        consultas=1;
        entregas=0;

        if(nuevos==1)
        {
            nuevo.setVisibility(View.VISIBLE);
            textNuevo.setVisibility(View.VISIBLE);
        }
        else if(nuevos ==0)
        {
            nuevo.setVisibility(View.INVISIBLE);
            textNuevo.setVisibility(View.INVISIBLE);
        }
        if(retiros==1)
        {
            retiro.setVisibility(View.VISIBLE);
            textRetiro.setVisibility(View.VISIBLE);
        }
        else if(retiros ==0)
        {
            retiro.setVisibility(View.INVISIBLE);
            textRetiro.setVisibility(View.INVISIBLE);
        }

        if(estadisticas==1)
        {
            estadistica.setVisibility(View.VISIBLE);
            textEstadistica.setVisibility(View.VISIBLE);
        }
        else if(estadisticas ==0)
        {
            estadistica.setVisibility(View.INVISIBLE);
            textEstadistica.setVisibility(View.INVISIBLE);
        }
        if(consultas==1)
        {
            consulta.setVisibility(View.VISIBLE);
            textConsulta.setVisibility(View.VISIBLE);
        }
        else if(consultas ==0)
        {consulta.setVisibility(View.INVISIBLE);
            textConsulta.setVisibility(View.INVISIBLE);

        }
        if(entregas==1)
        {
            entrega.setVisibility(View.VISIBLE);
            textEntrega.setVisibility(View.VISIBLE);
        }
        else if(entregas ==0)
        {
            entrega.setVisibility(View.INVISIBLE);
            textEntrega.setVisibility(View.INVISIBLE);
        }

        // modifica el texView con el usuario de la session

         txtName.setText(usuario);
        txtName.setTextColor(Color.BLACK);



        // Administrador de sesiones
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }

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
    // preferences Borra los datos de usuario de la tabla sqlite users

    private void logoutUser() {
        session.setLogin(false);
        // Lanzamiento de la actividad de inicio de sesión
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        startActivity(intent);
        finish();
    }







}
