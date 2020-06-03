package id.ac.umn.uas;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegisterActivity extends AppCompatActivity {
    Button btnRegis;
    EditText eName, eEmail, ePassword, eConfirm;
    ProgressDialog loading;
    DatabaseReference ref;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        auth=FirebaseAuth.getInstance();

        eName = findViewById(R.id.name_reg);
        eEmail = findViewById(R.id.email_reg);
        ePassword = findViewById(R.id.pw_reg);
        eConfirm = findViewById(R.id.pw2_reg);

        btnRegis = findViewById(R.id.regisBtn);
        loading = new ProgressDialog(this);

        btnRegis.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = eName.getText().toString();
                String email = eEmail.getText().toString();
                String pw = ePassword.getText().toString();
                String confirm = eConfirm.getText().toString();

                if(TextUtils.isEmpty(name) || TextUtils.isEmpty(email) || TextUtils.isEmpty(pw)){
                    Toast.makeText(RegisterActivity.this, "All fields are required",Toast.LENGTH_SHORT).show();
                }
                else if(!pw.equals(confirm)){
                    Toast.makeText(RegisterActivity.this,"Password does not", Toast.LENGTH_SHORT).show();
                }
                else{
                    loading.setMessage("Create your profile");
                    loading.show();
                    register(name, email, pw);
                }
            }
        });
    }

    private void register(final String name, final String email, final String password){
        auth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            FirebaseUser fbUser=auth.getCurrentUser();
                            String userID=fbUser.getUid();
                            ref= FirebaseDatabase.getInstance().getReference("user").child(userID);

                            HashMap<String, String> hashMap=new HashMap<>();
                            hashMap.put("id",userID);
                            hashMap.put("name",name);
                            hashMap.put("email",email);
                            hashMap.put("password",password);

                            ref.setValue(hashMap, new DatabaseReference.CompletionListener() {
                                @Override
                                public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                                    loading.dismiss();
                                    Toast.makeText(RegisterActivity.this, "Register Successful", Toast.LENGTH_SHORT).show();
                                    startActivity(new Intent(RegisterActivity.this,MainActivity.class));
                                }
                            });

                        }
                        else{
                            loading.hide();
                            Toast.makeText(RegisterActivity.this,"Register Failed",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

    }


}
