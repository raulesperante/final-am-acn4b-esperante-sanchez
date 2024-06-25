package ar.edu.davinci.goldengym;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

public class LoginActivity extends  AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);




    }

     public void login(View view){
         EditText mail = findViewById(R.id.email);
         EditText password = findViewById(R.id.password);
         if(mail.length() == 0 || password.length() == 0){
             Toast.makeText(LoginActivity.this,
                     "Debe completar todos los campos",
                     Toast.LENGTH_SHORT).show();
             return;
         }
         Intent intent = new Intent(getApplicationContext(), StartActivity.class);
         startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(intent);
    }
}

