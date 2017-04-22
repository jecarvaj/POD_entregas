package com.example.jean.prueba1.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.jean.prueba1.R;
import com.example.jean.prueba1.helper.SessionManager;

public class ConsultaActivity extends AppCompatActivity {

    private SessionManager session;
    private ImageButton volver;
    private Intent intent;
    private TextView text;
    String usuario;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_consulta);
        text= (TextView)findViewById(R.id.textView2) ;


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

    }
}
