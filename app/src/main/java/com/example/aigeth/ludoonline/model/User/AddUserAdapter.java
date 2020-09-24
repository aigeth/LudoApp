package com.example.aigeth.ludoonline.model.User;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aigeth.ludoonline.R;
import com.example.aigeth.ludoonline.model.Board;

public class AddUserAdapter extends  RecyclerView.Adapter<AddUserAdapter.ViewHolder>{

    private Users users;
    private Context mContext;

    public AddUserAdapter(Context context) {
        this.users = Board.getInstance(context).getUsers();
        this.mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_add_user_layout, parent, false);
        AddUserAdapter.ViewHolder holder = new AddUserAdapter.ViewHolder(view);
        return new AddUserAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Glide.with(mContext).asBitmap().load(users.get(position).getUrl()).into(holder.profilePicture);
        holder.firstName.setText(users.get(position).getFirstName());
        holder.lastName.setText(users.get(position).getLastName());
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView firstName, lastName;
        ImageView profilePicture;
        RelativeLayout parentLayout;
        Button addButton;

        public ViewHolder(View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.listAddProfilePicture);
            firstName = itemView.findViewById(R.id.listAddFirstName);
            lastName = itemView.findViewById(R.id.listAddLastName);
            parentLayout = itemView.findViewById(R.id.parent_Layout);
            addButton = itemView.findViewById(R.id.listAddButton);
        }
    }
}
