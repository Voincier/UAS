package id.ac.umn.uas;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import id.ac.umn.uas.ui.home.Karyawan;

public class DetailActivity extends AppCompatActivity {
    String id;
    FirebaseAuth auth;
    DatabaseReference ref;
    String nama, email, foto;
    TextView name, job, bidang,tGaji, tKelas, tEmail, tInfo;
    EditText eName;
    ImageView imView;
    Button editProfileBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent i = getIntent();
        id = i.getStringExtra("id");

        auth= FirebaseAuth.getInstance();
        name = findViewById(R.id.name);
        job = findViewById(R.id.job);
        bidang = findViewById(R.id.bidang);
        tGaji = findViewById(R.id.gaji_text);
        tKelas = findViewById(R.id.class_text);
        tEmail = findViewById(R.id.email_text);
        tInfo = findViewById(R.id.no_text);

        eName = findViewById(R.id.name_edit);
        eName.setVisibility(View.GONE);

        imView = findViewById(R.id.imgViewing);

        editProfileBtn = findViewById(R.id.editProfileBtn);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getUserData();
    }

    private void getUserData(){
        ref= FirebaseDatabase.getInstance().getReference("user").child(id);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Karyawan kwn = dataSnapshot.getValue(Karyawan.class);
                name.setText(kwn.getName());
                job.setText(kwn.getJob());
                bidang.setText(kwn.getMatkul());
                tGaji.setText("Rp. "+kwn.getGaji()+"/bulan");
                tEmail.setText(kwn.getEmail());
                tInfo.setText(kwn.getInfo());
                tKelas.setText(kwn.getKelas());

                if(kwn.getFoto().equals("default")){
                    imView.setImageResource(R.mipmap.ic_launcher);
                }else{
                    Picasso.get().load(String.valueOf(kwn.getFoto())).into(imView);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
