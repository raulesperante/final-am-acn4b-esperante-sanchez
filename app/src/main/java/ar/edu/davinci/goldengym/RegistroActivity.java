package ar.edu.davinci.goldengym;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import android.util.Patterns;


public class RegistroActivity extends  AppCompatActivity {
    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        mAuth = FirebaseAuth.getInstance();

    }

    public void register(View view){
        EditText inputPassword = findViewById(R.id.password);
        EditText inputEmail = findViewById(R.id.email);

        EditText[] inputs = {
                inputEmail,
                inputPassword,
                findViewById(R.id.name),
                findViewById(R.id.surname)
        };

        for (EditText input : inputs) {
            if (input.getText().toString().trim().isEmpty()) {
                Toast.makeText(RegistroActivity.this,
                        "Debe completar todos los campos",
                        Toast.LENGTH_SHORT).show();
                return;
            }
        }

        String password = inputPassword.getText().toString();
        if (password.length() < 8){
            Toast.makeText(RegistroActivity.this,
                    "La contraseña debe tener 8 caracteres como mínimo",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        String email = inputEmail.getText().toString();
        if (!isValidEmail(email)){
            Toast.makeText(RegistroActivity.this,
                    "El email no es válido",
                    Toast.LENGTH_SHORT).show();
            return;
        }

        createAccount(email, password);
    }

    private void goToRegisterSuccess(){
        Intent intent = new Intent(getApplicationContext(), RegisterSuccessActivity.class);
        startActivity(intent);
    }

    private void createAccount(String email, String password) {

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            goToRegisterSuccess();
                        } else {
                            if(task.getException() instanceof FirebaseAuthUserCollisionException) {
                                Toast.makeText(RegistroActivity.this,
                                        "Ya hay un usuario registrado con ese email",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });

    }

    private boolean isValidEmail(CharSequence email) {
        return email != null && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

}

