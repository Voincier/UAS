package id.ac.umn.uas.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import id.ac.umn.uas.R;
import id.ac.umn.uas.ui.home.Karyawan;

public class NotificationsFragment extends Fragment {
    FirebaseAuth auth;
    DatabaseReference ref;
    String nama, email, foto;
    TextView name, job, bidang,tGaji, tKelas, tEmail, tInfo;
    EditText eName;
    ImageView imView;
    Button editProfileBtn;
    private NotificationsViewModel notificationsViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                ViewModelProviders.of(this).get(NotificationsViewModel.class);
        View root = inflater.inflate(R.layout.fragment_notifications, container, false);

        auth= FirebaseAuth.getInstance();
        name = root.findViewById(R.id.name);
        job = root.findViewById(R.id.job);
        bidang = root.findViewById(R.id.bidang);
        tGaji = root.findViewById(R.id.gaji_text);
        tKelas = root.findViewById(R.id.class_text);
        tEmail = root.findViewById(R.id.email_text);
        tInfo = root.findViewById(R.id.no_text);

        eName = root.findViewById(R.id.name_edit);
        eName.setVisibility(View.GONE);

        imView = root.findViewById(R.id.imgViewing);

        editProfileBtn = root.findViewById(R.id.editProfileBtn);

        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        getUserData();
        return root;
    }

    private void getUserData(){
        FirebaseUser fbUser=auth.getCurrentUser();
        String userID=fbUser.getUid();
        ref= FirebaseDatabase.getInstance().getReference("user").child(userID);
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
