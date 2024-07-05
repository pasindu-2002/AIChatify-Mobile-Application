package com.example.chatgpt;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.Firebase;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUpActivity extends AppCompatActivity {

    TextView txtSignIn;

    EditText edtFullName, edtEmail, edtTele, edtPassword, edtConPassword;

    ProgressBar progressBar;

    Button btnSignUp;

    String strFullName, strEmail, strTele, strPassword, strConPassword;

    String emailPatten = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    FirebaseAuth mAuth;

    FirebaseFirestore db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        txtSignIn = findViewById(R.id.txtSignIn);
        edtFullName = findViewById(R.id.edtSignUpFullName);
        edtEmail = findViewById(R.id.edtSignUpEmail);
        edtTele = findViewById(R.id.edtSignUpMobile);
        edtPassword = findViewById(R.id.edtSignUpPassword);
        edtConPassword = findViewById(R.id.edtSignUpConfirmPassword);
        progressBar = findViewById(R.id.signUpProgressBar);
        btnSignUp = findViewById(R.id.btnSignUp);



        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SignUpActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strFullName = edtFullName.getText().toString();
                strEmail= edtEmail.getText().toString();
                strTele = edtTele.getText().toString();
                strPassword = edtPassword.getText().toString();
                strConPassword = edtConPassword.getText().toString();

                if(isValidate()){
                    Signup();
                }
            }

        });

    }

    private boolean isValidate() {

        if(TextUtils.isEmpty(strFullName)){
            edtFullName.setError("Full Name can't be Empty !");
            return false;
        }

        if(TextUtils.isEmpty(strEmail)){
            edtEmail.setError("Email can't be Empty !");
            return false;
        }

        if(!strEmail.matches(emailPatten)){
            edtEmail.setError("Enter Valid Email");
            return false;
        }

        if(TextUtils.isEmpty(strTele)){
            edtTele.setError("Telephone number can't be Empty !");
            return false;
        }

        if(TextUtils.isEmpty(strPassword)){
            edtPassword.setError("Password can't be Empty !");
            return false;
        }

        if(!strPassword.equals(strConPassword)){
            edtConPassword.setError("Confirm Password and Password should be same!");
            return false;
        }

        return true;
    }

    private void Signup() {
        btnSignUp.setVisibility(View.INVISIBLE);
        progressBar.setVisibility(View.VISIBLE);

        mAuth.createUserWithEmailAndPassword(strEmail,strPassword).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
            @Override
            public void onSuccess(AuthResult authResult) {
                Map<String, Object> user = new HashMap<>();
                user.put("FullName", strFullName);
                user.put("Email", strEmail);
                user.put("Mobile", strTele);

                db.collection("Users")
                        .document(strEmail)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Intent intent = new Intent(SignUpActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUpActivity.this,"Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                                btnSignUp.setVisibility(View.VISIBLE);
                                progressBar.setVisibility(View.INVISIBLE);
                            }
                        });
            }

        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(SignUpActivity.this,"Error - " + e.getMessage(), Toast.LENGTH_SHORT).show();
                btnSignUp.setVisibility(View.VISIBLE);
                progressBar.setVisibility(View.INVISIBLE);
            }
        });
    }
}