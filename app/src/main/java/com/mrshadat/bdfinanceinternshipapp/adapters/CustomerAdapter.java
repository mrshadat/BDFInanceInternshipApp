package com.mrshadat.bdfinanceinternshipapp.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.mrshadat.bdfinanceinternshipapp.R;
import com.mrshadat.bdfinanceinternshipapp.models.CustomerInfo;

import de.hdodenhof.circleimageview.CircleImageView;

public class CustomerAdapter extends FirebaseRecyclerAdapter<CustomerInfo,CustomerAdapter.myviewholderr> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public CustomerAdapter(@NonNull FirebaseRecyclerOptions<CustomerInfo> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholderr holder, int position, @NonNull CustomerInfo model) {
        holder.title.setText(model.getName());
        holder.description.setText(model.getProfession());
       Glide.with(holder.img.getContext()).load(model.getImg()).into(holder.img);
    }

    @NonNull
    @Override
    public myviewholderr onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_customer_item, parent, false);
        return new myviewholderr(view);
    }

    class myviewholderr extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView title, description;

        public myviewholderr(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.customer_img);
            title = itemView.findViewById(R.id.customer_name_tv);
            description = itemView.findViewById(R.id.customer_details_tv);

        }
    }
}
