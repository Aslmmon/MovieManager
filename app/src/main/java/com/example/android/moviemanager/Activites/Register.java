package com.example.android.moviemanager.Activites;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.android.moviemanager.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Register extends AppCompatActivity implements View.OnClickListener {
    private static final int GET_IMAGE_REQUEST = 1;
    CircleImageView profileImage;
    TextInputEditText email2, password2, confirmPassword2;
    Button signUp;
    ProgressBar progressBar;
    Uri image;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();
        initializeViews();
    }

    private void initializeViews() {
        profileImage = findViewById(R.id.imageProfile);
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                chooseImage();
            }
        });
        email2 = findViewById(R.id.Email2);
        password2 = findViewById(R.id.password2);
        confirmPassword2 = findViewById(R.id.cofirmPassword);
        progressBar = findViewById(R.id.progress);
        signUp = findViewById(R.id.signUp2);
        signUp.setOnClickListener(this);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GET_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
            image = data.getData();
            try {
                Bitmap bitmapImage = MediaStore.Images.Media.getBitmap(getContentResolver(), image);
                profileImage.setImageBitmap(bitmapImage);
                uploadToFirbase();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadToFirbase() {
        StorageReference storageReference = FirebaseStorage.getInstance()
                .getReference("profilePic/" + System.currentTimeMillis() + ".jpg");

        if (image != null) {
            storageReference.putFile(image).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    image = taskSnapshot.getDownloadUrl();
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {

                }

            });
        }
    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent.createChooser(intent, "select image")
                , GET_IMAGE_REQUEST);
    }

    @Override
    public void onClick(View view) {
        String emailRegisterd = email2.getText().toString().trim();
        String passwordRegisterd = password2.getText().toString().trim();
        String confirmPassword = confirmPassword2.getText().toString().trim();
        if (emailRegisterd.isEmpty()) {
            email2.setError("Enter Your Email Please");
            email2.requestFocus();
            return;
        }
        if (!Patterns.EMAIL_ADDRESS.matcher(emailRegisterd).matches()) {
            email2.setError("Enter Valid Email Please");
            email2.requestFocus();
            return;
        }
        if (passwordRegisterd.isEmpty()) {
            password2.setError("Enter Your Password Please");
            password2.requestFocus();
            return;
        }
        if (passwordRegisterd.length() < 6) {
            password2.setError("Minimum Password should not be less than 6");
            password2.requestFocus();
            return;
        }
        if (confirmPassword.isEmpty()) {
            confirmPassword2.setError("Confirm Please");
            confirmPassword2.requestFocus();
            return;
        }
        if (!passwordRegisterd.equals(confirmPassword)){
            confirmPassword2.setError("Not Matched Password");
            confirmPassword2.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        if (passwordRegisterd.equals(confirmPassword)) {
            mAuth.createUserWithEmailAndPassword(emailRegisterd, passwordRegisterd).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()) {
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(), "Registerd Successfully",
                                Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else if (task.getException() instanceof FirebaseAuthUserCollisionException){
                        progressBar.setVisibility(View.GONE);
                        Toast.makeText(getApplicationContext(),"Already user Registerd with same Email",Toast.LENGTH_SHORT)
                                .show();
                    }
                }
            });

        }

    }
}
