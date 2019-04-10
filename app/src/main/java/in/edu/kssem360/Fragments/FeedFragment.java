package in.edu.kssem360.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import in.edu.kssem360.Adapter.FeedAdapter;
import in.edu.kssem360.Admin.AdminFeedUpdate;
import in.edu.kssem360.Model.FeedModelClass;
import in.edu.kssem360.R;

public class FeedFragment extends Fragment {

    private RecyclerView recyclerView;
    private FeedAdapter adapter;
    private List<FeedModelClass> modelClasses;
    private DatabaseReference mReference;
    private FloatingActionButton fab;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseDatabase mDatabase;
    private String uid;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, null);

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        recyclerView = view.findViewById(R.id.recycler_view_feed);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        modelClasses = new ArrayList<>();

        fab = view.findViewById(R.id.fab_feed);
        fab.hide();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getContext(), AdminFeedUpdate.class));
            }
        });

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();

        mReference = mDatabase.getReference();
        mReference.keepSynced(true);
        mReference.child("users").child(uid).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("status")) {
                    fab.show();
                } else {
                    fab.hide();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        mReference.child("feed").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if (dataSnapshot != null) {

                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        FeedModelClass model = snapshot.getValue(FeedModelClass.class);
                        modelClasses.add(model);
                    }
                    adapter = new FeedAdapter(modelClasses,getContext());
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);

                } else {
                    Toast.makeText(getContext(), "No feed Found", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                Toast.makeText(getContext(), databaseError.toString(), Toast.LENGTH_SHORT).show();
            }
        });

    }
}
