package ar.edu.davinci.goldengym;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectFechaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_fecha);

        LinearLayout linearLayout = findViewById(R.id.botonFecha);
        String[] listaDias = {"Lunes", "Martes", "Miercoles", "Jueves", "Viernes", "Sabado"};
        String[] listaFechasMock = {"8 de Julio", "9 de Julio", "10 de Julio", "11 de Julio", "12 de Julio", "13 de Julio"};

        int indice_fecha = 0;
        for (String dia : listaDias) {
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

            if (indice_fecha <= listaFechasMock.length) {
                relativeLayout.addView(crearTextViewFecha(dia, listaFechasMock[indice_fecha]));
                indice_fecha++;
            }

            relativeLayout.addView(crearTextViewFlecha());

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = v.findViewById(R.id.text_view_fecha);
                    String textoFecha = textView.getText().toString();

                    Intent intent = new Intent(SelectFechaActivity.this, SelectHorarioActivity.class);
                    intent.putExtra("direccion_reserva", getIntent().getStringExtra("direccion_reserva"));
                    intent.putExtra("fecha_reserva", textoFecha);

                    startActivity(intent);
                }
            });

            // Agrego RelativeLayout a la vista
            linearLayout.addView(relativeLayout);
        }

    }

    private TextView crearTextViewFecha(String dia, String fecha) {
        int colorTexto = getResources().getColor(R.color.texto_primario);

        // TextView
        TextView textViewFecha = new TextView(this);
        textViewFecha.setId(R.id.text_view_fecha); // ID
        textViewFecha.setText(String.format("%s %s", dia, fecha));
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
        textViewFlecha.setId(View.generateViewId()); // ID
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
