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
import com.github.mikephil.charting.components.LimitLine;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.formatter.ValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.github.mikephil.charting.listener.OnChartGestureListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class Sensor02 extends AppCompatActivity
{
    private Toolbar t;
    private Button Sensor01;
    private Button Sensor02;
    private Button Sensor03;
    private Button Sensor04;
    private TextView resultadoSensorPh;

    private String previsaoTemperatura;
    private DatabaseReference referencia = FirebaseDatabase.getInstance().getReference();
    private DatabaseReference valorTeste = FirebaseDatabase.getInstance().getReference();
    private TextView resultadoSensor;
    private DatabaseReference ref3;

    private FloatingActionButton adicionarSensor;
    private LineChart lineChart;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sensor02);
        cria_botoes();

        ref3 = referencia.child("PAI").child("LastRecord").child("PH");
        ref3.addListenerForSingleValueEvent(
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

        t = (Toolbar) findViewById(R.id.toollbarSensor02);
        setSupportActionBar(t);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        //pega id
        lineChart = (LineChart) findViewById(R.id.grafPH);

        LimitLine superior = new LimitLine(9.5f, "Perigo");
        superior.setLineWidth(4f);
        superior.enableDashedLine(10f, 10f, 0);
        superior.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_TOP);
        superior.setTextSize(15f);

        LimitLine inferior = new LimitLine(5f, "Alerta");
        inferior.setLineWidth(4f);
        inferior.enableDashedLine(10f, 10f, 10f);
        inferior.setLabelPosition(LimitLine.LimitLabelPosition.RIGHT_BOTTOM);
        inferior.setTextSize(15f);

        ArrayList<Entry> yValues = new ArrayList<>();
        yValues.add(new Entry(0, 6f));
        yValues.add(new Entry(1, 2f));
        yValues.add(new Entry(2, 7f));
        yValues.add(new Entry(3, 6.4f));
        yValues.add(new Entry(4, 6f));

        LineDataSet set1 = new LineDataSet(yValues, "pH");
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
        direita.setAxisMaximum(15f);
        esquerda.removeAllLimitLines();
        esquerda.addLimitLine(superior);
        esquerda.addLimitLine(inferior);
        esquerda.setAxisMaximum(15f);
        esquerda.setAxisMinimum(0f);
        esquerda.enableGridDashedLine(10f, 10f, 0);
        esquerda.setDrawLimitLinesBehindData(true);
        lineChart.getAxisRight().setEnabled(true);

    }

    public class MyAxisFormatter extends ValueFormatter {
        public String[] mdias;

        public MyAxisFormatter(String[] dias) {
            this.mdias = dias;
        }
        @Override
        public String getFormattedValue(float value) {
            return mdias[(int) value];
        }
    }
    private void cria_botoes()
    {
        adicionarSensor = (FloatingActionButton)findViewById(R.id.adicionarNovoSensorSensor02);
        resultadoSensor = (TextView) findViewById(R.id.resultadoSensor02);
        Sensor01 = (Button) findViewById(R.id.BotaoTemp);
        Sensor03 = (Button) findViewById(R.id.Turbidez);
        Sensor04 = (Button) findViewById(R.id.TelaPH_TDS);
    }
    public void atualizarPH(View view) {

        ref3.addListenerForSingleValueEvent(
                new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String resultado = snapshot.getValue().toString();
                        resultadoSensor.setText(resultado);
                        toastAtualizarPh(view);
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        System.out.println("Erro!");
                    }
                }
        );
    }
    public void toastAtualizarPh(View view)
    {
        Toast.makeText(
                getApplicationContext(), "Valores Atualizados!",Toast.LENGTH_LONG
        ).show();
    }

    public void irTelaTemperatura(View view)
    {
        Intent intent = new Intent(Sensor02.this, nova_perfilGeral.class);
        startActivity(intent);
    }
    public void irTelaTurbidez(View view)
    {
        Intent intent = new Intent(Sensor02.this, Sensor03.class);
        startActivity(intent);
    }
    public void irTelaTDS(View view)
    {
        Intent intent = new Intent(Sensor02.this, Sensor04.class);
        startActivity(intent);
    }
    public void irTelaAdicionaSensorPh(View view)
    {
        Intent intent = new Intent(Sensor02.this, Adicionar_Sensor.class);
        startActivity(intent);
    }
}