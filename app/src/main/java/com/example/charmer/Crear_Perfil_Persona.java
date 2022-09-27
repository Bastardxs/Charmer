package com.example.charmer;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
                String mail = getIntent().getExtras().getString("mail");
                String pass = getIntent().getExtras().getString("pass");
                String name = nombre.getText().toString();
                String fono = telefono.getText().toString();
                String lugar = ubicacion.getText().toString();
                String sexo = "";

                if (RB_Ho.isChecked() == true) {
                    sexo = "Hombre";

                } else if (RB_Mu.isChecked() == true) {
                    sexo = "Mujer";

                }else if (RB_O.isChecked() == true) {
                    sexo = "Otro";

                } else {
                    Toast.makeText(getApplicationContext(), "Seleccione su sexo", Toast.LENGTH_SHORT).show();
                }
                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(fono) || TextUtils.isEmpty(lugar))
                    Toast.makeText(Crear_Perfil_Persona.this, "Rellena todos los datos",Toast.LENGTH_SHORT).show();
                else{
                    if(validarleght(name)){
                        if(validarphone(fono)){
                            if(validarleght(lugar)){
                                Boolean checkuser = DB.checkusername(mail);
                                if(checkuser == false){
                                    DB.insertData(mail, pass, name, fono, lugar, sexo);
                                    Intent intent = new Intent(getApplicationContext(), Perfil_Persona.class);
                                    intent.putExtra("mail",mail);
                                    startActivity(intent);
                                }else{
                                    Toast.makeText(Crear_Perfil_Persona.this,"Registro invalido",Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(Crear_Perfil_Persona.this,"Ubicacion invalida",Toast.LENGTH_SHORT).show();
                            }
                        }else{
                            Toast.makeText(Crear_Perfil_Persona.this,"Telefono invalido",Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(Crear_Perfil_Persona.this,"Nombre invalido",Toast.LENGTH_SHORT).show();
                    }
                }

            }
        });
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
