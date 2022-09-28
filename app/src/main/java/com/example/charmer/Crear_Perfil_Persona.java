package com.example.charmer;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import javax.net.ssl.HttpsURLConnection;

public class Crear_Perfil_Persona extends AppCompatActivity {

    ImageView btn_atras;
    Button btn_guardar;
    EditText nombre, telefono, ubicacion;
    RadioButton RB_Ho, RB_Mu, RB_O;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.crearperfilpersona);

        btn_atras = (ImageView) findViewById(R.id.btn_flechaatras_perfil);
        btn_guardar = (Button) findViewById(R.id.btn_guardar_perfil);
        nombre = (EditText) findViewById(R.id.ET_Fullname);
        telefono = (EditText) findViewById(R.id.ET_Fono);
        ubicacion = (EditText) findViewById(R.id.ET_Direccion);

        RB_Ho = (RadioButton) findViewById(R.id.RB_man);
        RB_Mu = (RadioButton) findViewById(R.id.RB_Women);
        RB_O = (RadioButton) findViewById(R.id.RB_Other);

        DBHelper DB = new DBHelper(this);


        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Atras = new Intent(getApplicationContext(), TipoUsuario.class);
                startActivity(Atras);
            }
        });

        btn_guardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = nombre.getText().toString();
                String fono = telefono.getText().toString();
                String lugar = ubicacion.getText().toString();
                Integer sexo = 2;
                Integer tipo = getIntent().getExtras().getInt("tipo");
                SharedPreferences preferences = getSharedPreferences("enter", Context.MODE_PRIVATE);
                String user = preferences.getString("user","0");

                if (RB_Ho.isChecked() == true)
                    sexo = 0;
                else if (RB_Mu.isChecked() == true)
                    sexo = 1;
                else if (RB_O.isChecked() == true)
                    sexo = 2;

                if(TextUtils.isEmpty(name))
                    nombre.setError(getString(R.string.errorData));
                if(TextUtils.isEmpty(fono))
                    telefono.setError(getString(R.string.errorData));
                if(TextUtils.isEmpty(lugar))
                    ubicacion.setError(getString(R.string.errorData));
                else{
                    if(validarleght(name)){
                        if(validarphone(fono)){
                            if(validarleght(lugar)){

                                new Crear_Perfil_Persona.envio(Crear_Perfil_Persona.this).execute(user,name,fono,lugar,sexo.toString(),tipo.toString());

                                Intent intent = new Intent(getApplicationContext(), Good_create.class);
                                startActivity(intent);
                            }else{
                                ubicacion.setError(getString(R.string.errorLugar));
                                ubicacion.requestFocus();
                            }
                        }else{
                            telefono.setError(getString(R.string.errorFono));
                            telefono.requestFocus();
                        }
                    }else{
                        nombre.setError(getString(R.string.errorName));
                        nombre.requestFocus();
                    }
                }

            }
        });
    }

    public static class envio extends AsyncTask<String,Void,String> {
        private WeakReference<Context> context;
        public envio(Context context){
            this.context = new WeakReference<>(context);
        }
        protected String doInBackground(String... params){
            String regis_url = "https://chamberapp.000webhostapp.com/perfil.php";
            String result;

            try{
                URL url = new URL(regis_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String user = params[0];
                String name = params[1];
                String fono = params[2];
                String lugar = params[3];
                String sexo = params[4];
                String tipo = params[5];

                String data = URLEncoder.encode("user","UTF-8")+"="+ URLEncoder.encode(user,"UTF-8")
                        +"&"+ URLEncoder.encode("name","UTF-8")+"="+ URLEncoder.encode(name,"UTF-8")
                        +"&"+ URLEncoder.encode("fono","UTF-8")+"="+ URLEncoder.encode(fono,"UTF-8")
                        +"&"+ URLEncoder.encode("lugar","UTF-8")+"="+ URLEncoder.encode(lugar,"UTF-8")
                        +"&"+ URLEncoder.encode("sexo","UTF-8")+"="+ URLEncoder.encode(sexo,"UTF-8")
                        +"&"+ URLEncoder.encode("tipo","UTF-8")+"="+ URLEncoder.encode(tipo,"UTF-8");
                bufferedWriter.write(data);
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                InputStream inputStream = httpsURLConnection.getInputStream();
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream,StandardCharsets.UTF_8));
                StringBuilder stringBuilder = new StringBuilder();

                String line;
                while ((line = bufferedReader.readLine())!=null){
                    stringBuilder.append(line);
                }
                result = stringBuilder.toString();

                bufferedReader.close();
                inputStream.close();
                httpsURLConnection.disconnect();
            } catch (MalformedURLException e){
                Log.d("MiAPP","URL MALA");
                result = "ERROR";
            } catch (IOException e){
                Log.d("MiAPP","Error de red");
                result = "Error de red";
            }
            return result;
        }
        protected void onPostExecute(String result){
            Toast.makeText(context.get(),result,Toast.LENGTH_LONG).show();
        }
    }

    private boolean validarleght(String a) {
        int length = a.length();
        if(length<8){
            return false;
        }else{
            return true;
        }
    };

    private boolean validarphone(String a) {
        int length = a.length();
        if(length>=8){
            char[] chars = a.toCharArray();
            String first = String.valueOf(chars[0]);
            if (first.contains("+"))
                return true;
            else
                return false;
        }else{
            return false;
        }
    };
}
