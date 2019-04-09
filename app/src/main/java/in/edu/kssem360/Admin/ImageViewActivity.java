package in.edu.kssem360.Admin;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import in.edu.kssem360.R;

public class ImageViewActivity extends AppCompatActivity {
    private ImageView mImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_view);

        mImage = findViewById(R.id.image_detail);

        Bundle bundle = getIntent().getExtras();

        String image_url = bundle.getString("image");

        Picasso.get().load(image_url).into(mImage);
    }
}
