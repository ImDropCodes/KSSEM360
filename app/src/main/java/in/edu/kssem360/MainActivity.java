package in.edu.kssem360;

import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import in.edu.kssem360.Fragments.AboutFragment;
import in.edu.kssem360.Fragments.FeedFragment;
import in.edu.kssem360.Fragments.FestFragment;

public class MainActivity extends AppCompatActivity {

    private FirebaseUser user;
    private FirebaseAuth mAuth;
    private BottomNavigationView navigationView;
    private FeedFragment mFeedFragment;
    private FestFragment mFestFragment;
    private AboutFragment mAboutFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //fire_base
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        
        mFeedFragment = new FeedFragment();
        mFestFragment = new FestFragment();
        mAboutFragment = new AboutFragment();

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
                    case R.id.nav_about:
                        handleFragments(mAboutFragment);
                        break;
                    default:
                        handleFragments(mFeedFragment);
                        break;
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
