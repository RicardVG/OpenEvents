package com.androidpprog2.openevents.presentation.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.androidpprog2.openevents.R;
import com.androidpprog2.openevents.business.User;
import com.androidpprog2.openevents.presentation.activities.InfoUserActivity;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;
import java.util.ArrayList;
import jp.wasabeef.glide.transformations.BlurTransformation;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.UsersAdapterViewHolder> {

    ArrayList<User> users;
    private Context context;

    public UsersAdapter(ArrayList<User> users, Context context) {
        this.users = users;
        this.context = context;
    }

    @NonNull
    @Override
    public UsersAdapter.UsersAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.single_user, parent, false);
        return new UsersAdapter.UsersAdapterViewHolder(view);
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
        private MaterialCardView userCard;

        public UsersAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            this.user_image = (ImageView) itemView.findViewById(R.id.userImage);
            this.user_name = (TextView) itemView.findViewById(R.id.userName);
            this.user_lastName = (TextView) itemView.findViewById(R.id.userLastName);
            this.userCard = (MaterialCardView) itemView.findViewById(R.id.userCard);

        }

        public void bind(User _user, Context _context) {
            this.user = _user;
            this.context = _context;

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
                            .bitmapTransform(new BlurTransformation(10, 1))
                            .placeholder(R.drawable.default_event)
                            .error(R.drawable.icon_profile_user))
                    .into(user_image);


            this.userCard.setOnClickListener(this);
            this.user_name.setText(this.user.getName());
            this.user_lastName.setText(this.user.getLast_name());
        }

        //Aquesta funció s'executarà si li donem a un card de un usuari. Si és així ens dirigirem a la
        //activity de InfoUser. També li passarem a través del intent la id de l'usuari.
        @Override
        public void onClick(View view) {
            Intent intent = new Intent(context, InfoUserActivity.class);
            intent.putExtra("id", this.user.getId());
            this.context.startActivity(intent);
        }
    }
}
