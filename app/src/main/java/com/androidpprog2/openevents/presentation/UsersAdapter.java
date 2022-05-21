package com.androidpprog2.openevents.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.Event;
import com.androidpprog2.openevents.business.User;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;

import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterViewHolder> {

    ArrayList<User> users;
    Context context;

    public UsersAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersAdapter.UsersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user, parent, false);
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.UsersAdapterViewHolder holder, int position) {
        holder.bind(users.get(position), context);
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    public static class UsersAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private User user;
        private Context context;
        private ImageView user_image;
        private TextView user_name;
        private TextView user_lastName;
        private TextView user_id;
        private TextView user_email;
        private MaterialCardView userCard;

        public UsersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.user_image = itemView.findViewById(R.id.user_image);
            this.user_name = itemView.findViewById(R.id.userName);
            this.user_lastName = itemView.findViewById(R.id.userLastName);
            this.user_id = itemView.findViewById(R.id.userID);
            this.user_email = itemView.findViewById(R.id.userEmail);
        }

        public void bind(User user, Context context) {
            this.user = user;
            this.context = context;

            String url = "";
            if (this.user.getImage() != null) {
                if (this.user.getImage().startsWith("http")) {
                    url = this.user.getImage();
                } else {
                    url = "http://172.16.205.68/img/" + this.user.getImage();
                }
            }
            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions
                            .bitmapTransform(new BlurTransformation(10, 3))
                            .placeholder(R.drawable.default_event)
                            .error(R.drawable.default_event))
                    .into(user_image);


            this.userCard.setOnClickListener(this);
            this.user_name.setText(this.user.getName());
            this.user_lastName.setText(this.user.getLast_name());
            this.user_id.setText(this.user.getId());
            this.user_email.setText(this.user.getEmail());
        }

        @Override
        public void onClick(View view) {

        }
    }
}
