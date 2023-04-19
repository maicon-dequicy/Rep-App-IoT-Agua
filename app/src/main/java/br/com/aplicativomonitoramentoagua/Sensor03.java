package br.com.aplicativomonitoramentoagua;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Sensor03 extends AppCompatActivity
{
    private Toolbar t;
    private Button BTSensor01;
    private Button BTSensor02;
    private Button BTSensor03;
    private Button BTSensor04;
    private String previsaoTemperatura;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference valorTeste = FirebaseDatabase.getInstance().getReference();
    private TextView resultadoSensor;
    private DatabaseReference ref4;
    private FloatingActionButton adicionaSensor;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor03);
        cria_botoes();

        ref4 = referencia.child("PAI").child("LastRecord").child("Turbidez");
        ref4.addListenerForSingleValueEvent(
                new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        String resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        System.out.println("Erro!");
                    }
                }
        );

        t = (Toolbar) findViewById(R.id.toollbarSensor03);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lineChart = (LineChart) findViewById(R.id.grafTurb);

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0, 2500f));
        yValues.add(new Entry(1, 2800f));
        yValues.add(new Entry(2, 3000f));
        yValues.add(new Entry(3, 3500f));
        yValues.add(new Entry(4, 3000f));

        LineDataSet set1 = new LineDataSet(yValues, "Turbidez");
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
        direita.setAxisMinimum(1000f);
        direita.setAxisMaximum(5000f);
        esquerda.removeAllLimitLines();
        esquerda.setAxisMaximum(5000f);
        esquerda.setAxisMinimum(1000f);
        esquerda.enableGridDashedLine(10f, 10f, 0);
        esquerda.setDrawLimitLinesBehindData(true);
        lineChart.getAxisRight().setEnabled(true);

    }

    private void cria_botoes()
    {
        adicionaSensor = (FloatingActionButton) findViewById(R.id.adicionarNovoSensor03);
        resultadoSensor = (TextView) findViewById(R.id.resultadoSensor03);
        BTSensor01 = (Button) findViewById(R.id.TelaTB_SensorTemperatura);
        BTSensor02 = (Button) findViewById(R.id.TelaTB_SensorPH);
        BTSensor04 = (Button) findViewById(R.id.TelaTB_Sensor04);
    }
    public void atualizarTurb(View view) {

        ref4.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                        toastAtualizarTurb(view);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Erro!");
                    }
                }
        );
    }
    public void toastAtualizarTurb(View view)
    {
        Toast.makeText(
                getApplicationContext(), "Valores Atualizados!",Toast.LENGTH_LONG
        ).show();
    }

    //transicoes a partir da tela do sensor de turbidez
    public void irTelaTemperatura_TB(View view)
    {
        Intent intent = new Intent(Sensor03.this, nova_perfilGeral.class);
        startActivity(intent);
    }

    public void irTelaPH_TB(View view)
    {
        Intent intent = new Intent(Sensor03.this, Sensor02.class);
        startActivity(intent);
    }
    public void irTelaTDS(View view)
    {
        Intent intent = new Intent(Sensor03.this, Sensor04.class);
        startActivity(intent);
    }
    public void irTelaAdicionaSensorTurb(View view)
    {
        Intent intent = new Intent(Sensor03.this, Adicionar_Sensor.class);
        startActivity(intent);
    }
}