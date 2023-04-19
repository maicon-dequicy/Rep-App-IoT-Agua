package br.com.aplicativomonitoramentoagua;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;


public class Adicionar_Sensor extends AppCompatActivity
{
    private Toolbar tbAd;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adicionar_sensor);

        tbAd = (Toolbar) findViewById(R.id.toolbar_adicionar);
        setSupportActionBar(tbAd);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }

}