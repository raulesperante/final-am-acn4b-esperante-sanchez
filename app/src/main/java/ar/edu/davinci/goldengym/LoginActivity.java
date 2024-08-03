package ar.edu.davinci.goldengym;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;
import android.view.View;
import android.widget.EditText;
import android.content.Intent;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends  AppCompatActivity {
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        mAuth = FirebaseAuth.getInstance();
    }

     public void login(View view){
         EditText inputMail = findViewById(R.id.email);
         EditText inputPassword = findViewById(R.id.password);
         if(inputMail.length() == 0 || inputPassword.length() == 0){
             Toast.makeText(LoginActivity.this,
                     "Debe completar todos los campos",
                     Toast.LENGTH_SHORT).show();
             return;
         }
         String mail = inputMail.getText().toString();
         String password = inputPassword.getText().toString();
         signIn(mail, password);
     }

    private void goToStartActivity(){
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }

    public void register(View view){
        Intent intent = new Intent(getApplicationContext(), RegistroActivity.class);
        startActivity(intent);
    }

    private void signIn(String email, String password) {
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goToStartActivity();
                        } else {
                            Toast.makeText(LoginActivity.this,
                                    "Email o contraseña incorrecta",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}

