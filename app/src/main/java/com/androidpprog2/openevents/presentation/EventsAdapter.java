package com.androidpprog2.openevents.presentation;



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
import com.androidpprog2.openevents.business.Event;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.material.card.MaterialCardView;


import java.util.ArrayList;

import jp.wasabeef.glide.transformations.BlurTransformation;


public class EventsAdapter extends RecyclerView.Adapter<EventsAdapter.EventsListViewHolder> {
    ArrayList<Event> events;
    Context context;

    public EventsAdapter(ArrayList<Event> events, Context context) {
        this.events = events;
        this.context = context;
    }

    @NonNull
    @Override
    public EventsAdapter.EventsListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.singleevent, parent, false);
        return new EventsAdapter.EventsListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull EventsAdapter.EventsListViewHolder holder, int position) {
        holder.bind(events.get(position), context);
    }

    @Override
    public int getItemCount() {
        return this.events.size();
    }

    public static class EventsListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Event event;
        private Context context;
        private ImageView event_image;
        private TextView name;
        private TextView location;
        private TextView date;
        private TextView category;
        private MaterialCardView eventCard;
        private ImageView deleteEventButton;
        public EventsListViewHolder(@NonNull View itemView) {
            super(itemView);
            this.deleteEventButton = itemView.findViewById(R.id.deleteEvent);
            this.event_image = (ImageView) itemView.findViewById(R.id.eventImageList);
            this.name = (TextView) itemView.findViewById(R.id.eventTitleList);
            this.location = (TextView) itemView.findViewById(R.id.eventLocationList);
            this.date = (TextView) itemView.findViewById(R.id.eventDateList);
            this.category = (TextView) itemView.findViewById(R.id.eventCategoryList);
            this.eventCard = (MaterialCardView) itemView.findViewById(R.id.event_card);



        }

        public void bind(Event _event, Context _context) {
            this.event = _event;
            this.context = _context;
            String url = "";
            if (this.event.getImage() != null) {
                if (this.event.getImage().startsWith("https")) {
                    url = this.event.getImage();
                } else {
                    url = "https://172.16.205.68/img/" + this.event.getImage();
                }
            }

            Glide.with(context)
                    .load(url)
                    .apply(RequestOptions
                            .bitmapTransform(new BlurTransformation(10, 3))
                            .placeholder(R.drawable.default_event)
                            .error(R.drawable.default_event))
                    .into(event_image);

            this.eventCard.setOnClickListener(this);
            this.name.setText(this.event.getName());
            this.location.setText(this.event.getLocation());
            this.date.setText((CharSequence) this.event.getEndDate());
            this.category.setText(this.event.getType());
            this.deleteEventButton.setTag(new Integer(this.event.getId()));
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, InfoEventActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("id", this.event.getId());
            this.context.startActivity(intent);


        }
    }
}
