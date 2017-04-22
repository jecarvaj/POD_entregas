package com.example.jean.prueba1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.prueba1.R;
import com.example.jean.prueba1.helper.SessionManager;

public class NuevoActivity extends AppCompatActivity {

    private SessionManager session;
    private ImageButton volver;
    private Intent intent;
    private TextView textView;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo);
        textView = (TextView)findViewById(R.id.textusuario) ;
        usuario =getIntent().getExtras().getString("parametro");
        textView.setText(usuario);
        session = new SessionManager(getApplicationContext());


        volver = (ImageButton)findViewById(R.id.volver);
        // volvemos al main
        volver.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent2 = new Intent (NuevoActivity.this, MainActivity.class);
                // enviamos el usuario para que la aplicacion no se caiga
                intent2.putExtra("parametro",usuario);
                startActivity(intent2);
            }
        });

    }

}
