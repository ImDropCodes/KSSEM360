package in.edu.kssem360.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;
import in.edu.kssem360.Model.AboutDeveloperModelClass;
import in.edu.kssem360.R;

public class AboutDeveloperAdapter extends RecyclerView.Adapter<AboutDeveloperAdapter.ViewHolder> {

    private List<AboutDeveloperModelClass> modelClasses;
    private Context context;



    public AboutDeveloperAdapter(List<AboutDeveloperModelClass> modelClasses, Context context) {
        this.modelClasses = modelClasses;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.about_dev_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        AboutDeveloperModelClass aboutDeveloperModelClass = modelClasses.get(position);

        holder.mName.setText(aboutDeveloperModelClass.getName());
        holder.mYear.setText(aboutDeveloperModelClass.getYear());
        holder.mDepartment.setText(aboutDeveloperModelClass.getDepartment());


    }

    @Override
    public int getItemCount() {
        return modelClasses.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView mName,mYear,mDepartment;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            mName = itemView.findViewById(R.id.dev_name);
            mYear = itemView.findViewById(R.id.dev_year);
            mDepartment = itemView.findViewById(R.id.dev_department);

        }
    }

}
