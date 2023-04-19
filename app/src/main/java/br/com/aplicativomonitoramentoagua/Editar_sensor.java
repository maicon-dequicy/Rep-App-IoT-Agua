package br.com.aplicativomonitoramentoagua;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class Editar_sensor extends AppCompatActivity {


    private Toolbar tb;
    private EditText nomeSensor;
    private EditText topicoMqtt;
    private Button confirmarEdicao;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_sensor);

        tb = (Toolbar) findViewById(R.id.toolbarEditarSensor);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        criaBotao_Input();
    }

    public void criaBotao_Input()
    {
        nomeSensor = (EditText) findViewById(R.id.NomeSensor);
        topicoMqtt = (EditText) findViewById(R.id.TopicoMqtt);
    }
}