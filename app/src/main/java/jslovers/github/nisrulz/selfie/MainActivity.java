package jslovers.github.nisrulz.selfie;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import github.nisrulz.recyclerviewhelper.RVHItemClickListener;
import github.nisrulz.recyclerviewhelper.RVHItemDividerDecoration;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

  RecyclerView myrecyclerview;
  ArrayList<Selfie> selfieList;
  SelfieListAdapter adapter;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    myrecyclerview = (RecyclerView) findViewById(R.id.rv_selfielist);

    selfieList = new ArrayList<>();
    selfieList.add(new Selfie("Selfie 1", "dummy_path"));
    selfieList.add(new Selfie("Selfie 2", "dummy_path"));

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
        Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
            .setAction("Action", null)
            .show();
      }
    });
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
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
