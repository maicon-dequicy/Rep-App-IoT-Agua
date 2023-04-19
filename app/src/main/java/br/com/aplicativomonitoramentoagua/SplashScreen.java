package br.com.aplicativomonitoramentoagua;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mostrarMainActivity();
            }
        }, 2000);
    }

    private void mostrarMainActivity() {
        Intent intent = new Intent(
                SplashScreen.this,nova_perfilGeral.class
        );
        startActivity(intent);
        finish();
    }
}