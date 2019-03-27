package in.edu.kssem360;

import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;
import in.edu.kssem360.Fragments.FeedFragment;
import in.edu.kssem360.Fragments.FestFragment;

public class MainActivity extends AppCompatActivity {

    private String name, uid, email;
    private Uri image_user;
    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private BottomNavigationView navigationView;
    private FeedFragment mFeedFragment;
    private FestFragment mFestFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //firebase
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        uid = user.getUid();
        name = user.getDisplayName();
        image_user = user.getPhotoUrl();
        email = user.getEmail();


        mFeedFragment = new FeedFragment();
        mFestFragment = new FestFragment();

        handleFragments(mFeedFragment);


        navigationView = findViewById(R.id.nav_view);
        navigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                switch (menuItem.getItemId()) {
                    case R.id.nav_feed:
                        handleFragments(mFeedFragment);
                        break;

                    case R.id.nav_fest:
                        handleFragments(mFestFragment);
                        break;
                    default:
                        handleFragments(mFeedFragment);
                }
                return true;
            }
        });
    }

    private void handleFragments(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.commit();
    }

}
