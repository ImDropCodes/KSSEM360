package in.edu.kssem360.Admin;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import in.edu.kssem360.R;

public class AdminEventUpdate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Button mDatePick, mPostEvent;
    private TextView mDateText;
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal;
    private String format = "" ;
    private String date_time;
    private TextInputEditText mEventName, mEventType, mReqFee, mVenue, mNumParticipate, mCordinatName, mCordinatNumber, mDescription;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    private String UID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_event_update);

        mDatePick = findViewById(R.id.date_pick_button);
        mDateText = findViewById(R.id.event_date_Text);
        mEventName = findViewById(R.id.event_name);
        mEventType = findViewById(R.id.event_type);
        mReqFee = findViewById(R.id.event_reg_fee);
        mVenue = findViewById(R.id.event_venue);
        mNumParticipate = findViewById(R.id.event_no_participate);
        mCordinatName = findViewById(R.id.event_co_ord_name);
        mCordinatNumber = findViewById(R.id.event_co_ord_number);
        mDescription = findViewById(R.id.event_description);
        mPostEvent = findViewById(R.id.event_post);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mRef = mDatabase.getReference().child("event").push();
        mUser = mAuth.getCurrentUser();
        UID = mUser.getUid();


        mDatePick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                year = calendar.get(Calendar.YEAR);
                month = calendar.get(Calendar.MONTH);
                day = calendar.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog datePickerDialog = new DatePickerDialog(AdminEventUpdate.this, AdminEventUpdate.this, year, month, day);
                datePickerDialog.show();

            }
        });

        mPostEvent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postEvent();
            }
        });
    }

    private void postEvent() {

        String event_name = mEventName.getEditableText().toString();
        String event_type = mEventType.getEditableText().toString();
        String event_reg_fee = mReqFee.getEditableText().toString();
        String event_venue = mVenue.getEditableText().toString();
        String event_num_parti = mNumParticipate.getEditableText().toString();
        String event_co_name = mCordinatName.getEditableText().toString();
        String event_co_number = mCordinatNumber.getEditableText().toString();
        String event_description = mDescription.getEditableText().toString();

        if (!TextUtils.isEmpty(event_name) && !TextUtils.isEmpty(event_type) && !TextUtils.isEmpty(event_reg_fee) && !TextUtils.isEmpty(event_venue) && !TextUtils.isEmpty(event_num_parti) && !TextUtils.isEmpty(event_co_name) && !TextUtils.isEmpty(event_co_number) && !TextUtils.isEmpty(event_description)) {

            Map map = new HashMap();
            map.put("name", event_name);
            map.put("type", event_type);
            map.put("fee", event_reg_fee);
            map.put("venue", event_venue);
            map.put("num_part", event_num_parti);
            map.put("co_ord_name", event_co_name);
            map.put("co_ord_number", event_co_number);
            map.put("desc", event_description);
            map.put("date",date_time);
            map.put("admin",UID);

            mRef.updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                @Override
                public void onComplete(@NonNull Task task) {
                    if (task.isSuccessful()) {
                        finish();
                        Toast.makeText(AdminEventUpdate.this, "successfully posted", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(AdminEventUpdate.this, "Failed to post", Toast.LENGTH_SHORT).show();
                    }
                }
            }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(AdminEventUpdate.this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        yearFinal = year;
        monthFinal = month + 1;
        dayFinal = dayOfMonth;

        Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);


        TimePickerDialog timePickerDialog = new TimePickerDialog(AdminEventUpdate.this, AdminEventUpdate.this, hour, minute, DateFormat.is24HourFormat(this));
        timePickerDialog.show();
    }

    @Override
    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

        if (hourOfDay == 0) {
            hourOfDay += 12;
            format = "AM";
        } else if (hourOfDay == 12) {
            format = "PM";
        } else if (hourOfDay > 12) {
            hourOfDay -= 12;
            format = "PM";
        } else {
            format = "AM";
        }

        StringBuilder time = new StringBuilder().append(hourOfDay).append(":").append(minute).append(" ").append(format);

        date_time = dayFinal + "/" + monthFinal + "/" + yearFinal + " at " + time;
        mDateText.setText(date_time);

    }
}
