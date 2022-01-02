package com.example.objetivo09;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAdapterNotification extends RecyclerView.Adapter<RecyclerAdapterNotification.ViewHolder> {
    private final ArrayList<Notification> arrayList;

    public RecyclerAdapterNotification(ArrayList<Notification> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view =inflater.inflate(R.layout.notificacion, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull RecyclerAdapterNotification.ViewHolder holder, int position) {
        Notification feed = arrayList.get(position);

        holder.interaccion.setText(feed.getUsuario() + " " + feed.getInteraccion() + " tu publicacion: " + feed.getPublicacion());
        holder.profileImage.setImageResource(feed.getProfileIcon());
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        ImageView profileImage;
        TextView interaccion;

        public ViewHolder(@NonNull View itemView){
            super(itemView);

            profileImage = itemView.findViewById(R.id.imgNotification);
            interaccion = itemView.findViewById(R.id.interaccion);


        }
    }

}
