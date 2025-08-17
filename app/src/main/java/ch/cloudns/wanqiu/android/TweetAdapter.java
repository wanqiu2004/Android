package ch.cloudns.wanqiu.android;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;

public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.TweetViewHolder> {

  private final List<String> tweetList;

  public TweetAdapter(List<String> tweetList) {
    this.tweetList = tweetList;
  }

  @NonNull
  @Override
  public TweetViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
    View view =
        LayoutInflater.from(parent.getContext()).inflate(R.layout.item_tweet, parent, false);
    return new TweetViewHolder(view);
  }

  @Override
  public void onBindViewHolder(@NonNull TweetViewHolder holder, int position) {
    holder.textViewTweet.setText(tweetList.get(position));
  }

  @Override
  public int getItemCount() {
    return tweetList.size();
  }

  static class TweetViewHolder extends RecyclerView.ViewHolder {
    final TextView textViewTweet;

    TweetViewHolder(@NonNull View itemView) {
      super(itemView);
      textViewTweet = itemView.findViewById(R.id.textViewTweet);
    }
  }
}
