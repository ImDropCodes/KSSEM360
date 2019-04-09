package in.edu.kssem360.Admin;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
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
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import de.hdodenhof.circleimageview.CircleImageView;
import in.edu.kssem360.R;

public class AdminEventUpdate extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener {

    private Button mDatePick, mPostEvent;
    private TextView mDateText;
    int day, month, year, hour, minute;
    int dayFinal, monthFinal, yearFinal;
    private String format = "";
    private String date_time, order;
    private TextInputEditText mRegistrationURL,mEventName, mEventType, mReqFee, mVenue, mDuration, mNumParticipate, mCordinatNameStudent, mCordinatNumber, mDescription, mCordinatNameTeacher, mDepartment;
    private CircleImageView mPoaterImage;

    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    public FirebaseStorage mStorage;
    public StorageReference mStorageRef;
    private String UID;
    public Uri uri;
    public String image_url;

    private static int REQUEST_CODE = 100;

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
        mCordinatNameStudent = findViewById(R.id.event_co_ord_name);
        mCordinatNameTeacher = findViewById(R.id.event_co_ord_name_teacher);
        mDepartment = findViewById(R.id.event_department);
        mCordinatNumber = findViewById(R.id.event_co_ord_number);
        mDescription = findViewById(R.id.event_description);
        mPostEvent = findViewById(R.id.event_post);
        mPoaterImage = findViewById(R.id.poster);
        mDuration = findViewById(R.id.event_duration);
        mRegistrationURL = findViewById(R.id.event_registration_url);

        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mRef = mDatabase.getReference().child("event");
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
        mPoaterImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "SELECT IMAGE"), REQUEST_CODE);
            }
        });
    }

    private void postEvent() {

        String event_name = mEventName.getEditableText().toString();
        String event_type = mEventType.getEditableText().toString();
        String event_reg_fee = mReqFee.getEditableText().toString();
        String event_venue = mVenue.getEditableText().toString();
        String event_num_parti = mNumParticipate.getEditableText().toString();
        String event_co_nameStudent = mCordinatNameStudent.getEditableText().toString();
        String event_co_nameTeacher = mCordinatNameTeacher.getEditableText().toString();
        String event_department = mDepartment.getEditableText().toString();
        String event_co_number = mCordinatNumber.getEditableText().toString();
        String event_description = mDescription.getEditableText().toString();
        String event_duration = mDuration.getEditableText().toString();
        String event_reg_url = mRegistrationURL.getEditableText().toString();

        if (!TextUtils.isEmpty(event_name)&& !TextUtils.isEmpty(image_url) && !TextUtils.isEmpty(event_type) && !TextUtils.isEmpty(event_reg_fee) && !TextUtils.isEmpty(event_venue) && !TextUtils.isEmpty(event_num_parti) && !TextUtils.isEmpty(event_co_nameStudent) && !TextUtils.isEmpty(event_co_nameTeacher) && !TextUtils.isEmpty(event_department) && !TextUtils.isEmpty(event_co_number) && !TextUtils.isEmpty(event_description) && !TextUtils.isEmpty(event_duration)) {

            Map map = new HashMap();
            map.put("name", event_name);
            map.put("type", event_type);
            map.put("fee", event_reg_fee);
            map.put("venue", event_venue);
            map.put("num_part", event_num_parti);
            map.put("teacher_co", event_co_nameTeacher);
            map.put("student_co", event_co_nameStudent);
            map.put("department", event_department);
            map.put("co_ord_number", event_co_number);
            map.put("desc", event_description);
            map.put("date", date_time);
            map.put("admin", UID);
            map.put("image",image_url);
            map.put("order", order);
            map.put("reg_url",event_reg_url);

            mRef.child(event_name).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
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
        } else {
            Toast.makeText(this, "Please enter all details", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            final ProgressDialog imageProgress = new ProgressDialog(AdminEventUpdate.this);
            imageProgress.setMessage("Please wait...");
            imageProgress.setTitle("Uploading Image");
            imageProgress.setIcon(R.mipmap.ic_launcher_logo);
            imageProgress.setCanceledOnTouchOutside(false);
            imageProgress.show();
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] image_byte = baos.toByteArray();

                mStorageRef = mStorage.getReference().child("fest").child("event").child(random() + ".jpeg");

                UploadTask uploadTask = mStorageRef.putBytes(image_byte);
                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {

                            mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri download_uri) {
                                    image_url = download_uri.toString();
                                    mPoaterImage.setImageURI(uri);
                                    imageProgress.dismiss();
                                    Toast.makeText(AdminEventUpdate.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(AdminEventUpdate.this, "error while uploading image please try again", Toast.LENGTH_LONG).show();
                            imageProgress.hide();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AdminEventUpdate.this, "error while getting image please try again", Toast.LENGTH_LONG).show();
            }

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
        order = "" + dayFinal + monthFinal + yearFinal + time;
        mDateText.setText(date_time);

    }

    public static String random() {
        Random generator = new Random();
        StringBuilder stringBuilder = new StringBuilder();
        int randomLength = generator.nextInt(10);
        char tempChar;
        for (int i = 0; i < randomLength; i++) {
            tempChar = (char) (generator.nextInt(96) + 32);
            stringBuilder.append(tempChar);
        }
        return stringBuilder.toString();
    }
}
