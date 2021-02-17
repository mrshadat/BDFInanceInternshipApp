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
import com.mrshadat.bdfinanceinternshipapp.models.Loan;

import de.hdodenhof.circleimageview.CircleImageView;

public class LoanAdapter extends FirebaseRecyclerAdapter<Loan,LoanAdapter.loanHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public LoanAdapter(@NonNull FirebaseRecyclerOptions<Loan> options) {
        super(options);
    }

    @NonNull
    @Override
    public loanHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_loan_product_item, parent, false);
        return new loanHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull loanHolder holder, int position, @NonNull Loan model) {
        holder.title.setText(model.getTitle());
        holder.description.setText(model.getDescription());
        Glide.with(holder.img.getContext()).load(model.getPurl()).into(holder.img);
    }

    class loanHolder extends RecyclerView.ViewHolder {

        CircleImageView img;
        TextView title, description;

        public loanHolder(@NonNull View itemView) {
            super(itemView);

            img = itemView.findViewById(R.id.loan_img);
            title = itemView.findViewById(R.id.loan_name_tv);
            description = itemView.findViewById(R.id.loan_desc_tv);

        }
    }

}
