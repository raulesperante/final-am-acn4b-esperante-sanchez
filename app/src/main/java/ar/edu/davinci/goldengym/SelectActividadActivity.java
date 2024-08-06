package ar.edu.davinci.goldengym;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class SelectActividadActivity extends AppCompatActivity {

    private static final String TAG = "SelectGymClass";
    private DatabaseReference bd;
    private Map<String, Map<String, Object>> gymActividadMap;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_gym_class);

        bd = FirebaseDatabase.getInstance().getReference("GymActividades");

        bd.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                gymActividadMap = new HashMap<>();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    String key = snapshot.getKey();
                    Map<String, Object> value = (Map<String, Object>) snapshot.getValue();
                    gymActividadMap.put(key, value);
                }
                actualizar();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Log.w(TAG, "Error al leer los valores de la base de datos", databaseError.toException());
            }
        });
    }

    private void actualizar() {
        Button btnNatacion = findViewById(R.id.btn_natacion);
        Button btnBoxeo = findViewById(R.id.btn_boxeo);
        Button btnYoga = findViewById(R.id.btn_yoga);
        Button btnPilates = findViewById(R.id.btn_pilates);
        Button btnCrossfit = findViewById(R.id.btn_crossfit);

        btnNatacion.setText(gymActividadMap.get("actividad1").get("nombre").toString());
        btnBoxeo.setText(gymActividadMap.get("actividad2").get("nombre").toString());
        btnYoga.setText(gymActividadMap.get("actividad3").get("nombre").toString());
        btnPilates.setText(gymActividadMap.get("actividad4").get("nombre").toString());
        btnCrossfit.setText(gymActividadMap.get("actividad5").get("nombre").toString());

        btnNatacion.setOnClickListener(this::handleClick);
        btnBoxeo.setOnClickListener(this::handleClick);
        btnYoga.setOnClickListener(this::handleClick);
        btnPilates.setOnClickListener(this::handleClick);
        btnCrossfit.setOnClickListener(this::handleClick);
    }

    public void handleClick(View view) {
        MyApp app = (MyApp) getApplicationContext();

        int id = view.getId();
        String selectedActivityKey = null;

        if (id == R.id.btn_natacion) {
            selectedActivityKey = "actividad1";
        } else if (id == R.id.btn_boxeo) {
            selectedActivityKey = "actividad2";
        } else if (id == R.id.btn_yoga) {
            selectedActivityKey = "actividad3";
        } else if (id == R.id.btn_pilates) {
            selectedActivityKey = "actividad4";
        } else if (id == R.id.btn_crossfit) {
            selectedActivityKey = "actividad5";
        }

        if (selectedActivityKey != null) {
            app.setGymClass(gymActividadMap.get(selectedActivityKey).get("nombre").toString());
        }

        Intent intent = new Intent(getApplicationContext(), SelectDireccionActivity.class);
        intent.putExtra("actividad", selectedActivityKey);
        startActivity(intent);
    }
}