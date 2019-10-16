package com.example.patpatchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patpatchat.model.User;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

    private static final String TAG = "RecyclerViewAdapter";
    private List<User> userList;

    private Context context;
    private OnItemClickListener itemClickListener;

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }

    public RecyclerViewAdapter(List<User> userList, Context context, OnItemClickListener itemClickListener) {
        this.userList = userList;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listuser, parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();
        User user = userList.get(pos);
        if(user != null){
            Picasso.get()
                    .load(user.getProfileImageUrl())
                    .into(holder.circleImageView);
            holder.textView.setText(user.getName());
            holder.textView1.setText(user.getEmail());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    itemClickListener.onItemClick(pos);
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        CircleImageView circleImageView;
        TextView textView, textView1;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            circleImageView = itemView.findViewById(R.id.image_profileUrl);
            textView = itemView.findViewById(R.id.profile_name);
            textView1 = itemView.findViewById(R.id.profile_email);
        }
    }
}
