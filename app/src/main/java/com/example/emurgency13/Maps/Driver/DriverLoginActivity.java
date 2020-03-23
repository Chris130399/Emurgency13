package com.example.emurgency13.Maps.Driver;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionManager;
import android.util.Patterns;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.example.emurgency13.Maps.MainActivity;
import com.example.emurgency13.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DriverLoginActivity extends AppCompatActivity {

    private EditText mEmail, mPassword;
    private Button mLogin, mRegistration;
    private TextView forgot_password, txt;
    private ImageView back_button;

    ViewGroup login_layout;

    AwesomeValidation awesomeValidation;

    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_customer_login);

        mAuth = FirebaseAuth.getInstance();

        firebaseAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                if(user!=null){
                    Intent intent = new Intent(DriverLoginActivity.this, DriverMapActivity.class);
                    startActivity(intent);
                    finish();
                }
            }
        };

        //Note
        login_layout = (ViewGroup) findViewById(R.id.login_layer);
        txt = (TextView) findViewById(R.id.note_password);

        mEmail = (EditText) findViewById(R.id.email);
        mPassword = (EditText) findViewById(R.id.password);

        //Text Watcher
        mEmail.addTextChangedListener(loginTextWatcher);
        mPassword.addTextChangedListener(loginTextWatcher);

        mLogin = (Button) findViewById(R.id.login);
        mRegistration = (Button) findViewById(R.id.registration);

        forgot_password = (TextView) findViewById(R.id.forgot_password);

        //Initialize Validation Style
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);

        //For Email
        awesomeValidation.addValidation(this,R.id.email
                , Patterns.EMAIL_ADDRESS, R.string.invalid_email);

        //For Password
        awesomeValidation.addValidation(this, R.id.password
                , ".{6}", R.string.invalid_password);

        //Back Arrow (Login)
        back_button = (ImageView) findViewById(R.id.back_button);

        back_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DriverLoginActivity.this, MainActivity.class );
                startActivity(intent);
                finish();
            }
        });

        //REGISTER
        mRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();

                //VALIDATION
                if (awesomeValidation.validate()){
                    //On Success
                    Toast.makeText(getApplicationContext()
                            ,"Registration Successful", Toast.LENGTH_SHORT).show();
                }else {

                    //On Failure
                    Toast.makeText(getApplicationContext()
                            ,"Registration Error", Toast.LENGTH_SHORT).show();

                }

                //EMAIL & PASSWORD
                mAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(DriverLoginActivity.this, "Registered?", Toast.LENGTH_SHORT).show();
                        }else {
                            String user_id = mAuth.getCurrentUser().getUid();
                            DatabaseReference current_user_db = FirebaseDatabase.getInstance().getReference().child("Users").child("Drivers").child(user_id).child("name");
                            current_user_db.setValue(true);
                        }
                    }
                });
            }
        });

        //LOGIN
        mLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = mEmail.getText().toString();
                final String password = mPassword.getText().toString();


                //Validation
                if (awesomeValidation.validate()){
                    //On Success
                    Toast.makeText(getApplicationContext()
                            ,"Login Successful", Toast.LENGTH_SHORT).show();
                }else {

                    //On Failure
                    Toast.makeText(getApplicationContext()
                            ,"Login Error", Toast.LENGTH_SHORT).show();

                }
                mAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener(DriverLoginActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(!task.isSuccessful()){
                            Toast.makeText(DriverLoginActivity.this, "Registered?", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });

        //Forget Password
        forgot_password.setOnClickListener(new View.OnClickListener() {

            boolean visible;
            @Override
            public void onClick(View view) {

                //Note
                TransitionManager.beginDelayedTransition(login_layout);
                visible = !visible;
                txt.setVisibility(visible ? View.VISIBLE: View.GONE);

                if (mEmail.getText().toString().isEmpty()){
                    Toast.makeText(DriverLoginActivity.this, "Enter Email Id.", Toast.LENGTH_SHORT).show();
                }
                else {
                    mAuth.sendPasswordResetEmail(mEmail.getText().toString());
                    Toast.makeText(DriverLoginActivity.this, "Password reset link sent to " + mEmail.getText().toString(), Toast.LENGTH_SHORT).show();
                }

            }
        });

    }

    //TEXT WATCHER (ENABLED DISABLED)
    private TextWatcher loginTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            String usernameInput = mEmail.getText().toString().trim();
            String passwordInput = mPassword.getText().toString().trim();

            mLogin.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());
            mRegistration.setEnabled(!usernameInput.isEmpty() && !passwordInput.isEmpty());

        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };


    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(firebaseAuthListener);
    }
    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(firebaseAuthListener);
    }

}