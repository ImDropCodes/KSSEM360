package in.edu.kssem360.Fragments;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.edu.kssem360.Adapter.AboutDeveloperAdapter;
import in.edu.kssem360.Model.AboutDeveloperModelClass;
import in.edu.kssem360.R;

public class AboutFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView mUserTV;
    private CircleImageView mUserImage;
    private RecyclerView mRecyclerView;
    private AboutDeveloperAdapter adapter;
    private List<AboutDeveloperModelClass> modelClasses;

    private String names[] = {"Sagar DB (DropCodes)","Revanth Chowdary M","Vishal M","Arvind","Mohammed Yunus","Shashidhara N","Charan TN"};
    private String department[] = {"EEE","CSE","CSE","ECE","ECE","CSE","EEE"};
    private String year[] = {"3rd year","3rd year","4th year","3rd year","4th year","3rd year","3rd year",};

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about, null);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mUserTV = view.findViewById(R.id.about_user_name);
        mUserImage = view.findViewById(R.id.about_user_image);

        mRecyclerView = view.findViewById(R.id.about_recycler_view);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        modelClasses = new ArrayList<>();

        for (int i = 0; i < names.length; i++) {
            AboutDeveloperModelClass settingModel = new AboutDeveloperModelClass(names[i], department[i],year[i]);
            modelClasses.add(settingModel);

            adapter = new AboutDeveloperAdapter(modelClasses, getContext());
            mRecyclerView.setAdapter(adapter);
        }

        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        String name = mUser.getDisplayName();
        Uri profile_image = mUser.getPhotoUrl();

        mUserTV.setText("Hey, "+name);
        Picasso.get().load(profile_image).into(mUserImage);
    }

}
