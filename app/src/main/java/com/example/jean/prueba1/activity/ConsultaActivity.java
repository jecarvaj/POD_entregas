package com.example.jean.prueba1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.example.jean.prueba1.R;
import com.example.jean.prueba1.helper.SessionManager;

public class ConsultaActivity extends AppCompatActivity {

    private SessionManager session;
    private ImageButton volver;
    private Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);


        session = new SessionManager(getApplicationContext());


        volver = (ImageButton)findViewById(R.id.volver);
        // volvemos al main
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (ConsultaActivity.this, MainActivity.class);
                startActivity(intent);

            }
        });

    }
}
