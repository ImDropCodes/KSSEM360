package in.edu.kssem360.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import in.edu.kssem360.Admin.AdminEventUpdate;
import in.edu.kssem360.Admin.EventDetailActivity;
import in.edu.kssem360.Model.EventModelClass;
import in.edu.kssem360.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context context;
    private List<EventModelClass> modelClasses;

    public EventAdapter(Context context, List<EventModelClass> modelClasses) {
        this.context = context;
        this.modelClasses = modelClasses;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.event_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, int position) {

        final EventModelClass eventModelClass = modelClasses.get(position);
        holder.mName.setText(eventModelClass.getName());
        holder.mDate.setText("Date: "+eventModelClass.getDate());
        holder.mDepartment.setText("Department: "+eventModelClass.getDepartment());
        holder.mVenue.setText("Venue: "+eventModelClass.getVenue());

        Picasso.get().load(eventModelClass.getImage()).fit() .into(holder.mImage);

        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetailActivity.class);
                intent.putExtra("name",eventModelClass.getName());
                intent.putExtra("type",eventModelClass.getType());
                intent.putExtra("fee",eventModelClass.getFee());
                intent.putExtra("venue",eventModelClass.getVenue());
                intent.putExtra("num_part",eventModelClass.getNum_part());
                intent.putExtra("teacher_co",eventModelClass.getTeacher_co());
                intent.putExtra("student_co",eventModelClass.getStudent_co());
                intent.putExtra("department",eventModelClass.getDepartment());
                intent.putExtra("co_ord_number",eventModelClass.getCo_ord_number());
                intent.putExtra("desc",eventModelClass.getDesc());
                intent.putExtra("date",eventModelClass.getDate());
                intent.putExtra("admin",eventModelClass.getAdmin());
                intent.putExtra("image",eventModelClass.getImage());
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return modelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mName, mDate, mDepartment,mVenue;
        private CardView mCard;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.event_list_image);
            mName = itemView.findViewById(R.id.event_list_name);
            mDate = itemView.findViewById(R.id.event_list_date);
            mVenue = itemView.findViewById(R.id.event_list_venue);
            mDepartment = itemView.findViewById(R.id.event_list_department);
            mCard = itemView.findViewById(R.id.event_card_view);

        }
    }
}
