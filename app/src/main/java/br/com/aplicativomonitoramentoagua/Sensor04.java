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

public class Sensor04 extends AppCompatActivity
{
    private Button TemperaturaBotao;
    private Button PHBotao;
    private Button TurbidezBotao;
    private Toolbar t;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference valorTeste = FirebaseDatabase.getInstance().getReference();
    private TextView resultadoSensor;
    private DatabaseReference ref5;
    private FloatingActionButton adicionaSensor;
    private LineChart lineChart;
    private float ultimoDado;
    private String resultado;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor04);
        cria_botoes();

        ref5 = referencia.child("PAI").child("LastRecord").child("TDS");
        ref5.addListenerForSingleValueEvent(
                new ValueEventListener()
                {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot)
                    {
                        resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error)
                    {
                        System.out.println("Erro!");
                    }
                }
        );
        t = (Toolbar) findViewById(R.id.toollbarSensor04);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lineChart = (LineChart) findViewById(R.id.grafTDS);
        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0, 1f));
        yValues.add(new Entry(1, 1f));
        yValues.add(new Entry(2, 3f));
        yValues.add(new Entry(3, 3f));
        yValues.add(new Entry(4, 4f));

        LineDataSet set1 = new LineDataSet(yValues, "TDS");
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
        direita.setAxisMinimum(0f);
        direita.setAxisMaximum(10f);
        esquerda.removeAllLimitLines();
        esquerda.setAxisMaximum(10f);
        esquerda.setAxisMinimum(0f);
        esquerda.enableGridDashedLine(10f, 10f, 0);
        esquerda.setDrawLimitLinesBehindData(true);
        lineChart.getAxisRight().setEnabled(true);

    }

    private void cria_botoes()
    {
        TemperaturaBotao = (Button) findViewById(R.id.TelaTDS_SensorTemperatura);
        PHBotao = (Button) findViewById(R.id.TelaTDS_SensorPH);
        TurbidezBotao = (Button) findViewById(R.id.TelaTDS_SensorTurbidez);
        resultadoSensor = (TextView) findViewById(R.id.resultadoSensor04);
        adicionaSensor = (FloatingActionButton) findViewById(R.id.adicionarNovoSensor04);
    }
    public void atualizarTDS(View view) {

        ref5.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                        toastAtualizarTDS(view);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Erro!");
                    }
                }
        );
    }
    public void toastAtualizarTDS(View view)
    {
        Toast.makeText(
                getApplicationContext(), "Valores Atualizados!",Toast.LENGTH_LONG
        ).show();
    }

    public void irTelaTemperatura(View view)
    {
        Intent intent = new Intent(Sensor04.this, nova_perfilGeral.class);
        startActivity(intent);
    }
    public void irTelaPH(View view)
    {
        Intent intent = new Intent(Sensor04.this, Sensor02.class);
        startActivity(intent);
    }
    public void irTelaTurbidez(View view)
    {
        Intent intent = new Intent(Sensor04.this, Sensor03.class);
        startActivity(intent);
    }
    public void irTelaAdicionaSensorTDS(View view)
    {
        Intent intent = new Intent(Sensor04.this, Adicionar_Sensor.class);
        startActivity(intent);
    }
}