package com.example.aigeth.ludoonline.Lobby;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.aigeth.ludoonline.ConnectionHandler.ClientConnection;
import com.example.aigeth.ludoonline.ConnectionHandler.CommandHandler;
import com.example.aigeth.ludoonline.GameLobby.GameLobbies;
import com.example.aigeth.ludoonline.R;
import com.example.aigeth.ludoonline.ConnectionHandler.SendHandler;

public class LobbyListAdapter extends RecyclerView.Adapter<LobbyListAdapter.ViewHolder> {
    private static final String TAG = "RecyclerViewAdapter";

    private GameLobbies gameLobbies;
    private Context mContext;
    private CommandHandler commandHandler;

    public LobbyListAdapter(Context context, CommandHandler commandHandler) {
        this.gameLobbies = GameLobbies.getInstance();
        this.commandHandler = commandHandler;
        this.mContext = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_lobby_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: called");

        Glide.with(mContext).asBitmap().load(gameLobbies.get(position).getUrl()).into(holder.profilePicture);
        if(gameLobbies.get(position).hasPassword()){
            Glide.with(mContext).asBitmap().load(BitmapFactory.decodeResource(mContext.getResources(), android.R.drawable.ic_secure)).into(holder.lock);
        }else
            holder.lock.setVisibility(View.GONE);

        holder.name.setText(gameLobbies.get(position).getName());
        holder.playersInLobby.setText(gameLobbies.get(position).getSize() + "/6");
        holder.joinButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SendHandler sendHandler = new SendHandler(ClientConnection.getInstance(commandHandler).getDout());
                sendHandler.joinLobby(gameLobbies.get(position).getId());
            }
        });
    }

    @Override
    public int getItemCount() {
        return gameLobbies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView name, playersInLobby;
        ImageView profilePicture, lock;
        RelativeLayout parentLayout;
        Button joinButton;

        public ViewHolder(View itemView) {
            super(itemView);
            profilePicture = itemView.findViewById(R.id.profilePicture);
            name = itemView.findViewById(R.id.roomName);
            playersInLobby = itemView.findViewById(R.id.playersInLobby);
            lock = itemView.findViewById(R.id.lock);
            parentLayout = itemView.findViewById(R.id.parent_Layout);
            joinButton = itemView.findViewById(R.id.joinButton);
        }
    }
}
