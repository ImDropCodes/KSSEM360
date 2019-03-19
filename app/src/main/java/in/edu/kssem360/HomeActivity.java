package in.edu.kssem360;

import androidx.appcompat.app.AppCompatActivity;
import in.edu.kssem360.Admin.AdminEventUpdate;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    private FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        floatingActionButton = findViewById(R.id.home_fab);

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this, AdminEventUpdate.class));
            }
        });

    }
}
