package com.example.project;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.transition.ChangeBounds;
import android.transition.Explode;
import android.transition.Fade;
import android.transition.Slide;
import android.util.Pair;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.SignInButton;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.android.material.transition.platform.MaterialFade;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    TextInputLayout emailId,password;
    Button btnSignIn;
    TextView tvSignUp;
    FirebaseAuth mFirebaseAuth;
    Animation Alpha;

    private FirebaseAuth.AuthStateListener mAuthStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // set an exit transition
        getWindow().setExitTransition(new MaterialFade());
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mFirebaseAuth = FirebaseAuth.getInstance();
        emailId = findViewById(R.id.TextEmailAddress);
        password = findViewById(R.id.TextPassword);
        btnSignIn = findViewById(R.id.buttonLogin);
        tvSignUp = findViewById(R.id.textView);


        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Alpha(btnSignIn);
                String email = emailId.getEditText().getText().toString();
                String pwd = password.getEditText().getText().toString();
                if(email.isEmpty()){
                    emailId.setError("Please enter email id");
                    emailId.requestFocus();
                }
                else if(pwd.isEmpty()){
                    password.setError("Please enter your password");
                    password.requestFocus();
                }
                else if(email.isEmpty() && pwd.isEmpty()){
                    Toast.makeText(LoginActivity.this,"Fields Are Empty!",Toast.LENGTH_SHORT).show();
                }
                else if(!(email.isEmpty() && pwd.isEmpty())){
                    mFirebaseAuth.signInWithEmailAndPassword(email, pwd).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(LoginActivity.this,"Login Error, Please Login Again",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Intent intToHome = new Intent(LoginActivity.this,HomeActivity.class);
                                startActivity(intToHome);
                                finish();
                            }
                        }
                    });
                }
                else{
                    Toast.makeText(LoginActivity.this,"Error Occurred!",Toast.LENGTH_SHORT).show();

                }

            }
        });

        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intSignUp = new Intent(LoginActivity.this, SignupActivity.class);
                startActivity(intSignUp,
                        ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this).toBundle());
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
//        mFirebaseAuth.addAuthStateListener(mAuthStateListener);
    }
    public void Alpha (Button bttn){
        Alpha= new AlphaAnimation(1.0f,0.7f);
        Alpha.setDuration(800);
        Alpha.setRepeatCount(1);
        Alpha.setRepeatMode(Animation.REVERSE);
        bttn.startAnimation(Alpha);
    }
    public void onBackPressed()
    {
        Intent intent = new Intent(this,StartUp.class);
        startActivity(intent);
    }
}