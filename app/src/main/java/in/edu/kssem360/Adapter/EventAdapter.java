package in.edu.kssem360.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.List;

import in.edu.kssem360.Admin.EventDetailActivity;
import in.edu.kssem360.Model.EventModelClass;
import in.edu.kssem360.R;

public class EventAdapter extends RecyclerView.Adapter<EventAdapter.ViewHolder> {

    private Context context;
    private List<EventModelClass> modelClasses;
    private DatabaseReference mRef;
    private FirebaseUser mUser;
    private FirebaseAuth mAuth;

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
        mRef = FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();

        holder.mName.setText(eventModelClass.getName());
        holder.mDate.setText("Date: " + eventModelClass.getDate());
        holder.mDepartment.setText("By: " + eventModelClass.getDepartment());

        Picasso.get().load(eventModelClass.getImage()).placeholder(R.drawable.placeholder).into(holder.mImage);

        holder.mCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EventDetailActivity.class);
                intent.putExtra("name", eventModelClass.getName());
                intent.putExtra("type", eventModelClass.getType());
                intent.putExtra("fee", eventModelClass.getFee());
                intent.putExtra("venue", eventModelClass.getVenue());
                intent.putExtra("num_part", eventModelClass.getNum_part());
                intent.putExtra("teacher_co", eventModelClass.getTeacher_co());
                intent.putExtra("student_co", eventModelClass.getStudent_co());
                intent.putExtra("department", eventModelClass.getDepartment());
                intent.putExtra("co_ord_number", eventModelClass.getCo_ord_number());
                intent.putExtra("desc", eventModelClass.getDesc());
                intent.putExtra("date", eventModelClass.getDate());
                intent.putExtra("admin", eventModelClass.getAdmin());
                intent.putExtra("image", eventModelClass.getImage());
                context.startActivity(intent);
            }
        });

        String current_user_id = mUser.getUid();
        mRef.child("users").child(current_user_id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("status")) {

                    holder.mDelete.setVisibility(View.VISIBLE);
                } else {

                    holder.mDelete.setVisibility(View.GONE);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        holder.mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setMessage("Are you sure you want delete the Event ")
                        .setCancelable(false)
                        .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                mRef.child("event").child(eventModelClass.getName()).addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        mRef.child("event").child(eventModelClass.getName()).setValue(null);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });

                            }
                        })
                        .setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
                AlertDialog alert = builder.create();
                alert.show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return modelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView mImage;
        private TextView mName, mDate, mDepartment;
        private CardView mCard;
        private Button mDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mImage = itemView.findViewById(R.id.event_list_image);
            mName = itemView.findViewById(R.id.event_list_name);
            mDate = itemView.findViewById(R.id.event_list_date);
            mDepartment = itemView.findViewById(R.id.event_list_department);
            mCard = itemView.findViewById(R.id.event_card_view);
            mDelete = itemView.findViewById(R.id.event_delete_btn);

        }
    }
}
