package ar.edu.davinci.goldengym;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start);
    }

    public void letsgo(View view){
        Intent intent = new Intent(getApplicationContext(), SelectActividadActivity.class);
        startActivity(intent);
    }

    public void verReservas(View view){
        Intent intent = new Intent(getApplicationContext(), VistaReservasActivity.class);
        startActivity(intent);
    }
}
