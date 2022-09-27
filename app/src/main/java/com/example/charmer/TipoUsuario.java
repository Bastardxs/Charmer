package com.example.charmer;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

public class TipoUsuario extends AppCompatActivity {

    ConstraintLayout btn_empresa, btn_persona;
    Button btnContinuar;
    ImageView btn_atras;
    RadioButton RB_1, RB_2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tipousuario);

        btn_empresa = (ConstraintLayout) findViewById(R.id.btn_cont_empre);
        btn_persona = (ConstraintLayout) findViewById(R.id.btn_cont_per);
        btnContinuar = (Button) findViewById(R.id.btn_Continuar);
        btn_atras = (ImageView) findViewById(R.id.btn_flechaatras_perfil);
        RB_1 = (RadioButton) findViewById(R.id.btnradiopersona);
        RB_2 = (RadioButton) findViewById(R.id.btnradioEmpresa);

        RB_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RB_2.setChecked(false);
            }
        });

        RB_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RB_1.setChecked(false);
            }
        });

        btn_persona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RB_1.setChecked(true);
                RB_2.setChecked(false);
            }
        });


        btn_empresa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                RB_2.setChecked(true);
                RB_1.setChecked(false);



            }
        });

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Atras = new Intent(getApplicationContext(), Good_create.class);
                startActivity(Atras);
            }
        });


        btnContinuar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String mail = getIntent().getExtras().getString("mail");
                String pass = getIntent().getExtras().getString("pass");

                if(RB_1.isChecked() == true){
                    Toast.makeText(getApplicationContext(), "Elegiste persona", Toast.LENGTH_SHORT).show();

                    Intent Seguir = new Intent(getApplicationContext(), Crear_Perfil_Persona.class);
                    Seguir.putExtra("mail", mail);
                    Seguir.putExtra("pass", pass);
                    startActivity(Seguir);

                }else if(RB_2.isChecked() == true){
                    Toast.makeText(getApplicationContext(), "Elegiste empresa", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getApplicationContext(), "Seleccione Una opción", Toast.LENGTH_SHORT).show();
                }
            }
        });



    }
}
