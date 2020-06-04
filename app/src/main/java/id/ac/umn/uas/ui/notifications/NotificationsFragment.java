package id.ac.umn.uas.ui.notifications;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
    TextView name, job, bidang;
    ImageView imView;
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

        imView = root.findViewById(R.id.imgViewing);

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
