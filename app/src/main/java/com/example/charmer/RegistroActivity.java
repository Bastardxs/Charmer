package com.example.charmer;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.regex.Pattern;

import javax.net.ssl.HttpsURLConnection;

public class RegistroActivity extends AppCompatActivity {

    EditText Correo, password, confpass;
    Button btn_crear;
    TextView link_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.resgistro);

        Correo = (EditText) findViewById(R.id.input_correo_crear);
        password = (EditText) findViewById(R.id.password_crear);
        confpass = (EditText) findViewById(R.id.confpass_crear);
        btn_crear = (Button) findViewById(R.id.btn_crear_cuenta);
        link_login = (TextView) findViewById(R.id.linkLogin);

        DBHelper DB = new DBHelper(this);

        btn_crear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String user = Correo.getText().toString();
                String contrasena = password.getText().toString();
                String repass = confpass.getText().toString();

                if(TextUtils.isEmpty(user))
                    Correo.setError(getString(R.string.errorData));
                if(TextUtils.isEmpty(contrasena))
                    password.setError(getString(R.string.errorData));
                if(TextUtils.isEmpty(repass))
                    confpass.setError(getString(R.string.errorData));
                else{
                    if(validarEmail(user)){
                        if(contrasena.equals(repass)){
                            Boolean checkuser = DB.checkusername(user);
                            if(validarleght(contrasena)){
                                if(checkuser == false){

                                    Toast.makeText(RegistroActivity.this,"usuario registrado",Toast.LENGTH_SHORT).show();

                                    Correo.setText("");
                                    password.setText("");
                                    confpass.setText("");

                                    new envio(RegistroActivity.this).execute(user,contrasena);

                                    Intent intent = new Intent(getApplicationContext(), Good_create.class);
                                    intent.putExtra("mail", user);
                                    intent.putExtra("pass", contrasena);
                                    startActivity(intent);

                                }else{
                                    Correo.setError(getString(R.string.errorUser));
                                    Correo.requestFocus();
                                }
                            }else{
                                password.setError(getString(R.string.errorPass));
                                password.requestFocus();
                            }
                        }else{
                            confpass.setError(getString(R.string.errorConpass));
                            confpass.requestFocus();
                        }
                    }else{
                        Correo.setError(getString(R.string.errorMail));
                        Correo.requestFocus();
                    }
                }
            }
        });
        link_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(),loginActivity.class);
                startActivity(intent);
            }
        });
    }

    public static class envio extends AsyncTask<String,Void,String>{
        private WeakReference<Context> context;
        public envio(Context context){
            this.context = new WeakReference<>(context);
        }
        protected String doInBackground(String... params){
            String regis_url = "https://chamberapp.000webhostapp.com/regis.php";
            String result;

            try{
                URL url = new URL(regis_url);
                HttpsURLConnection httpsURLConnection = (HttpsURLConnection) url.openConnection();
                httpsURLConnection.setRequestMethod("POST");
                httpsURLConnection.setDoOutput(true);
                OutputStream outputStream = httpsURLConnection.getOutputStream();
                BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, StandardCharsets.UTF_8));

                String mail = params[0];
                String pass = params[1];

                String data = URLEncoder.encode("mail","UTF-8")+"="+ URLEncoder.encode(mail,"UTF-8")
                        +"&"+ URLEncoder.encode("pass","UTF-8")+"="+ URLEncoder.encode(pass,"UTF-8");
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

    private boolean validarleght(String contra) {
        int length = contra.length();
        if(length<8){
            return false;
        }else{
            return true;
        }
    };

    private boolean validarEmail(String email) {
        Pattern pattern = Patterns.EMAIL_ADDRESS;
        return pattern.matcher(email).matches();
    };
}