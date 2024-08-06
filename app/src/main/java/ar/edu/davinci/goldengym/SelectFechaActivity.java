package ar.edu.davinci.goldengym;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SelectFechaActivity extends AppCompatActivity {

    private static final String TAG = "SelectFechaActivity";
    private DatabaseReference bd;
    private List<String> fechas;
    private String direccionSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_fecha);

        direccionSeleccionada = getIntent().getStringExtra("direccion_reserva");

        if (direccionSeleccionada != null) {
            bd = FirebaseDatabase.getInstance().getReference("GymActividades");

            bd.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    fechas = new ArrayList<>();

                    for (DataSnapshot actividadSnapshot : dataSnapshot.getChildren()) {
                        Map<String, Object> actividadData = (Map<String, Object>) actividadSnapshot.getValue();
                        if (actividadData != null && actividadData.containsKey("direcciones")) {
                            Map<String, Object> direccionesData = (Map<String, Object>) actividadData.get("direcciones");
                            for (Map.Entry<String, Object> entry : direccionesData.entrySet()) {
                                Map<String, Object> direccionData = (Map<String, Object>) entry.getValue();
                                String direccion = (String) direccionData.get("direccion");
                                if (direccion != null && direccion.equals(direccionSeleccionada)) {
                                    List<String> fechasList = (List<String>) direccionData.get("fechas");
                                    if (fechasList != null) {
                                        fechas.addAll(fechasList);
                                    }
                                    break;
                                }
                            }
                        }
                    }
                    actualizar();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Log.w(TAG, "Error al leer los valores de la base de datos", databaseError.toException());
                }
            });
        }
    }

    private void actualizar() {
        LinearLayout linearLayout = findViewById(R.id.botonFecha);
        linearLayout.removeAllViews();

        for (String fecha : fechas) {
            // RelativeLayout
            RelativeLayout relativeLayout = new RelativeLayout(this);

            // RelativeLayout Parametros
            RelativeLayout.LayoutParams layoutParametros = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    150
            );
            layoutParametros.setMargins(0, 0, 0, 50);
            relativeLayout.setLayoutParams(layoutParametros);
            relativeLayout.setBackgroundResource(R.color.menu_item); // color de fondo

            // Crear TextView Direccion y Flecha
            TextView textViewDireccion = crearTextViewFecha(fecha);
            TextView textViewFlecha = crearTextViewFlecha();

            // Configurar el Tag del RelativeLayout con la direccion
            relativeLayout.setTag(fecha);

            // Agregar TextViews a RelativeLayout
            relativeLayout.addView(textViewDireccion);
            relativeLayout.addView(textViewFlecha);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String textoFecha = (String) v.getTag();

                    Intent intent = new Intent(SelectFechaActivity.this, SelectHorarioActivity.class);
                    intent.putExtra("direccion_reserva", direccionSeleccionada);
                    intent.putExtra("fecha_reserva", textoFecha);

                    startActivity(intent);
                }
            });

            // Agregar RelativeLayout a la vista
            linearLayout.addView(relativeLayout);
        }
    }

    private TextView crearTextViewFecha(String fecha) {
        int colorTexto = getResources().getColor(R.color.texto_primario);

        // TextView
        TextView textViewFecha = new TextView(this);
        textViewFecha.setId(R.id.text_view_fecha);
        textViewFecha.setText(fecha);
        textViewFecha.setTextColor(colorTexto); // color de texto
        textViewFecha.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // tamaño del texto

        // TextView - Parametros
        RelativeLayout.LayoutParams textViewParametros = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParametros.addRule(RelativeLayout.ALIGN_PARENT_START);
        textViewParametros.addRule(RelativeLayout.CENTER_VERTICAL);
        textViewParametros.setMarginStart(32); // margen de inicio
        textViewParametros.setMarginEnd(8); // margen final
        textViewFecha.setLayoutParams(textViewParametros);

        return textViewFecha;
    }

    private TextView crearTextViewFlecha() {
        int colorTexto = getResources().getColor(R.color.texto_primario);

        // TextView Icono Flecha
        TextView textViewFlecha = new TextView(this);
        textViewFlecha.setId(View.generateViewId()); // Generar un ID único
        textViewFlecha.setText(R.string.flecha);
        textViewFlecha.setTextSize(TypedValue.COMPLEX_UNIT_SP, 20); // tamaño del texto
        textViewFlecha.setTextColor(colorTexto); // color del texto

        // TextView Icono Flecha - Parametros
        RelativeLayout.LayoutParams textViewFlechaParametros = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewFlechaParametros.addRule(RelativeLayout.ALIGN_PARENT_END);
        textViewFlechaParametros.addRule(RelativeLayout.CENTER_VERTICAL);
        textViewFlechaParametros.setMarginEnd(50); // margen derecho
        textViewFlecha.setLayoutParams(textViewFlechaParametros);

        return textViewFlecha;
    }
}