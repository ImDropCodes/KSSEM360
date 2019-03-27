package in.edu.kssem360.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import in.edu.kssem360.Model.FeedModelClass;
import in.edu.kssem360.R;

public class FeedAdapter extends RecyclerView.Adapter<FeedAdapter.ViewHolder> {

    private List<FeedModelClass> feedModelClass;

    public FeedAdapter(List<FeedModelClass> feedModelClass) {
        this.feedModelClass = feedModelClass;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.feed_items, parent, false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        FeedModelClass modelClass = feedModelClass.get(position);

        holder.mCaption.setText(modelClass.getCaption());
        Picasso.get().load(modelClass.getImage()).into(holder.mImage);


    }

    @Override
    public int getItemCount() {
        return feedModelClass.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mCaption;
        private CardView mCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.feed_item_image);
            mCaption = itemView.findViewById(R.id.feed_item_text);
            mCard = itemView.findViewById(R.id.feed_card_view);
        }

    }
}
