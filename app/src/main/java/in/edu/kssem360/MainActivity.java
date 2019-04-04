package in.edu.kssem360;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import java.util.HashMap;
import java.util.Map;
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
    private FirebaseDatabase mDatabase;
    private DatabaseReference mReference;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        //fire_base
        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        mDatabase = FirebaseDatabase.getInstance();

        String uid = user.getUid();
        String name = user.getDisplayName();
        Uri image = user.getPhotoUrl();

        mReference = mDatabase.getReference().child("users").child(uid);

        Map maps = new HashMap();
        maps.put("name", name);
        maps.put("image", image.toString());


        mReference.updateChildren(maps).addOnCompleteListener(new OnCompleteListener() {
            @Override
            public void onComplete(@NonNull Task task) {
                if (task.isSuccessful()) {
                    Toast.makeText(MainActivity.this, "User Data Updated", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(MainActivity.this, "Error while updating user data", Toast.LENGTH_LONG).show();
                }
            }
        });

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.toolbar_logout) {
            mAuth.signOut();
            startActivity(new Intent(MainActivity.this, LogIn.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void handleFragments(Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.main_frame_layout, fragment);
        fragmentTransaction.commit();
    }

}
