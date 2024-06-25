package ar.edu.davinci.goldengym;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterSuccessActivity extends  AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_success);
    }


    public void toStartActivity(View view){
        Intent intent = new Intent(getApplicationContext(), StartActivity.class);
        startActivity(intent);
    }

}

