package jslovers.github.nisrulz.selfie;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import java.util.Collections;
import java.util.List;

public class SelfieListAdapter extends RecyclerView.Adapter<SelfieListAdapter.ItemViewHolder> {

  List<Selfie> selfieList;
  Context context;

  public SelfieListAdapter(Context context, List<Selfie> selfieList) {
    this.selfieList = selfieList;
    this.context = context;
  }

  @Override public ItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.rvlist_item, parent, false);

    return new ItemViewHolder(view);
  }

  @Override public void onBindViewHolder(ItemViewHolder holder, int position) {
    // Set the name of selfie in the holder
    holder.txtvw_name.setText(selfieList.get(position).getName());

    // Set the image in the holder using the helper
    ImageHelper.setImageFromFilePath(context, selfieList.get(position).getPath(),
        holder.imgvw_selfie);
  }

  @Override public int getItemCount() {
    return selfieList.size();
  }

  public class ItemViewHolder extends RecyclerView.ViewHolder {
    TextView txtvw_name;
    ImageView imgvw_selfie;

    public ItemViewHolder(View itemView) {
      super(itemView);

      // Setup the view id in the item view holder
      txtvw_name = (TextView) itemView.findViewById(R.id.txtvw_name);
      imgvw_selfie = (ImageView) itemView.findViewById(R.id.imgvw_selfie);
    }
  }

  // Helper functions for managing items of the list
  private void remove(int position) {
    selfieList.remove(position);
    notifyItemRemoved(position);
  }

  private void swap(int firstPosition, int secondPosition) {
    Collections.swap(selfieList, firstPosition, secondPosition);
    notifyItemMoved(firstPosition, secondPosition);
  }
}
