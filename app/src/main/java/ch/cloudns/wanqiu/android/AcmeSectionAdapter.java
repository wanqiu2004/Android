package ch.cloudns.wanqiu.android;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class AcmeSectionAdapter extends RecyclerView.Adapter<AcmeSectionAdapter.ViewHolder> {

  private final List<String> items;
  private final LayoutInflater inflater;

  public AcmeSectionAdapter(Context context, List<String> items) {
    this.items = items;
    this.inflater = LayoutInflater.from(context);
  }

  @NonNull
  @Override
  public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View v = inflater.inflate(R.layout.item_acme, parent, false);
    return new ViewHolder(v);
  }

  @Override
  public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
    holder.text.setText(items.get(position));
  }

  @Override
  public int getItemCount() {
    return items.size();
  }

  static class ViewHolder extends RecyclerView.ViewHolder {
    TextView text;

    ViewHolder(View itemView) {
      super(itemView);
      text = itemView.findViewById(R.id.textTitle);
    }
  }
}
