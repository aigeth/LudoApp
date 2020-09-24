package com.example.aigeth.ludoonline.model.User;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aigeth.ludoonline.ConnectionHandler.ClientConnection;
import com.example.aigeth.ludoonline.ConnectionHandler.SendHandler;
import com.example.aigeth.ludoonline.R;
import com.example.aigeth.ludoonline.model.Board;

public class UserListAdapter extends RecyclerView.Adapter<UserListAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private Users users;
    private Context mContext;
    private ClientConnection clientConnection;

    public UserListAdapter(Context context, ClientConnection clientConnection) {
        this.users = Board.getInstance(context).getUsers();
        this.mContext = context;
        this.clientConnection = clientConnection;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_user_layout, parent, false);
        UserListAdapter.ViewHolder holder = new UserListAdapter.ViewHolder(view);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserListAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext).asBitmap().load(users.get(position).getUrl()).into(holder.profilePicture);
        holder.firstName.setText(users.get(position).getFirstName());
        holder.lastName.setText(users.get(position).getLastName());
        holder.cardView.setCardBackgroundColor(users.get(position).getReady());
        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(users.get(position).getReady() == Color.RED){
                    SendHandler sendHandler = new SendHandler(clientConnection.getDout());
                    sendHandler.ready();
                }
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
        CardView cardView;

        public ViewHolder(View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.gameProfilePic);
            firstName = itemView.findViewById(R.id.gameUserFirstName);
            lastName = itemView.findViewById(R.id.gameUserLastName);
            cardView = itemView.findViewById(R.id.gameCardView);
            parentLayout = itemView.findViewById(R.id.parent_Layout_Game);
        }
    }
}
