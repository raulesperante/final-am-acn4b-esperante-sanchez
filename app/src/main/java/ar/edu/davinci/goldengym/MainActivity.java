package ar.edu.davinci.goldengym;

import java.util.Collections;
import java.util.List;
import java.util.Arrays;


import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
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
                "Av. Mitre 560, Avellaneda"
        };
        Integer[] indices = {0, 1, 2, 3, 4};
        List<Integer> listaIndices = Arrays.asList(indices);
        Collections.shuffle(listaIndices);
        for (Integer indice : listaIndices) {
            TextView direccion = new TextView(this);
            direccion.setText(gyms[indice]);
            resultados.addView(direccion);
        }
    }
}