package in.edu.kssem360.Admin;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ServerValue;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import in.edu.kssem360.R;

public class AdminFeedUpdate extends AppCompatActivity {

    private ImageView mImage;
    private TextInputEditText mEditText;
    private Button mPost;
    private static int REQUEST_CODE = 100;
    private FirebaseDatabase mDatabase;
    private DatabaseReference mRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    public FirebaseStorage mStorage;
    public StorageReference mStorageRef;
    private String UID;
    public Uri uri;
    public String image_url,name;
    public Uri photo_url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_feed_update);

        mImage = findViewById(R.id.feed_image_view);
        mEditText = findViewById(R.id.feed_edit_text);
        mPost = findViewById(R.id.feed_post_button);


        mAuth = FirebaseAuth.getInstance();
        mDatabase = FirebaseDatabase.getInstance();
        mStorage = FirebaseStorage.getInstance();
        mRef = mDatabase.getReference().child("feed");
        mUser = mAuth.getCurrentUser();
        UID = mUser.getUid();
        name = mUser.getDisplayName();
        photo_url = mUser.getPhotoUrl();

        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(i, "SELECT IMAGE"), REQUEST_CODE);
            }
        });

        mPost.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String caption = mEditText.getEditableText().toString();

                if (!TextUtils.isEmpty(caption) && !TextUtils.isEmpty(image_url) && !TextUtils.isEmpty(UID)) {

                    Map map = new HashMap();
                    map.put("caption", caption);
                    map.put("image",image_url);
                    map.put("uid",UID);

                    mRef.child(caption).updateChildren(map).addOnCompleteListener(new OnCompleteListener() {
                        @Override
                        public void onComplete(@NonNull Task task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AdminFeedUpdate.this, "feed posted successfully", Toast.LENGTH_SHORT).show();
                                finish();
                            } else {
                                Toast.makeText(AdminFeedUpdate.this, "Failed to post feed try again", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

            final ProgressDialog imageProgress = new ProgressDialog(AdminFeedUpdate.this);
            imageProgress.setMessage("Please wait...");
            imageProgress.setTitle("Uploading Image");
            imageProgress.setIcon(R.mipmap.ic_launcher);
            imageProgress.setCanceledOnTouchOutside(false);
            imageProgress.show();
            uri = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), uri);
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                final byte[] image_byte = baos.toByteArray();

                mStorageRef = mStorage.getReference().child("fest").child("feed").child(random() + ".jpeg");

                UploadTask uploadTask = mStorageRef.putBytes(image_byte);
                uploadTask.addOnCompleteListener(new OnCompleteListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<UploadTask.TaskSnapshot> task) {

                        if (task.isSuccessful()) {

                            mStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri download_uri) {
                                    image_url = download_uri.toString();
                                    mImage.setImageURI(uri);
                                    imageProgress.dismiss();
                                    Toast.makeText(AdminFeedUpdate.this, "Image uploaded successfully", Toast.LENGTH_LONG).show();
                                }
                            });
                        } else {
                            Toast.makeText(AdminFeedUpdate.this, "error while uploading image please try again", Toast.LENGTH_LONG).show();
                            imageProgress.hide();
                        }
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
                Toast.makeText(AdminFeedUpdate.this, "error while getting image please try again", Toast.LENGTH_LONG).show();
            }

        }
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
