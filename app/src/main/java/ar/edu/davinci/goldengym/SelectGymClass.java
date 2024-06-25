package ar.edu.davinci.goldengym;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class SelectGymClass extends  AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.select_gym_class);
    }


    public void handleClick(View view){
        MyApp app = (MyApp) getApplicationContext();

        int id = view.getId();

        if (id == R.id.btn_natacion) {
            app.setGymClass("Natación");
        } else if (id == R.id.btn_boxeo) {
            app.setGymClass("Boxeo");
        } else if (id == R.id.btn_yoga) {
            app.setGymClass("Yoga");
        } else if (id == R.id.btn_pilates) {
            app.setGymClass("Pilates");
        } else if (id == R.id.btn_crossfit) {
            app.setGymClass("CrossFit");
        }

        Intent intent = new Intent(getApplicationContext(), SelectDireccionActivity.class);
        startActivity(intent);
    }
}

