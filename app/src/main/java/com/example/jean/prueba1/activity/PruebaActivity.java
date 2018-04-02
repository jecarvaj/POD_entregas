package com.example.jean.prueba1.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jean.prueba1.R;
import com.example.jean.prueba1.helper.Helper;
import com.example.jean.prueba1.helper.SessionManager;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PruebaActivity extends AppCompatActivity {
    public  String usuario;
    private SessionManager session;
    ArrayList<NameValuePair> Pairs;
    String parametro;
    HttpPost httppost;
    HttpClient httpclient;
    private TextView cuenta;
    ListView listView;
    final String TAG = this.getClass().getSimpleName();
    EditText editText;
    String imei2;
    ArrayList<Subject> SubjectList = new ArrayList<Subject>();
    String HttpURL = "http://192.168.100.18/api/test/Subjects.php";
    ListAdapter listAdapter;
    ProgressBar progressBar ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prueba);
        listView = (ListView) findViewById(R.id.listView1);

        editText = (EditText) findViewById(R.id.edittext1);
        progressBar = (ProgressBar)findViewById(R.id.progressbar);

        listView.setTextFilterEnabled(true);
        cuenta=(TextView) findViewById(R.id.textCuenta3);
        usuario =getIntent().getExtras().getString("parametro");
        cuenta.setText(usuario);
        session = new SessionManager(getApplicationContext());

        listView.setOnItemClickListener(new OnItemClickListener() {

            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Subject ListViewClickData = (Subject)parent.getItemAtPosition(position);

                //Toast.makeText(PruebaActivity.this, ListViewClickData.getSubName(), Toast.LENGTH_SHORT).show();
                parametro = ListViewClickData.getSubName();
                Intent intent2 = new Intent (PruebaActivity.this, Entrega2Activity.class);
                intent2.putExtra("parametro",parametro);
                intent2.putExtra("usuario",usuario);
                startActivity(intent2);




    }
        });
        editText.addTextChangedListener(new TextWatcher() {

            public void afterTextChanged(Editable s) {
            }

            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            public void onTextChanged(CharSequence stringVar, int start, int before, int count) {

                listAdapter.getFilter().filter(stringVar.toString());
            }
        });

        new ParseJSonDataClass(this).execute();

    }
    private class ParseJSonDataClass extends AsyncTask<Void, Void, Void> {
        public Context context;
        String FinalJSonResult;

        public ParseJSonDataClass(Context context) {

            this.context = context;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
        }

        @Override
        protected Void doInBackground(Void... arg0) {


            try{








                // httppost= new HttpPost("http://192.168.100.18/api/getEntregas.php");
                imei2 = Helper.getImei(getApplicationContext()).toString();

                Log.d(TAG, "este es el imei "+ imei2);





                httppost.setEntity(new UrlEncodedFormEntity(Pairs));
                //response=httpclient.execute(httppost);
                Log.d(TAG, "mensaje "+Pairs.toString());
                HttpResponse response = httpclient.execute(httppost);


                HttpEntity entity = response.getEntity();
                // String json = EntityUtils.toString(entity);
                // Get our response as a String.

            }catch(Exception e){

                Log.e("ERROR", e.getMessage());
            }



            HttpServicesClass httpServiceClass = new HttpServicesClass(HttpURL);

            try {
                imei2 = Helper.getImei(getApplicationContext()).toString();

                Log.d(TAG, "este es el imei "+ imei2);

                httpServiceClass.AddParam("imei",imei2);
                httpServiceClass.AddParam("usuario",usuario);
                httpServiceClass.ExecutePostRequest();




                if (httpServiceClass.getResponseCode() == 200) {

                    FinalJSonResult = httpServiceClass.getResponse();


                    if (FinalJSonResult != null) {

                        JSONArray jsonArray = null;
                        try {

                            jsonArray = new JSONArray(FinalJSonResult);

                            JSONObject jsonObject;

                            Subject subject;

                            SubjectList = new ArrayList<Subject>();

                            for (int i = 0; i < jsonArray.length(); i++) {

                                jsonObject = jsonArray.getJSONObject(i);

                                String tempName = jsonObject.getString("waybill").toString();

                                String tempFullForm = jsonObject.getString("address").toString();


                                String tempFullPrioridad = jsonObject.getString("priority").toString();

                                String tempFullEstado = jsonObject.getString("status").toString();
                                subject = new Subject(tempName, tempFullForm, tempFullPrioridad, tempFullEstado);



                                SubjectList.add(subject);
                            }
                        } catch (JSONException e) {
                            // TODO Auto-generated catch block
                            e.printStackTrace();
                        }
                    }
                } else {

                    Toast.makeText(context, httpServiceClass.getErrorMessage(), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result)

        {
            progressBar.setVisibility(View.INVISIBLE);
            listAdapter = new ListAdapter(PruebaActivity.this, R.layout.custom_layout, SubjectList);
            listView.setAdapter(listAdapter);
        }
    }

}

