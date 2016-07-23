package jslovers.github.nisrulz.selfie;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;

public class DetailActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_detail);

    // get data from incoming intent
    String imagepath = getIntent().getExtras().getString("path");
    String name = getIntent().getExtras().getString("name");

    // Get reference to imageview
    ImageView imgvw_details = (ImageView) findViewById(R.id.imgvw_selfie_detail);

    // Set image in imageview
    imgvw_details.setImageBitmap(BitmapFactory.decodeFile(imagepath));
    // Set title of the activity
    getSupportActionBar().setTitle(name);
  }
}
