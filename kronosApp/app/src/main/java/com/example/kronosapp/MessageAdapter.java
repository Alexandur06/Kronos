package com.example.kronosapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Message> messageList;

    private int ITEM_RECEIVE = 1;
    private int ITEM_SENT = 2;

    public MessageAdapter (Context context, ArrayList<Message> messageList){
        this.context = context;
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if(viewType == 1){
            //receive
            View view = LayoutInflater.from(context).inflate(R.layout.receive, parent, false);
            return new ReceiveViewHolder(view);
        }
        else {
            //sent
            View view = LayoutInflater.from(context).inflate(R.layout.sent, parent, false);
            return new SentViewHolder(view);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Message currentMessage = messageList.get(position);

        if(holder.getClass() == SentViewHolder.class){
            RecyclerView.ViewHolder viewHolder = (SentViewHolder)holder;
            ((SentViewHolder) holder).sentMessage.setText(currentMessage.getMessage());
        }
        else{
            RecyclerView.ViewHolder viewHolder = (ReceiveViewHolder)holder;
            ((ReceiveViewHolder) holder).receiveMessage.setText(currentMessage.getMessage());
        }
    }

    @Override
    public int getItemViewType(int position) {
        Message currentMessage = messageList.get(position);

        if(FirebaseAuth.getInstance().getCurrentUser().getUid().equals(currentMessage.getSenderId())){
            return ITEM_SENT;
        }
        else {
            return ITEM_RECEIVE;
        }

    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    class SentViewHolder extends RecyclerView.ViewHolder{
        public TextView sentMessage;
        public SentViewHolder(@NonNull View itemView) {
            super(itemView);
            sentMessage = itemView.findViewById(R.id.txt_sent_message);
        }
    }

    class ReceiveViewHolder extends RecyclerView.ViewHolder{
        public TextView receiveMessage;
        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            receiveMessage = itemView.findViewById(R.id.txt_receive_message);
        }
    }
}
