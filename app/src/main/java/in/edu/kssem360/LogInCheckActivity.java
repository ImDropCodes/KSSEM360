package in.edu.kssem360;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class LogInCheckActivity extends AppCompatActivity {

    private FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in_check);

        firebaseAuth = FirebaseAuth.getInstance();

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if (user != null){

            String uid = user.getUid();
            String name = user.getDisplayName();
            startActivity(new Intent(LogInCheckActivity.this,HomeActivity.class));
            finish();
            Toast.makeText(LogInCheckActivity.this,"WelCome "+name, Toast.LENGTH_SHORT).show();
        }else {
            startActivity(new Intent(LogInCheckActivity.this,LogIn.class));
            finish();
        }
    }
}
