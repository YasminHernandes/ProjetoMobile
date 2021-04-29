package com.example.projetopresencaaluno;

import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentActivity;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.projetopresencaaluno.modelos.Disciplina;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Objects;
import java.util.TimeZone;

public class SelecaoDisciplina extends FragmentActivity {

    public double latitude = 0d;
    public double longitude = 0d;

    public String[] dias = {"Domingo", "Segunda Feira", "Terça Feira", "Quarta Feira", "Quinta Feira", "Sexta Feixa", "Sábado"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selecao_disciplina);

        TextView viewAula = (TextView) findViewById(R.id.viewAula);
        TextView viewLocalizacao = (TextView) findViewById(R.id.viewLocalizacao);
        TextView txtPresenca = (TextView) findViewById(R.id.txtPresenca);
        Button btnRegistrarPresenca = (Button) findViewById(R.id.btnRegistrarPresenca);
        Button btnDesistir = (Button) findViewById(R.id.btnDesistir);

        TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
        TimeZone.setDefault(tz);
        Calendar ca = GregorianCalendar.getInstance(tz);

        List<Disciplina> disciplinas = new ArrayList<>();
        disciplinas.add(new Disciplina("SEM DISCIPLINA HOJE", 0, 0, 0));
        disciplinas.add(new Disciplina("LINGUAGENS FORMAIS E AUTÔMATOS", 19*60 + 10, 21*60 + 50, 2));
        disciplinas.add(new Disciplina("TRABALHO DE GRADUAÇÃO INTERDISCIPLINAR I", 19*60 + 10, 21*60 + 50, 3));
        disciplinas.add(new Disciplina("PROGRAMAÇÃO PARA DISPOSITIVOS MÓVEIS", 19*60 + 10, 21*60 + 50, 4));
        disciplinas.add(new Disciplina("FUNDAMENTOS DE INTELIGÊNCIA ARTIFICIAL", 19*60 + 10, 21*60 + 50, 5));

        verificaEBuscaLocalizacao(viewLocalizacao);


        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        initSpinner(spinner, disciplinas);


        viewAula.setText("Aula de Hoje (" + dias[ca.get(Calendar.DAY_OF_WEEK) - 1] + ") - " + new SimpleDateFormat("dd/MM/yyyy").format(ca.getTime()));

        btnDesistir.setOnClickListener(s -> {
            finish();
                });
        btnRegistrarPresenca.setOnClickListener(s -> {
            if(localizacaoValidaParaMarcarPresenca()) {
                if(horarioValidoParaMarcarPresenca(ca, buscaDisciplinaHoje(disciplinas, ca.get(Calendar.DAY_OF_WEEK)))) {
                    Log.i("Teste", "Marcou");
                    btnRegistrarPresenca.setVisibility(View.INVISIBLE);

                    txtPresenca.setText("Presença registra em ".concat(new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date())));

                    btnDesistir.setBackgroundResource(R.drawable.buttonshapebackground);
                    btnDesistir.setTextColor(Color.parseColor("#ffffff"));
                    btnDesistir.setText("Sair");

                } else {
                    Toast.makeText(getApplicationContext(), "Não está dentro do horario da disciplina",Toast.LENGTH_LONG).show();
                    Log.i("Teste", "Não está dentro do horario da disciplina");
                }
            } else {
                Toast.makeText(getApplicationContext(), "Você nao está no lugar certo",Toast.LENGTH_LONG).show();
                Log.i("Teste", "Você nao está no lugar certo");

            }
        });
    }

    public boolean localizacaoValidaParaMarcarPresenca() {
        Double latitudeUNICID = -23.536286105990403;
        Double longitudeUNICID = -46.560337171952156;

        return Objects.equals(latitude, latitudeUNICID) && Objects.equals(longitude, longitudeUNICID);
    }

    public boolean horarioValidoParaMarcarPresenca(Calendar calendar, Disciplina disciplina) {
        Integer horarioInicial = disciplina.getIniciaEm();
        Integer horarioFinal = disciplina.getFinalizaEm();

        Integer minutosAtual = calendar.get(Calendar.HOUR_OF_DAY) * 60 + calendar.get(Calendar.MINUTE);



        return horarioInicial <= minutosAtual && horarioFinal >= minutosAtual;
    }

    public Disciplina buscaDisciplinaHoje(List<Disciplina> disciplinas, Integer diaHoje) {
        Disciplina disciplina = null;

       try {
           disciplina = disciplinas.stream().filter(obj -> Objects.equals(obj.getDiaSemana(), diaHoje)).findFirst().get();
       }catch(Exception e ) {
           Log.i("App", "Deu erro");
       }
        return disciplina;
    }

    public void initSpinner(Spinner spinner, List<Disciplina> disciplinas) {
        TimeZone tz = TimeZone.getTimeZone("America/Sao_Paulo");
        TimeZone.setDefault(tz);
        Calendar ca = GregorianCalendar.getInstance(tz);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(SelecaoDisciplina.this, android.R.layout.simple_spinner_item,toArrayVector(disciplinas));

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        Disciplina disciplina = buscaDisciplinaHoje(disciplinas, ca.get(Calendar.DAY_OF_WEEK));
        for (int i = 0; i < toArrayVector(disciplinas).length; i++) {
            if(Objects.equals(toArrayVector(disciplinas)[i], disciplina.getNome())) {
                spinner.setSelection(i);
                break;
            }
        }

        spinner.setEnabled(false);



    }

    public String[] toArrayVector(List<Disciplina> disciplinas) {
        String[] stringDisciplinas = new String[5];

        for (int i = 0; i < 5; i++) {
            stringDisciplinas[i] = disciplinas.get(i).getNome();
        }

        return stringDisciplinas;
    }



    public void verificaEBuscaLocalizacao(TextView viewLocalizacao) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        } else {
            try {
                LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);

                LocationListener locationListener = new LocationListener() {
                    public void onLocationChanged(Location location) {
                        latitude = location.getLatitude();
                        longitude = location.getLongitude();
                        viewLocalizacao.setText("Localização: ".concat(location.getLatitude()+", ").concat(location.getLongitude()+""));
                    }

                    public void onStatusChanged(String provider, int status, Bundle extras) {
                    }

                    public void onProviderEnabled(String provider) {
                    }

                    public void onProviderDisabled(String provider) {
                    }
                };
                // Determina a atualização do GPS:
                // minTimeMs: intervalo de tempo mínimo entre atualizações de localização em milissegundos
                // minDistanceM: distância mínima entre atualizações de localização em metros
                locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 10, 1, locationListener);
            } catch (SecurityException ex) {
                Toast.makeText(this, ex.getMessage(), Toast.LENGTH_LONG).show();
            }
        }

    }

}