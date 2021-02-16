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
import com.mrshadat.bdfinanceinternshipapp.models.Deposit;

import de.hdodenhof.circleimageview.CircleImageView;

public class DepositAdapter extends FirebaseRecyclerAdapter<Deposit,DepositAdapter.myviewholder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public DepositAdapter(@NonNull FirebaseRecyclerOptions<Deposit> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myviewholder holder, int position, @NonNull Deposit model) {

        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);

    }

    @NonNull
    @Override
    public myviewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_deposit_product_item, parent, false);
        return new myviewholder(view);
    }

    class myviewholder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView title, description;

        public myviewholder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.deposit_img);
            title = itemView.findViewById(R.id.deposit_name_tv);
            description = itemView.findViewById(R.id.deposit_desc_tv);


        }
    }
}
