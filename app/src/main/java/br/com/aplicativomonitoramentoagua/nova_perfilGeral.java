package br.com.aplicativomonitoramentoagua;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.widget.Toolbar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import java.util.ArrayList;

public class nova_perfilGeral extends AppCompatActivity{

    private Button EditandoSensor;
    private Button Sensor01;
    private Button Sensor02;
    private Button Sensor03;
    private Button Sensor04;
    private FloatingActionButton adicionarSensor;
    private Toolbar t;
    private String previsaoTemperatura;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference valorTeste = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference atualTemp = FirebaseDatabase.getInstance().getReference();

    private FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    private DatabaseReference myRef = firebaseDatabase.getReference("Sensor").child("PH");

    private TextView resultadoSensor;
    private DatabaseReference ref2, atualTempRecep;
    private BottomNavigationView navigationView;
    private LineChart lineChart;
    private String resultado;
    public float valorTempFB;
    String val;
    @SuppressLint("NewApi")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nova_perfil_geral);
        criaBotoes();

        t = (androidx.appcompat.widget.Toolbar) findViewById(R.id.toollbarPerfil01);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        EditandoSensor = (Button) findViewById(R.id.EditSensor);

        //pega o valor do nó sensores do firebase e exibe na tela
        ref2 = referencia.child("PAI").child("LastRecord").child("Temperatura");
        ref2.addValueEventListener(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Erro!");
                    }
                }
        );
        grafico();
    }

    public void grafico()
    {
        lineChart = (LineChart) findViewById(R.id.grafTemperatura);

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0, 23f));
        yValues.add(new Entry(1, 20f));
        yValues.add(new Entry(2, 28f));
        yValues.add(new Entry(3, 27f));
        yValues.add(new Entry(4, 25f));

        LineDataSet set1 = new LineDataSet(yValues, "Temperatura");
        set1.setFillAlpha(110);
        set1.setLineWidth(3f);
        set1.setValueTextSize(10f);
        set1.setValueTextColor(Color.BLUE);

        ArrayList<ILineDataSet> dataSets = new ArrayList<>();
        dataSets.add(set1);

        LineData data = new LineData(dataSets);
        lineChart.setData(data);

        String[] dias = new String[]{"Dia 01", "Dia 02", "Dia 03", "Dia 04", "Dia 05"};

        XAxis xAxis = lineChart.getXAxis();
        xAxis.setGranularity(1f);

        xAxis.setValueFormatter(new nova_perfilGeral.MyAxisFormatter(dias));

        YAxis esquerda = lineChart.getAxisLeft();
        YAxis direita = lineChart.getAxisRight();
        direita.removeAllLimitLines();
        direita.setAxisMinimum(5f);
        direita.setAxisMaximum(40f);
        esquerda.removeAllLimitLines();
        esquerda.setAxisMaximum(40f);
        esquerda.setAxisMinimum(5f);
        esquerda.enableGridDashedLine(10f, 10f, 0);
        esquerda.setDrawLimitLinesBehindData(true);
        lineChart.getAxisRight().setEnabled(true);
    }
    //formatação do array de dias
   public static class MyAxisFormatter extends ValueFormatter {
       public String[] mdias;

       public MyAxisFormatter(String[] dias) {
           this.mdias = dias;
       }

       @Override
       public String getFormattedValue(float value) {
           return mdias[(int) value];
       }
   }
    //pega ids de botoes, layouts e textviews
    public void criaBotoes()
    {
        adicionarSensor = (FloatingActionButton) findViewById(R.id.adicionarNovoSensorBt);
        EditandoSensor = (Button) findViewById(R.id.EditSensor);
        resultadoSensor = (TextView) findViewById(R.id.resultadoSensor01);
        Sensor02 = (Button) findViewById(R.id.PH);
        Sensor03 = (Button) findViewById(R.id.Turbidez);
        Sensor04 = (Button) findViewById(R.id.SensorTDS);

    }


    //metodo para pegar o valor mais recente da temperatura
    public void atualizar(View view)
    {
        ref2.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                        toastAtualizar(view);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Erro!");
                    }
                }
        );
    }
    //mostra mensagem dizendo que os valores foram atualizados
    public void toastAtualizar(View view)
    {
        Toast.makeText(
                getApplicationContext(), "Valores Atualizados!",Toast.LENGTH_LONG
        ).show();
    }

    // vai à tela do Sensor02 (PH)
    public void irTelaSensor02(View view)
    {
        Intent intent = new Intent(nova_perfilGeral.this, Sensor02.class);
        startActivity(intent);
    }
    public void irTelaSensor03(View view)
    {
        Intent intent = new Intent(nova_perfilGeral.this, Sensor03.class);
        startActivity(intent);
    }

    public void irTelaSensor04(View view)
    {
        Intent intent = new Intent(nova_perfilGeral.this, Sensor04.class);
        startActivity(intent);
    }
    public void irTelaAdicionarNovoSensor(View view)
    {
        Intent intent = new Intent(nova_perfilGeral.this, Adicionar_Sensor.class);
        startActivity(intent);
    }
}