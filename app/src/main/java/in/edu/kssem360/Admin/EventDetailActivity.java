package in.edu.kssem360.Admin;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.squareup.picasso.Picasso;

import in.edu.kssem360.R;

public class EventDetailActivity extends AppCompatActivity {

    private String name, image, department, date, venue, type, fee, num_part, teacher_co, student_co, co_ord_number, desc, admin;
    private TextView name_tv, department_tv, date_tv, venue_tv, type_tv, fee_tv, num_part_tv, teacher_co_tv, student_co_tv, co_ord_number_tv, desc_tv, admin_tv;
    private ImageView mImage;
    private FloatingActionButton mFAB;
    private static final int REQUEST_CALL = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_detail);

        Bundle bundle = getIntent().getExtras();

        name = bundle.getString("name");
        image = bundle.getString("image");
        fee = bundle.getString("fee");
        department = bundle.getString("department");
        date = bundle.getString("date");
        venue = bundle.getString("venue");
        type = bundle.getString("type");
        num_part = bundle.getString("num_part");
        teacher_co = bundle.getString("teacher_co");
        student_co = bundle.getString("student_co");
        co_ord_number = bundle.getString("co_ord_number");
        desc = bundle.getString("desc");
        admin = bundle.getString("admin");

        //initialization

        name_tv = findViewById(R.id.event_detail_activity_event_name);
        department_tv = findViewById(R.id.event_detail_activity_event_department);
        date_tv = findViewById(R.id.event_detail_activity_event_date);
        venue_tv = findViewById(R.id.event_detail_activity_event_venue);
        type_tv = findViewById(R.id.event_detail_activity_event_type);
        fee_tv = findViewById(R.id.event_detail_activity_event_fee);
        num_part_tv = findViewById(R.id.event_detail_activity_event_num_participate);
        teacher_co_tv = findViewById(R.id.event_detail_activity_event_teacher);
        student_co_tv = findViewById(R.id.event_detail_activity_event_student);
        co_ord_number_tv = findViewById(R.id.event_detail_activity_event_student_number);
        desc_tv = findViewById(R.id.event_detail_activity_event_description);
        mImage = findViewById(R.id.event_detail_activity_image_view);
        mFAB = findViewById(R.id.fab_event_detail_call);


        name_tv.setText("Event Name: " + name);
        department_tv.setText("Conducted by: " + department);
        date_tv.setText("Date: " + date);
        venue_tv.setText("Venue: " + venue);
        type_tv.setText("Type: " + type);
        fee_tv.setText("Fee: " + fee);
        num_part_tv.setText("Number of Participate: " + num_part);
        teacher_co_tv.setText("Teacher Co-ordinator: " + teacher_co);
        student_co_tv.setText("Student Co-ordinator: " + student_co);
        co_ord_number_tv.setText("Contact-number: " + co_ord_number);
        desc_tv.setText("Description and Rules: \n\n" + desc);

        Picasso.get().load(image).into(mImage);

        mFAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                makePhoneCall();
            }
        });

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == REQUEST_CALL){
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                makePhoneCall();
            }else {
                Toast.makeText(this, "Permission Denied", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void makePhoneCall() {
        if (ActivityCompat.checkSelfPermission(EventDetailActivity.this,
                Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(EventDetailActivity.this,new String[] {Manifest.permission.CALL_PHONE},REQUEST_CALL);

        }else {
            String dial = "tel:"+co_ord_number;
            startActivity(new Intent(Intent.ACTION_CALL,Uri.parse(dial)));
        }
    }
}
