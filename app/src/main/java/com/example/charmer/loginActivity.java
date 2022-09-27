package com.example.charmer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class loginActivity extends AppCompatActivity {

    EditText correo, password;
    TextView Registrarse;
    Button ingresar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);

        correo = (EditText) findViewById(R.id.input_correo);
        password = (EditText) findViewById(R.id.input_password);
        Registrarse = (TextView) findViewById(R.id.linkRegistrar);
        ingresar = (Button) findViewById(R.id.btn_ingreso);

        DBHelper DB = new DBHelper(this);

        ingresar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String user = correo.getText().toString();
                String pass = password.getText().toString();

                if(TextUtils.isEmpty(user) || TextUtils.isEmpty(pass))
                    Toast.makeText(loginActivity.this, "llena los datos",Toast.LENGTH_SHORT).show();
                else{
                    Boolean checkuserpass = DB.checkusernamepassword(user,pass);

                    if(checkuserpass==true){
                        Toast.makeText(loginActivity.this, "Inicio exitoso",Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), Good_inicio.class);

                        correo.setText("");
                        password.setText("");

                        startActivity(intent);

                    }else{
                        Toast.makeText(loginActivity.this, "Inicio fallido",Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        Registrarse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Registrar = new Intent(getApplicationContext(), RegistroActivity.class);
                startActivity(Registrar);
            }
        });
    }
}
