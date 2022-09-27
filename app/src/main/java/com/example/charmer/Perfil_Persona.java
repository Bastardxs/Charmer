package com.example.charmer;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Perfil_Persona extends AppCompatActivity {

    ImageView btn_atras;
    Button btn_editar;
    TextView nombre, correo, password, telefono, ubicacion, sexo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.perfil_persona);

        btn_atras = (ImageView) findViewById(R.id.btn_flechaatras_perfil);
        btn_editar = (Button) findViewById(R.id.btn_EditarPerfil);
        nombre = (TextView) findViewById(R.id.TV_Nombre);
        correo = (TextView) findViewById(R.id.correo_usuario);
        password = (TextView) findViewById(R.id.password_usuario);
        telefono = (TextView) findViewById(R.id.telefono_usuario);
        ubicacion = (TextView) findViewById(R.id.ubicacion_usuario);
        sexo = (TextView) findViewById(R.id.Sexo_Usuario);

        DBHelper DB = new DBHelper(this);

        String mail = getIntent().getExtras().getString("mail");

        SQLiteDatabase db = DB.getWritableDatabase();
        Toast.makeText(Perfil_Persona.this,mail,Toast.LENGTH_SHORT).show();
        Cursor cursor = db.rawQuery("SELECT * FROM users WHERE mail = ?", new String[]{mail});
        if(cursor.moveToFirst()){
            do{
                nombre.setText(cursor.getString(3));
                correo.setText(cursor.getString(1));
                password.setText(cursor.getString(2));
                telefono.setText(cursor.getString(4));
                ubicacion.setText(cursor.getString(5));
                sexo.setText(cursor.getString(6));
            }while (cursor.moveToNext());
        }
        cursor.close();

        btn_atras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent Atras = new Intent(getApplicationContext(), Crear_Perfil_Persona.class);
                startActivity(Atras);
            }
        });

    }
}