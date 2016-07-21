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
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class MainActivity extends AppCompatActivity {

  private static final int REQUEST_IMAGE_CAPTURE = 100;

  RecyclerView myrecyclerview;
  ArrayList<Selfie> selfieList;
  SelfieListAdapter adapter;

  String currentSelfiePath;
  String currentSelfieName;

  LinearLayout ll_emptystate;

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
    Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
    // Ensure that there's a camera activity to handle the intent
    if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
      // Create the File where the photo should go
      File photoFile = null;
      try {
        photoFile = createImageFile();
      } catch (IOException ex) {
        // Error occurred while creating the File
      }

      // Continue only if the File was successfully created
      if (photoFile != null) {
        takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(photoFile));
        startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
      }
    }
  }

  private File createImageFile() throws IOException {
    // Create an image file name
    currentSelfieName = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
    File imageFile = File.createTempFile(currentSelfieName, ".jpg", getExternalFilesDir(null));
    currentSelfiePath = imageFile.getAbsolutePath();
    return imageFile;
  }

  @Override protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {

      File photoFile = new File(currentSelfiePath);
      File selfieFile =
          new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), currentSelfieName + ".jpg");
      photoFile.renameTo(selfieFile);

      Selfie selfie = new Selfie(currentSelfieName, Uri.fromFile(selfieFile).getPath());
      selfieList.add(selfie);

      showEmptyStateIfListEmpty();

      adapter.notifyDataSetChanged();
    } else {
      File photoFile = new File(currentSelfiePath);
      photoFile.delete();
    }
  }

  private void showEmptyStateIfListEmpty() {
    if (ll_emptystate != null) {
      if (selfieList != null && selfieList.size() != 0) {
        ll_emptystate.setVisibility(View.GONE);
      } else {
        ll_emptystate.setVisibility(View.VISIBLE);
      }
    }
  }

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
