package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class registration extends AppCompatActivity {
    EditText name_input, sign_password, email_input, phone_input;
    Button signup, button_login;
    boolean valid= true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;

    TinyDB tinyDB;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);


        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        tinyDB = new TinyDB(getApplicationContext());


        name_input = findViewById(R.id.name_input);
        sign_password = findViewById(R.id.sign_password);
        email_input = findViewById(R.id.email_input);

        phone_input = findViewById(R.id.phone_input);
        signup = findViewById(R.id.signup);
        button_login = findViewById(R.id.button_login);

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), UserLogin.class));
            }
        });


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(name_input);
                checkField(email_input);
                checkField(sign_password);
                checkField(phone_input);

                if(valid){
                    //starting registration on conditions here
                    fAuth.createUserWithEmailAndPassword(email_input.getText().toString(),sign_password.getText().toString()).addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                        @Override
                        public void onSuccess(AuthResult authResult) {
                            FirebaseUser user = fAuth.getCurrentUser();
                            Toast.makeText(registration.this, "User created", Toast.LENGTH_SHORT).show();
                            DocumentReference df = fStore.collection("users").document(user.getUid());
                            Map<String,Object> userInfo = new HashMap<>();
                            userInfo.put("Name",name_input.getText().toString());
                            userInfo.put("Email",email_input.getText().toString());
                            userInfo.put("Phone", phone_input.getText().toString());

                            df.set(userInfo);

                            String name = name_input.getText().toString();

//                            tinyDB.putString("UName", name );


                            startActivity(new Intent(getApplicationContext(),UserLogin.class));
                            finish();

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(registration.this, "Try again with correct credentials", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });


    }

    public boolean checkField(EditText textField) {
      if (textField.getText().toString().isEmpty()){
          textField.setError("Error");
          valid = false;
      }
      else {
          valid = true;

      }
      return valid;
    }
}