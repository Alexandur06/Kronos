package com.example.kronosapp.private_chat;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kronosapp.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MessageAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private ArrayList<Message> messageList;

    private int ITEM_RECEIVE = 1;
    private int ITEM_SENT = 2;

    DatabaseReference db = FirebaseDatabase.getInstance("https://kronos-app-tues-default-rtdb.europe-west1.firebasedatabase.app").getReference();
    ArrayList<User> userList;

    public MessageAdapter (Context context, ArrayList<Message> messageList){
        this.context = context;
        this.messageList = messageList;

        userList = new ArrayList<>();
        db.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    User currentUser = postSnapshot.getValue(User.class);
//                    assert currentUser != null;
//                    if(!Objects.requireNonNull(mAuth.getCurrentUser()).getUid().equals(currentUser.getUid())){
                    userList.add(currentUser);
//                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

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

            //find user from id


            for(User u : userList){
                if(u.getUid().equals(currentMessage.getSenderId())){
                    ((ReceiveViewHolder) holder).receiveMessageName.setText(u.getName());
                }
            }

//            ((ReceiveViewHolder) holder).receiveMessageName.setText(currentMessage.getSenderId());
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
        public TextView receiveMessageName;
        public ReceiveViewHolder(@NonNull View itemView) {
            super(itemView);
            receiveMessage = itemView.findViewById(R.id.txt_receive_message);
            receiveMessageName = itemView.findViewById(R.id.txt_receive_message_name);
        }
    }
}
