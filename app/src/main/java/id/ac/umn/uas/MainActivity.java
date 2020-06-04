package id.ac.umn.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth auth;
    ProgressDialog loading;
    EditText eEmail, ePassword;
    Button login;
    TextView toRegis;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        auth=FirebaseAuth.getInstance();
        loading = new ProgressDialog(this);

        eEmail = findViewById(R.id.email_log);
        ePassword = findViewById(R.id.pw_log);
        toRegis = findViewById(R.id.toRegis);

        toRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(i);
            }
        });

        login = findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = eEmail.getText().toString();
                String pw = ePassword.getText().toString();

                if(TextUtils.isEmpty(email) || TextUtils.isEmpty(pw)){
                    Toast.makeText(MainActivity.this, "All fields are required",Toast.LENGTH_SHORT).show();
                }
                else{
                    loading.setMessage("Logging In");
                    login(email,pw);
                }
            }
        });
    }

    protected void login(String email,String password){
        auth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            loading.dismiss();
                            Intent i = new Intent(MainActivity.this,OptionActivity.class);
                            startActivity(i);
                            finish();

                        }
                        else{
                            loading.hide();
                            Toast.makeText(MainActivity.this,"Incorrect email or password",Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
