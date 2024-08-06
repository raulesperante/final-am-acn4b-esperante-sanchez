package ar.edu.davinci.goldengym;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SelectDireccionActivity extends AppCompatActivity {

    private static final String TAG = "SelectDireccionActivity";
    private DatabaseReference bd;
    private List<String> gymDirecciones;
    private String actividadSeleccionada;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_direccion);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        Intent intent = getIntent();
        actividadSeleccionada = intent.getStringExtra("actividad");

        if (actividadSeleccionada != null) {
            bd = FirebaseDatabase.getInstance().getReference("GymActividades").child(actividadSeleccionada).child("direcciones");

            bd.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    gymDirecciones = new ArrayList<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        Map<String, Object> direccionData = (Map<String, Object>) snapshot.getValue();
                        if (direccionData != null) {
                            String direccion = (String) direccionData.get("direccion");
                            gymDirecciones.add(direccion);
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
        LinearLayout resultados = findViewById(R.id.resultados);
        resultados.removeAllViews();

        for (String direccion : gymDirecciones) {
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
            TextView textViewDireccion = crearTextViewDireccion(direccion);
            TextView textViewFlecha = crearTextViewFlecha();

            // Configurar el Tag del RelativeLayout con la direccion
            relativeLayout.setTag(direccion);

            // Agregar TextViews a RelativeLayout
            relativeLayout.addView(textViewDireccion);
            relativeLayout.addView(textViewFlecha);

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String textoDireccion = (String) v.getTag();

                    Intent intent = new Intent(SelectDireccionActivity.this, SelectFechaActivity.class);
                    intent.putExtra("direccion_reserva", textoDireccion);

                    startActivity(intent);
                }
            });

            // Agregar RelativeLayout a la vista
            resultados.addView(relativeLayout);
        }
    }

    private TextView crearTextViewDireccion(String direccion) {
        int colorTexto = getResources().getColor(R.color.texto_primario);

        // TextView Direccion Gimnasio
        TextView textViewDireccion = new TextView(this);
        textViewDireccion.setId(R.id.text_view_direccion);
        textViewDireccion.setText(direccion);
        textViewDireccion.setTextColor(colorTexto); // color de texto
        textViewDireccion.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // tamaño del texto

        // TextView Direccion Gimnasio - Parametros
        RelativeLayout.LayoutParams textViewDireccionParametros = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewDireccionParametros.addRule(RelativeLayout.ALIGN_PARENT_START);
        textViewDireccionParametros.addRule(RelativeLayout.CENTER_VERTICAL);
        textViewDireccionParametros.setMarginStart(32); // margen de inicio
        textViewDireccionParametros.setMarginEnd(8); // margen final
        textViewDireccion.setLayoutParams(textViewDireccionParametros);

        return textViewDireccion;
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

    public void buscarGimnasio(View view) {
        // Limpiar input texto
        EditText editText = findViewById(R.id.campoBusqueda);
        LinearLayout resultados = findViewById(R.id.resultados);
        resultados.removeAllViews();
        actualizar(editText.getText().toString());
    }

    private void actualizar(String buscarDireccion) {
        LinearLayout resultados = findViewById(R.id.resultados);
        resultados.removeAllViews();

        for (String direccion : gymDirecciones) {
            if (!buscarDireccion.isEmpty()) {
                if (direccion.toLowerCase().startsWith(buscarDireccion.toLowerCase())) {
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
                    TextView textViewDireccion = crearTextViewDireccion(direccion);
                    TextView textViewFlecha = crearTextViewFlecha();

                    // Configurar el Tag del RelativeLayout con la direccion
                    relativeLayout.setTag(direccion);

                    // Agregar TextViews a RelativeLayout
                    relativeLayout.addView(textViewDireccion);
                    relativeLayout.addView(textViewFlecha);

                    relativeLayout.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            String textoDireccion = (String) v.getTag();

                            Intent intent = new Intent(SelectDireccionActivity.this, SelectFechaActivity.class);
                            intent.putExtra("direccion_reserva", textoDireccion);

                            startActivity(intent);
                        }
                    });

                    // Agregar RelativeLayout a la vista
                    resultados.addView(relativeLayout);
                }
            } else {
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
                TextView textViewDireccion = crearTextViewDireccion(direccion);
                TextView textViewFlecha = crearTextViewFlecha();

                // Configurar el Tag del RelativeLayout con la direccion
                relativeLayout.setTag(direccion);

                // Agregar TextViews a RelativeLayout
                relativeLayout.addView(textViewDireccion);
                relativeLayout.addView(textViewFlecha);

                relativeLayout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String textoDireccion = (String) v.getTag();

                        Intent intent = new Intent(SelectDireccionActivity.this, SelectFechaActivity.class);
                        intent.putExtra("direccion_reserva", textoDireccion);

                        startActivity(intent);
                    }
                });

                // Agregar RelativeLayout a la vista
                resultados.addView(relativeLayout);
            }
        }
    }
}