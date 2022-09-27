package com.example.charmer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class Good_create extends AppCompatActivity {

    Button btn_CrearPerfil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.good_create);

        btn_CrearPerfil = (Button) findViewById(R.id.btn_crearperfil);


        btn_CrearPerfil.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = getIntent().getExtras().getString("mail");
                String pass = getIntent().getExtras().getString("pass");

                Intent Seguir = new Intent(getApplicationContext(), TipoUsuario.class);
                Seguir.putExtra("mail", mail);
                Seguir.putExtra("pass", pass);
                startActivity(Seguir);
            }
        });
    }
}
