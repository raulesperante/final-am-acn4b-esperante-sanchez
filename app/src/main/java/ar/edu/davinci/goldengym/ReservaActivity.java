package ar.edu.davinci.goldengym;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
public class ReservaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva);

        TextView textViewFecha = findViewById(R.id.textViewFecha);
        TextView textViewHorario = findViewById(R.id.textViewHorario);
        TextView textViewDireccion = findViewById(R.id.textViewLugar);

        textViewFecha.setText(getIntent().getStringExtra("fecha_reserva"));
        textViewHorario.setText(getIntent().getStringExtra("horario_reserva"));
        textViewDireccion.setText(getIntent().getStringExtra("direccion_reserva"));

        Button buttonReserva = findViewById(R.id.btn_reservar);

        buttonReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ReservaActivity.this, VistaReservasActivity.class);
                intent.putExtra("fecha_reserva", getIntent().getStringExtra("fecha_reserva"));
                intent.putExtra("horario_reserva", getIntent().getStringExtra("horario_reserva"));
                intent.putExtra("direccion_reserva", getIntent().getStringExtra("direccion_reserva"));

                startActivity(intent);
            }
        });
    }
}
