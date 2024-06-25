package ar.edu.davinci.goldengym;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VistaReservasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_reserva);
        MyApp app = (MyApp) getApplicationContext();
        String gymClass = app.getGymClass();
        String reserva = gymClass + " " + getIntent().getStringExtra("fecha_reserva") + ", " + getIntent().getStringExtra("horario_reserva") + ", " + getIntent().getStringExtra("direccion_reserva");

        TextView textViewReserva = findViewById(R.id.text_view_vista_reserva);

        textViewReserva.setText(reserva);
    }
}
