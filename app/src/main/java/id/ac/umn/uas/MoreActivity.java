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

public class MoreActivity extends AppCompatActivity {
    Button finishBtn;
    EditText eSubject, eClass, eSalary, eBio;
    Spinner jobSpinner;
    ProgressDialog loading;
    FirebaseAuth auth;
    DatabaseReference ref;
    String nama, email, foto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more);

        finishBtn = findViewById(R.id.finishBtn);

        eSubject = findViewById(R.id.sub_reg);
        eClass = findViewById(R.id.class_reg);
        eBio = findViewById(R.id.bio_reg);
        eSalary = findViewById(R.id.salary_reg);

        jobSpinner = findViewById(R.id.spinner_reg);

        loading = new ProgressDialog(this);

        auth= FirebaseAuth.getInstance();

        finishBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                String sub, cla, bio,sal;
                String job=String.valueOf(jobSpinner.getSelectedItem());

                sal = eSalary.getText().toString();
                sub = eSubject.getText().toString();
                bio = eBio.getText().toString();
                cla = eClass.getText().toString();

                if(TextUtils.isEmpty(sub) || TextUtils.isEmpty(bio) || TextUtils.isEmpty(cla)) {
                    Toast.makeText(MoreActivity.this, "All fields are required", Toast.LENGTH_SHORT).show();
                }
                else{
                    loading.setMessage("Create your profile");
                    loading.show();
                    register(sal,sub,bio,cla,job);
                }
            }


        });
    }

    private void register(String salary,String subject, String bio, String clas, String job){

        FirebaseUser fbUser=auth.getCurrentUser();
        String userID=fbUser.getUid();
        ref= FirebaseDatabase.getInstance().getReference("user").child(userID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Karyawan kwn = dataSnapshot.getValue(Karyawan.class);
                nama = kwn.getNama();
                email = kwn.getEmail();
                foto = kwn.getFoto();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        HashMap<String, String> hashMap=new HashMap<>();
//        hashMap.put("id",userID);
//        hashMap.put("name", nama);
//        hashMap.put("email",email);
//        hashMap.put("foto",foto);
        hashMap.put("gaji",salary);
        hashMap.put("matkul",subject);
        hashMap.put("bio",bio);
        hashMap.put("kelas",clas);
        hashMap.put("job",job);

        ref.setValue(hashMap, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(@Nullable DatabaseError databaseError, @NonNull DatabaseReference databaseReference) {
                loading.dismiss();
                Toast.makeText(MoreActivity.this, "Added Additional Details", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MoreActivity.this,OptionActivity.class));
            }
        });
    }
}
