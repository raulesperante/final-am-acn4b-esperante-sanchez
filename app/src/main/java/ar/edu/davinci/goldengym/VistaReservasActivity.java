package ar.edu.davinci.goldengym;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class VistaReservasActivity extends AppCompatActivity {

    private static final String TAG = "VistaReservasActivity";
    private TextView textViewReserva;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vista_reserva);

        textViewReserva = findViewById(R.id.text_view_vista_reserva);

        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        String emailUsuario = currentUser != null ? currentUser.getEmail() : null;

        if (emailUsuario != null) {
            String emailUsuarioAjustado = emailUsuario.replace(".", "_").replace("#", "_")
                    .replace("$", "_").replace("[", "_")
                    .replace("]", "_");

            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("reservas").child(emailUsuarioAjustado);

            databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    List<String> reservas = new ArrayList<>();
                    for (DataSnapshot reservaSnapshot : dataSnapshot.getChildren()) {
                        Reserva reserva = reservaSnapshot.getValue(Reserva.class);
                        if (reserva != null) {
                            reservas.add(reserva.toString());
                        }
                    }
                    mostrarReservas(reservas);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Error al leer las reservas", databaseError.toException());
                }
            });
        }
    }

    private void mostrarReservas(List<String> reservas) {
        if (reservas.isEmpty()) {
            textViewReserva.setText("No hay reservas");
        } else {
            StringBuilder builder = new StringBuilder();
            for (String reserva : reservas) {
                builder.append(reserva).append("\n\n");
            }
            textViewReserva.setText(builder.toString());
        }
    }
}
