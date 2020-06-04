package id.ac.umn.uas.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Picasso;

import id.ac.umn.uas.DetailActivity;
import id.ac.umn.uas.R;

public class HomeFragment extends Fragment {
    private RecyclerView recyclerView;
    private HomeViewModel homeViewModel;
    private FirebaseRecyclerAdapter adapter;
    Karyawan kwn;

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = view.findViewById(R.id.list_karyawan_recycler);

//        Ascending
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

//        LinearLayoutManager layoutManager = new LinearLayoutManager(context);
//        layoutManager.setReverseLayout(true);
//        layoutManager.setStackFromEnd(true);
//        recyclerView.setLayoutManager(layoutManager);


        readUsers();
        // Inflate the layout for this fragment
        return view;
    }

    private void readUsers(){
        adapter = null;
        Query query = FirebaseDatabase.getInstance()
                .getReference()
                .child("user");

        FirebaseRecyclerOptions<Karyawan> options =
                new FirebaseRecyclerOptions.Builder<Karyawan>()
                        .setQuery(query, new SnapshotParser<Karyawan>() {
                            @NonNull
                            @Override
                            public Karyawan parseSnapshot(@NonNull DataSnapshot snapshot) {
                                return new Karyawan(snapshot.child("job").getValue().toString(),
                                        snapshot.child("name").getValue().toString(),
                                        snapshot.child("foto").getValue().toString(),
                                        snapshot.child("id").getValue().toString());
                            }
                        })
                        .build();

        adapter = new FirebaseRecyclerAdapter<Karyawan, ViewHolder>(options) {
            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext())
                        .inflate(R.layout.list_karyawan, parent, false);
                return new ViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull ViewHolder holder, final int position, @NonNull final Karyawan model) {
                holder.setNama(model.getName());
                holder.setJabatan(model.getJob());
                holder.setImg(model.getFoto());
                holder.root.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // Toast.makeText(getContext(), String.valueOf(model.getId()), Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(getActivity(), DetailActivity.class);
                        i.putExtra("id",String.valueOf(model.getId()));
                        startActivity(i);
                    }
                });
            }

        };


        recyclerView.setAdapter(adapter);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public LinearLayout root;
        public TextView jbtn,nama;
        public ImageView img;

        public ViewHolder(View itemView){
            super(itemView);
            root = itemView.findViewById(R.id.karyawan_layout);
            img = itemView.findViewById(R.id.list_img);
            jbtn = itemView.findViewById(R.id.list_job);
            nama = itemView.findViewById(R.id.list_nama);

        }

        public void setJabatan(String string) {
            jbtn.setText(string);
        }

        public void setNama(String string) {
            nama.setText(string);
        }

        public void setImg(String string){
            if(string.equals("default")){
                img.setImageResource(R.mipmap.ic_launcher);
            } else{
                Picasso.get().load(string).into(img);
            }
        }

    }
}
