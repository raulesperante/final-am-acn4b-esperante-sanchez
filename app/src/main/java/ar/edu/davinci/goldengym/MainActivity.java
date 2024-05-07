package ar.edu.davinci.goldengym;

import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.EditText;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    public void buscarGimnasio(View view){
        // Limpiar input texto
        EditText editText = findViewById(R.id.campoBusqueda);
        if(editText.length() == 0) return;
        editText.setText("");
        LinearLayout resultados = findViewById(R.id.resultados);
        // Elimina todos los TextView existentes
        resultados.removeAllViews();

        // Datos simulados
        String[] gyms = {
                "Florida 100, Microcentro, CABA",
                "Lavalle 500, Microcentro CABA",
                "Av. Caseros 3000, CABA",
                "Av. Belgrano 1120, Avellaneda",
                "Av. Mitre 560, Avellaneda",
                "Av. Acoyte 480, Caballito, CABA",
                "Av. Cordoba 4887, Palermo",
                "Av. Medrano 148,CABA",
                "Moreno 369, CABA",
                "Av. Juramento 223, CABA"
        };

        for (String direccion : gyms) {
            // RelativeLayout
            RelativeLayout relativeLayout = new RelativeLayout(this);

            // RelativeLayout Parametros
            RelativeLayout.LayoutParams layoutParametros = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.MATCH_PARENT,
                    150
            );
            layoutParametros.setMargins(0, 0,0, 50);
            relativeLayout.setLayoutParams(layoutParametros);
            relativeLayout.setBackgroundResource(R.color.menu_item); // color de fondo

            // Agrego Direccion Gimnasio y Icono Flecha a RelativeLayout
            relativeLayout.addView(crearTextViewDireccion(direccion));
            relativeLayout.addView(crearTextViewFlecha());

            // Agrego RelativeLayout a la vista
            resultados.addView(relativeLayout);
        }
    }

    private TextView crearTextViewDireccion(String direccion) {
        int colorTexto = getResources().getColor(R.color.texto_primario);

        // TextView Direccion Gimnasio
        TextView textViewDireccion = new TextView(this);
        textViewDireccion.setId(View.generateViewId()); // ID
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