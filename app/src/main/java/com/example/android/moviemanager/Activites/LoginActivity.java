package com.example.android.moviemanager.Activites;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.moviemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthEmailException;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    EditText email, password;
    Button signIn;
    TextView signUp;
    FirebaseAuth auth;
    SharedPreferences sp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initializeViews();
    }



    private void initializeViews() {
        email = findViewById(R.id.Email);
        password = findViewById(R.id.password);
        auth = FirebaseAuth.getInstance();
        signIn = findViewById(R.id.signIn);
        signIn.setOnClickListener(this);
        signUp = findViewById(R.id.signUp);
        signUp.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.signIn:
                goToMainActivity();
                break;

            case R.id.signUp:
                goToRegisteration();
                break;
        }
    }

    private void goToRegisteration() {
        startActivity(new Intent(this, Register.class));
    }

    private void goToMainActivity() {
        String emailUser = email.getText().toString().trim();
        String passUser = password.getText().toString().trim();

        if (emailUser.isEmpty()) {
            email.setError("Enter Your Email please");
            email.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailUser).matches()) {
            email.setError("Enter Valid email please");
            email.requestFocus();
            return;
        }
        if (passUser.isEmpty()) {
            password.setError("Enter Your Password please");
            password.requestFocus();
            return;
        }
        if (passUser.length() < 6) {
            password.setError("enter valid password");
            password.requestFocus();
            return;
        }

        auth.signInWithEmailAndPassword(emailUser, passUser).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    if (task.getException() instanceof FirebaseAuthEmailException){
                        Toast.makeText(getApplicationContext(),"Check your email again",Toast.LENGTH_SHORT)
                                .show();
                    }
                    else {Toast.makeText(getApplicationContext(),task.getException().getMessage(),Toast.LENGTH_SHORT)
                            .show();
                    }
                }
            }
        });
    }
}
