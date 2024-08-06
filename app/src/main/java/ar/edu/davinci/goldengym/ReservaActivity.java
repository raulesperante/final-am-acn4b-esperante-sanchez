package ar.edu.davinci.goldengym;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class ReservaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.reserva);
        MyApp app = (MyApp) getApplicationContext();

        TextView textViewActividad = findViewById(R.id.textViewActividad);
        TextView textViewFecha = findViewById(R.id.textViewFecha);
        TextView textViewHorario = findViewById(R.id.textViewHorario);
        TextView textViewDireccion = findViewById(R.id.textViewLugar);

        textViewActividad.setText(app.getActividad());
        textViewFecha.setText(getIntent().getStringExtra("fecha_reserva"));
        textViewHorario.setText(getIntent().getStringExtra("horario_reserva"));
        textViewDireccion.setText(getIntent().getStringExtra("direccion_reserva"));

        Button buttonReserva = findViewById(R.id.btn_reservar);

        buttonReserva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String actividad = textViewActividad.getText().toString();
                String fecha = textViewFecha.getText().toString();
                String horario = textViewHorario.getText().toString();
                String direccion = textViewDireccion.getText().toString();

                FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
                String emailUsuario = currentUser != null ? currentUser.getEmail() : null;

                if (emailUsuario != null) {
                    String emailUsuarioAjustado = emailUsuario.replace(".", "_").replace("#", "_")
                            .replace("$", "_").replace("[", "_")
                            .replace("]", "_");

                    Reserva reserva = new Reserva(actividad, fecha, horario, direccion);

                    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

                    databaseReference.child("reservas").child(emailUsuarioAjustado).push().setValue(reserva)
                            .addOnCompleteListener(task -> {
                                if (task.isSuccessful()) {
                                    //buttonReserva.setText("Reserva Guardada");
                                    Intent intent = new Intent(ReservaActivity.this, VistaReservasActivity.class);
                                    startActivity(intent);
                                }
                            });
                }
            }
        });
    }
}
