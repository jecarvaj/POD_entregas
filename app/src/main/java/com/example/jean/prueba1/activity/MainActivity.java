package com.example.jean.prueba1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jean.prueba1.R;
import com.example.jean.prueba1.helper.SessionManager;

import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    private TextView txtName;
    private TextView txtUsuario;
    private Button btnLogout;
    private ImageButton entrega;
    private ImageButton nuevo;
    private ImageButton consulta;
    private ImageButton estadisticas;
    private ImageButton retiro;

    private SessionManager session;

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
        estadisticas =(ImageButton) findViewById(R.id.estadisticas);
        retiro =(ImageButton) findViewById(R.id.retiro);


        consulta.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v) {
            Intent intent = new Intent (MainActivity.this, ConsultaActivity.class);
            startActivity(intent); } });

        nuevo.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v) {
            Intent intent = new Intent (MainActivity.this, NuevoActivity.class);
            startActivity(intent); } });


        // Administrador de sesiones
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }


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
