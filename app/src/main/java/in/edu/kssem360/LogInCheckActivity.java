package in.edu.kssem360;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LogInCheckActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_check);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null) {

            String name = user.getDisplayName();
            startActivity(new Intent(LogInCheckActivity.this, MainActivity.class));
            finish();
            Toast.makeText(LogInCheckActivity.this, "welCome " + name, Toast.LENGTH_SHORT).show();
        } else {
            startActivity(new Intent(LogInCheckActivity.this, LogIn.class));
            finish();
            Toast.makeText(LogInCheckActivity.this, "No user logged In", Toast.LENGTH_SHORT).show();
        }
    }
}
