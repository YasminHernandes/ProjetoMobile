package com.example.projetopresencaaluno;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button btnConectar = (Button) findViewById(R.id.btnConectar);
        EditText edtSenha = (EditText) findViewById(R.id.edtSenha);
        EditText edtRGM = (EditText) findViewById(R.id.edtRGM);

        Map<String,String> alunos = new HashMap<>();
        alunos.put("19709021", "123");
        alunos.put("20363974", "123");
        alunos.put("19192363", "123");
        alunos.put("19815557", "123");

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SelecaoDisciplina.class));
            }
        });

        Button btnSair = (Button) findViewById(R.id.btnSair);
        btnSair.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.finish();
            }
        });

        btnConectar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String senha = alunos.get(edtRGM.getText().toString());


                if(senha != null && senha.equals(edtSenha.getText().toString())) {
                    startActivity(new Intent(MainActivity.this, SelecaoDisciplina.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Usuário não encontrado ou senha inválida.",Toast.LENGTH_LONG).show();
                }
            }
        });
    }
}