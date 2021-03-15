package com.example.nishaa;



import android.content.Intent;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


import com.example.nishaa.Common.Common;
import com.example.nishaa.Model.User;

import io.paperdb.Paper;

public class SignIn extends AppCompatActivity {
    EditText edtPhone, edtPassword;
    Button btnSignIn;

    ImageButton showHideBtn;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtPassword = findViewById(R.id.editPassword);
        edtPhone = findViewById(R.id.editPhoneNumber);
        btnSignIn = findViewById(R.id.btnSignIn);

        showHideBtn = findViewById(R.id.showHideBtn);

        showHideBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (flag == true) {
                    flag = false;
                    edtPassword.setTransformationMethod(null);
                    if (edtPassword.getText().length() > 0)
                        edtPassword.setSelection(edtPassword.getText().length());

                } else {
                    flag = true;
                    edtPassword.setTransformationMethod(new PasswordTransformationMethod());
                    if (edtPassword.getText().length() > 0)
                        edtPassword.setSelection(edtPassword.getText().length());

                }
            }
        });



        Paper.init(this);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        final DatabaseReference table_user = database.getReference("User");


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Common.isConnectedToInternet(getBaseContext())) {


                        Paper.book().write(Common.USER_KEY, edtPhone.getText().toString());
                        Paper.book().write(Common.PWD_KEY, edtPassword.getText().toString());





                    table_user.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {

                            if (dataSnapshot.child(edtPhone.getText().toString()).exists()) {

                                Toast.makeText(SignIn.this, " reached database", Toast.LENGTH_SHORT).show();

                                User user = dataSnapshot.child(edtPhone.getText().toString()).getValue(User.class);
                                user.setPhone(edtPhone.getText().toString());
                                if (user.getPassword().equals(edtPassword.getText().toString())) {
                                    Toast.makeText(SignIn.this, "creating intent", Toast.LENGTH_LONG).show();
                                    Intent homeIntent = new Intent(SignIn.this, Home.class);

                                    startActivity(homeIntent);
                                    finish();

                                } else {
                                    Toast.makeText(SignIn.this, "Wrong Password ", Toast.LENGTH_SHORT).show();
                                }
                            } else {

                                Toast.makeText(SignIn.this, "User not exists", Toast.LENGTH_SHORT).show();

                            }

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                } else {
                    Toast.makeText(SignIn.this, "Please Check Internet Connection", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });

    }
}
