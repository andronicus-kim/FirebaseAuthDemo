package ke.co.dataintegrated.firebaseauthentication;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    private FirebaseAuth mAuth;

    private ProgressDialog mProgressDialog;
    private EditText mUserEmail;
    private EditText mUserPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button login = (Button) findViewById(R.id.btn_login);
        mUserEmail = (EditText) findViewById(R.id.et_email);
        mUserPassword = (EditText) findViewById(R.id.et_password);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mProgressDialog = new ProgressDialog(MainActivity.this);
                mProgressDialog.setCancelable(true);
                mProgressDialog.setMessage("Signing In...");
                mProgressDialog.show();

                startSignIn();
            }
        });

    }
    private void startSignIn(){
        String email = mUserEmail.getText().toString().trim();
        String password = mUserPassword.getText().toString().trim();

        if (!TextUtils.isEmpty(email) || !TextUtils.isEmpty(password)){
            mProgressDialog.dismiss();
            Toast.makeText(this, "Some fields are Empty!", Toast.LENGTH_SHORT).show();
        }else {
            mAuth.signInWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if (!task.isSuccessful()){
                        if (task.getException().getMessage() !=null){
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(MainActivity.this, "Login failed try again", Toast.LENGTH_SHORT).show();
                        }

                    }else {
                        mUserEmail.setText("");
                        mUserPassword.setText("");
                        mProgressDialog.dismiss();
                        Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                        startActivity(intent);
                        finish();
                    }
                }
            });
        }
    }

}
