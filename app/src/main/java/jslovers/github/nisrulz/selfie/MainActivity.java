package jslovers.github.nisrulz.selfie;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import github.nisrulz.recyclerviewhelper.RVHItemClickListener;
import github.nisrulz.recyclerviewhelper.RVHItemDividerDecoration;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

  private static final int REQUEST_IMAGE_CAPTURE = 100;

  RecyclerView myrecyclerview;
  ArrayList<Selfie> selfieList;
  SelfieListAdapter adapter;

  LinearLayout ll_emptystate;

  String currentPhotoPath;
  String currentPhotoName;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    // Get the reference of empty state view
    ll_emptystate = (LinearLayout) findViewById(R.id.emptystate);

    myrecyclerview = (RecyclerView) findViewById(R.id.rv_selfielist);

    selfieList = new ArrayList<>();

    adapter = new SelfieListAdapter(this, selfieList);
    myrecyclerview.hasFixedSize();
    myrecyclerview.setLayoutManager(new LinearLayoutManager(this));
    myrecyclerview.setAdapter(adapter);

    // Set the divider
    myrecyclerview.addItemDecoration(
        new RVHItemDividerDecoration(this, LinearLayoutManager.VERTICAL));

    // Set On Click
    myrecyclerview.addOnItemTouchListener(
        new RVHItemClickListener(this, new RVHItemClickListener.OnItemClickListener() {
          @Override public void onItemClick(View view, int position) {
            Intent i = new Intent(MainActivity.this, DetailActivity.class);
            i.putExtra("path", selfieList.get(position).getPath());
            i.putExtra("name", selfieList.get(position).getName());
            startActivity(i);
          }
        }));

    FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
    fab.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        takePictureUsingIntent();
      }
    });

    showEmptyStateIfListEmpty();
  }

  private void takePictureUsingIntent() {
    currentPhotoName = getFileName();

    // create Intent to take a picture and return control to the calling application
    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    intent.putExtra(MediaStore.EXTRA_OUTPUT,
        getPhotoFileUri(currentPhotoName)); // set the image file name

    // If you call startActivityForResult() using an intent that no app can handle, your app will crash.
    // So as long as the result is not null, it's safe to use the intent.
    if (intent.resolveActivity(getPackageManager()) != null) {
      // Start the image capture intent to take photo
      startActivityForResult(intent, REQUEST_IMAGE_CAPTURE);
    }
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {

    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
      Selfie selfie = new Selfie(currentPhotoName, currentPhotoPath);
      selfieList.add(selfie);

      showEmptyStateIfListEmpty();

      adapter.notifyDataSetChanged();
    }
  }

  private void galleryAddPic(String filePath) {
    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
    File f = new File(filePath);
    Uri contentUri = Uri.fromFile(f);
    mediaScanIntent.setData(contentUri);
    sendBroadcast(mediaScanIntent);
  }

  private String getFileName() {
    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    String imageFileName = "JPEG_" + timeStamp + "_";
    return imageFileName;
  }

  // Returns the Uri for a photo stored on disk given the fileName
  public Uri getPhotoFileUri(String fileName) {
    // Only continue if the SD Card is mounted
    if (isExternalStorageAvailable()) {
      // Get safe storage directory for photos
      // Use `getExternalFilesDir` on Context to access package-specific directories.
      // This way, we don't need to request external read/write runtime permissions.
      File mediaStorageDir =
          new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "SelFie");

      // Create the storage directory if it does not exist
      if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
        System.out.println("failed to create directory");
      }

      // Return the file target for the photo based on filename
      File newFile = new File(mediaStorageDir.getPath() + File.separator + fileName);
      currentPhotoPath = newFile.getPath();
      galleryAddPic(currentPhotoPath);
      return Uri.fromFile(newFile);
    }
    return null;
  }

  // Returns true if external storage for photos is available
  private boolean isExternalStorageAvailable() {
    String state = Environment.getExternalStorageState();
    return state.equals(Environment.MEDIA_MOUNTED);
  }

  // Setup empty state function
  private void showEmptyStateIfListEmpty() {
    if (ll_emptystate != null) {
      if (selfieList != null && selfieList.size() != 0) {
        ll_emptystate.setVisibility(View.GONE);
      } else {
        ll_emptystate.setVisibility(View.VISIBLE);
      }
    }
  }

  // Options Menu

  @Override public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.menu_main, menu);
    return true;
  }

  @Override public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_deleteall) {
      if (selfieList != null && adapter != null) {
        selfieList.clear();
        showEmptyStateIfListEmpty();
        adapter.notifyDataSetChanged();
      }
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
