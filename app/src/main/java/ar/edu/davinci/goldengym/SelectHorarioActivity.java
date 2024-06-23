package ar.edu.davinci.goldengym;

import android.content.Intent;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SelectHorarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_horario);

        LinearLayout linearLayout = findViewById(R.id.botonHorario);
        String[] listaHorario = {"9:00 HS", "13:00 HS", "15:00 HS", "16:00 HS", "19:00 HS", "20:00 HS"};

        for (String horario : listaHorario) {
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

            relativeLayout.addView(crearTextViewHorario(horario));
            relativeLayout.addView(crearTextViewFlecha());

            relativeLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView textView = v.findViewById(R.id.text_view_horario);
                    String textoHorario = textView.getText().toString();

                    Intent intent = new Intent(SelectHorarioActivity.this, ReservaActivity.class);
                    intent.putExtra("direccion_reserva", getIntent().getStringExtra("direccion_reserva"));
                    intent.putExtra("fecha_reserva", getIntent().getStringExtra("fecha_reserva"));
                    intent.putExtra("horario_reserva", textoHorario);

                    startActivity(intent);
                }
            });

            // Agrego RelativeLayout a la vista
            linearLayout.addView(relativeLayout);
        }
    }

    private TextView crearTextViewHorario(String horario) {
        int colorTexto = getResources().getColor(R.color.texto_primario);

        // TextView Gimnasio
        TextView textView = new TextView(this);
        textView.setId(R.id.text_view_horario); // ID
        textView.setText(horario);
        textView.setTextColor(colorTexto); // color de texto
        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16); // tamaño del texto

        // TextView - Parametros
        RelativeLayout.LayoutParams textViewParametros = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT
        );
        textViewParametros.addRule(RelativeLayout.ALIGN_PARENT_START);
        textViewParametros.addRule(RelativeLayout.CENTER_VERTICAL);
        textViewParametros.setMarginStart(32); // margen de inicio
        textViewParametros.setMarginEnd(8); // margen final
        textView.setLayoutParams(textViewParametros);

        return textView;
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
