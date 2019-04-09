package in.edu.kssem360.Fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import in.edu.kssem360.R;

public class AboutFragment extends Fragment {

    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    private TextView mUserTV;
    private CircleImageView mUserImage;
    private Button mPrivacyPlicy;

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
        mPrivacyPlicy = view.findViewById(R.id.about_privacy_policy_btn);


        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        String name = mUser.getDisplayName();
        Uri profile_image = mUser.getPhotoUrl();

        mUserTV.setText("Hey, "+name);
        Picasso.get().load(profile_image).placeholder(R.drawable.placeholder).into(mUserImage);

        mPrivacyPlicy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("https://sites.google.com/view/kssem360/home");
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }
        });
    }

}
