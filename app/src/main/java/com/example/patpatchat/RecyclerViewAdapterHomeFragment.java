package com.example.patpatchat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.patpatchat.model.Messages;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class RecyclerViewAdapterHomeFragment extends RecyclerView.Adapter<RecyclerViewAdapterHomeFragment.ViewHolder> {

    private static final String TAG = "RecyclerViewAdapter";
    private List<Messages> userListHome;

    private Context context;
    private OnItemClickListener itemClickListener;

    public void setUserListHome(List<Messages> userListHome){
        this.userListHome = userListHome;
    }

    public RecyclerViewAdapterHomeFragment(List<Messages> userListHome, Context context, OnItemClickListener itemClickListener) {
        this.userListHome = userListHome;
        this.context = context;
        this.itemClickListener = itemClickListener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_listuser_home,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final int pos = holder.getAdapterPosition();

        Messages messages = userListHome.get(pos);

        if(userListHome != null){

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
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        CircleImageView circleImageView;
        TextView textView, textView1;

        ViewHolder(@NonNull View itemView){
            super(itemView);

            circleImageView = itemView.findViewById(R.id.home_image_profileUrl);
            textView = itemView.findViewById(R.id.home_name);
            textView1 = itemView.findViewById(R.id.home_last_message);

        }

    }

}
