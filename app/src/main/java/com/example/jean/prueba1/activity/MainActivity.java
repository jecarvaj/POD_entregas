package com.example.jean.prueba1.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
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



public class MainActivity extends AppCompatActivity  {

    private TextView txtName;
    private TextView txtUsuario;
    private TextView txtCuenta;
    private ImageButton btnLogout;
    private ImageButton entrega;
    private ImageButton nuevo;
    private ImageButton consulta;
    private ImageButton ubicacion;
    private ImageButton retiro;
    private ImageButton qr;
    private TextView textNuevo;
    private TextView textConsulta;
    private TextView textRetiro;
    private TextView textEntrega;
    private TextView textEstadistica;

    final String TAG = this.getClass().getSimpleName();
    private SessionManager session;
    public boolean permiso = false;
    String usuario;
    String create;
    String cuenta;
    String deli;
    String consu;
    String esta;
    String reti;
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // txtName = (TextView) findViewById(R.id.name);
        txtUsuario = (TextView) findViewById(R.id.usuario);
       // btnLogout = (ImageButton) findViewById(R.id.btnLogout);
        entrega =(ImageButton) findViewById(R.id.entrega);
        nuevo =(ImageButton) findViewById(R.id.nuevo);
        qr =(ImageButton) findViewById(R.id.lector2);
        consulta =(ImageButton) findViewById(R.id.consulta);
        ubicacion =(ImageButton) findViewById(R.id.ubicacion);
        retiro =(ImageButton) findViewById(R.id.retiro);
        textNuevo= (TextView) findViewById(R.id.textnuevo);
        textConsulta= (TextView) findViewById(R.id.textconsulta);
        textEntrega= (TextView) findViewById(R.id.textentrega);
        textRetiro= (TextView) findViewById(R.id.textretiro);
      //  txtCuenta = (TextView)findViewById(R.id.cuenta);



        // recibo el dato del usuario de la session
        usuario =getIntent().getExtras().getString("parametro");
        // llamada el metodo para chequear el tipo de menu del usuario logeado
        checkMenu(usuario);
        // mostramos el nombre de usuario
       // txtName.setText(usuario);
//        txtName.setTextColor(Color.BLACK);
        initToolBar();

        // el icono consulta lo dejo siempre visible
            consulta.setVisibility(View.VISIBLE);
            textConsulta.setVisibility(View.VISIBLE);

        // Administrador de sesiones
        session = new SessionManager(getApplicationContext());

        if (!session.isLoggedIn()) {
            logoutUser();
        }
        //  llamadas a las activitys consulta y nuevo
        qr.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v) {
            try {

                Intent intent = new Intent("com.google.zxing.client.android.SCAN");
                intent.putExtra("SCAN_MODE", "QR_CODE_MODE"); // "PRODUCT_MODE for bar codes

                startActivityForResult(intent, 0);

            } catch (Exception e) {

                Uri marketUri = Uri.parse("market://details?id=com.google.zxing.client.android");
                Intent marketIntent = new Intent(Intent.ACTION_VIEW,marketUri);
                startActivity(marketIntent);

            }
        } });
        consulta.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v) {
            Intent intent1 = new Intent (MainActivity.this, ConsultaActivity.class);
            intent1.putExtra("parametro",usuario);
            startActivity(intent1);
            } });
        entrega.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v) {
            Intent intent3 = new Intent (MainActivity.this, PruebaActivity.class);
            intent3.putExtra("parametro",usuario);
            startActivity(intent3);
        } });

        nuevo.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v) {
            Intent intent2 = new Intent (MainActivity.this, NuevoActivity.class);
            intent2.putExtra("parametro",usuario);
            startActivity(intent2);
            } });


        ubicacion.setOnClickListener(new View.OnClickListener()
        { @Override public void onClick(View v) {
            Intent intent4 = new Intent (MainActivity.this, MyLocationUsingLocationAPI.class);
            intent4.putExtra("parametro",usuario);
            startActivity(intent4);
        } });
    }




    public void initToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle(usuario);
        toolbar.setSubtitle(cuenta);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(MainActivity.this, "clicking the Back!", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
// Handle item selection
        switch (item.getItemId()) {
            case R.id.miCompose:
                onBackPressed();
                return true;
            case R.id.confi:

                return true;
            case R.id.estadisticas:

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage("Realmente Desea salir?")
                .setCancelable(false)
                .setPositiveButton("Si", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        logoutUser();
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
    //Cerrar la sesión del usuario. Se establecerá el indicador isLoggedIn en false en shared

    private void logoutUser() {
        session.setLogin(false);
        // Lanzamiento de la actividad de inicio de sesión
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);

        startActivity(intent);
        finish();
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0) {

            if (resultCode == RESULT_OK) {

                session = new SessionManager(getApplicationContext());
                String parametro = data.getStringExtra("SCAN_RESULT");
                Toast.makeText(MainActivity.this, parametro, Toast.LENGTH_SHORT).show();

               Intent intent2 = new Intent (MainActivity.this, Entrega2Activity.class);
                intent2.putExtra("parametro",parametro);
                intent2.putExtra("usuario",usuario);
                startActivity(intent2);
            }
            if(resultCode == RESULT_CANCELED){
                Toast.makeText(MainActivity.this, "Orden No válida", Toast.LENGTH_SHORT).show();
            }
        }
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
                         //   txtCuenta.setText(cuenta);
                         //   txtCuenta.setTextColor(Color.BLACK);
                          //  txtCuenta.setVisibility(View.VISIBLE);
                         // comprobamos por la visibilidad del icono "nuevo"
                            switch (create) {
                                case "1":

                                    nuevo.setEnabled(true);
                                   // nuevo.setVisibility(View.VISIBLE);
                                   // textNuevo.setVisibility(View.VISIBLE);

                                    break;

                                case "0":
                                    nuevo.setEnabled(false);
                                   // nuevo.setVisibility(View.INVISIBLE);
                                    //textNuevo.setVisibility(View.INVISIBLE);
                                    break;
                                default:
                                    break;
                            }
                            // comprobamos por la visibilidad del icono "entregas"
                            switch (deli) {

                                case "1":
                                    entrega.setEnabled(true);
                                   // entrega.setVisibility(View.VISIBLE);
                                    //textEntrega.setVisibility(View.VISIBLE);

                                    break;

                                case "0":
                                    entrega.setEnabled(false);
                                   // entrega.setVisibility(View.INVISIBLE);
                                   // textEntrega.setVisibility(View.INVISIBLE);
                                    break;
                                default:
                                    break;
                            }
                            // comprobamos por la visibilidad del icono "retiros"
                            switch (reti) {

                                case "1":
                                    retiro.setEnabled(true);
                                   // retiro.setVisibility(View.VISIBLE);
                                   // textRetiro.setVisibility(View.VISIBLE);

                                    break;

                                case "0":
                                    retiro.setEnabled(false);
                                   // retiro.setVisibility(View.INVISIBLE);
                                    //textRetiro.setVisibility(View.INVISIBLE);
                                    break;
                                default:
                                    break;
                            }
                            // comprobamos por la visibilidad del icono "estadisticas"


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
                        Log.d(TAG, "No hay conexión con el servidor"+error);
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
