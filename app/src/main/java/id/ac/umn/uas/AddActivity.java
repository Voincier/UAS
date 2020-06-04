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
import android.widget.Spinner;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

import id.ac.umn.uas.ui.home.Karyawan;

public class AddActivity extends AppCompatActivity {
    Button addBtn;
    EditText eEmail, eName, eBio, eMatkul, eInfo, eClass, eSalary;
    Spinner jobSpinner;
    ProgressDialog loading;
    FirebaseAuth auth;
    DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        addBtn = findViewById(R.id.addBtn);

        eEmail = findViewById(R.id.email_add);
        eName = findViewById(R.id.name_add);
        eBio = findViewById(R.id.bio_add);
        eMatkul = findViewById(R.id.sub_add);
        eInfo = findViewById(R.id.no_add);
        eClass = findViewById(R.id.class_add);
        eSalary = findViewById(R.id.salary_add);

        jobSpinner = findViewById(R.id.spinner_add);

        loading = new ProgressDialog(this);

        auth= FirebaseAuth.getInstance();

        addBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String sub, cla, bio,sal,num,email,name;
                String job=String.valueOf(jobSpinner.getSelectedItem());

                sal = eSalary.getText().toString();
                sub = eMatkul.getText().toString();
                bio = eBio.getText().toString();
                cla = eClass.getText().toString();
                num = eInfo.getText().toString();
                email = eEmail.getText().toString();
                name = eName.getText().toString();

                if(TextUtils.isEmpty(sub) || TextUtils.isEmpty(bio) || TextUtils.isEmpty(cla) || TextUtils.isEmpty(email) || TextUtils.isEmpty(name)) {
                    Toast.makeText(AddActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    loading.setMessage("Adding to database");
                    loading.show();
                    add(sal,sub,bio,cla,job,num,email,name);
                }
            }
        });

    }

    private void add(String gaji, String matkul, String bio, String kelas, String job, String info, String email, String name){
        String uniqueId = String.valueOf(System.currentTimeMillis());
        final HashMap<String, Object> hashMap=new HashMap<>();
        ref= FirebaseDatabase.getInstance().getReference("user").child(uniqueId);

        hashMap.put("id",uniqueId);
        hashMap.put("name", name);
        hashMap.put("email",email);
        hashMap.put("foto","default");
        hashMap.put("gaji",gaji);
        hashMap.put("matkul",matkul);
        hashMap.put("bio",bio);
        hashMap.put("kelas",kelas);
        hashMap.put("job",job);
        hashMap.put("info",info);

        ref.setValue(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                loading.dismiss();
                Toast.makeText(AddActivity.this, "Added", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(AddActivity.this,OptionActivity.class));
            }
        });
    }
}
