package com.example.projetopresencaaluno;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.projetopresencaaluno.modelos.Disciplina;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class TelaRegistroPresenca extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tela_registro_presenca);

        String descricaoPresenca = getIntent().getExtras().getString("descricaoPresenca");
        String descricaoDisciplina = getIntent().getExtras().getString("descricaoDisciplina");
        String descricaoAula = getIntent().getExtras().getString("descricaoAula");
        String descricaoLocalizacao = getIntent().getExtras().getString("descricaoLocalizacao");

        TextView viewAula = (TextView) findViewById(R.id.viewAulaTelaRegistro);
        TextView viewLocalizacao = (TextView) findViewById(R.id.viewLocalizacaoTelaRegistro);
        TextView txtPresenca = (TextView) findViewById(R.id.txtPresencaTelaRegistro);
        Spinner spinner = (Spinner) findViewById(R.id.spinnerTelaRegistro);

        viewAula.setText(descricaoAula);
        viewLocalizacao.setText(descricaoLocalizacao);
        txtPresenca.setText(descricaoPresenca);

        initSpinner(spinner, descricaoDisciplina);


        Button btnSair = (Button) findViewById(R.id.btnSairTelaRegistro);
        btnSair.setOnClickListener(s -> {
            finish();
        });

    }

    public void initSpinner(Spinner spinner, String value) {
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(TelaRegistroPresenca.this, android.R.layout.simple_spinner_item,toArrayVector(value));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        spinner.setEnabled(false);

    }

    public String[] toArrayVector(String value) {
        String[] stringDisciplinas = new String[1];

        stringDisciplinas[0] = value;

        return stringDisciplinas;
    }
}