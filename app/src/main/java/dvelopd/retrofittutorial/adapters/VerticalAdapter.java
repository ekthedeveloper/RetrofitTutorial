package dvelopd.retrofittutorial.adapters;
import android.content.Context;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.List;

import dvelopd.retrofittutorial.R;
import dvelopd.retrofittutorial.helpers.CircleTransform;
import dvelopd.retrofittutorial.models.Search;

public class VerticalAdapter extends RecyclerView.Adapter<VerticalAdapter.CustomViewHolder> {

    private Context context;
    private List<Search> dataList;

    public VerticalAdapter(Context context, List<Search> dataList){
        this.context = context;
        this.dataList = dataList;
    }

    @Override
    public CustomViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.data_single_item, null);
        CustomViewHolder viewHolder = new CustomViewHolder(view);

//        view.setOnClickListener(this);

        return viewHolder;
    }


    @Override
    public void onBindViewHolder(VerticalAdapter.CustomViewHolder holder, int position) {
        Search search = dataList.get(position);

        Picasso.get()
                .load(search.getPoster())
                .into(holder.fullImage);

        holder.nameTextView.setText(search.getTitle());
        holder.timeStamp.setText(search.getYear());
        holder.profileName.setText(search.getType());
    }

    @Override
    public int getItemCount() {
        return (dataList!=null ? dataList.size() : 0);
    }


    class CustomViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        ImageView fullImage;
        ImageView profileImage;
        TextView nameTextView;
        TextView profileName;
        TextView timeStamp;
        CardView cardView;


        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        CustomViewHolder(View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            nameTextView = (TextView) itemView.findViewById(R.id.name_text_view);
            profileName = (TextView) itemView.findViewById(R.id.profile_name);
            timeStamp = (TextView) itemView.findViewById(R.id.time_stamp);
            fullImage = (ImageView) itemView.findViewById(R.id.full_image);
            profileImage = (ImageView) itemView.findViewById(R.id.profile_image);
            cardView = (CardView) itemView.findViewById(R.id.card_view);

            cardView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            Toast.makeText(context, dataList.get(clickedPosition).getTitle(), Toast.LENGTH_LONG).show();
        }
    }
}
