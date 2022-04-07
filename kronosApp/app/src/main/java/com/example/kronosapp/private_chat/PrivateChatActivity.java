package com.example.kronosapp.private_chat;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.kronosapp.private_chat.MessageAdapter;
import com.example.kronosapp.R;
import com.example.kronosapp.calendar.CalendarActivity;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PrivateChatActivity extends AppCompatActivity {

    private TextView groupName;
    private Button changeToCalendar;

    private RecyclerView chatRecyclerView;
    private EditText messageBox;
    private Button sendButton;

    private MessageAdapter messageAdapter;
    private ArrayList<Message> messageList;

    private String receiverRoom = null;
    private String senderRoom = null;

    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_chat);

        Intent intent = getIntent();
        String name = intent.getStringExtra("groupName");
        String receiverUid = intent.getStringExtra("uid");
        mDbRef = FirebaseDatabase.getInstance("https://kronos-app-tues-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        String senderUid = FirebaseAuth.getInstance().getCurrentUser().getUid();

        senderRoom = receiverUid + senderUid;
        receiverRoom = senderUid + receiverUid;

//        getSupportActionBar().setTitle(name);

        groupName = findViewById(R.id.groupName);
        groupName.setText(name);
        changeToCalendar = (Button) findViewById(R.id.changeToCalendar);

        changeToCalendar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCalendarActivity();

            }
        });

        chatRecyclerView = findViewById(R.id.chatRecyclerView);
        messageBox = findViewById(R.id.messageBox);
        sendButton = findViewById(R.id.sentButton);

        messageList = new ArrayList<>();
        messageAdapter = new MessageAdapter(this, messageList);

        chatRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        chatRecyclerView.setAdapter(messageAdapter);

        //logic for adding data to recyclerView
        mDbRef.child("chats").child(senderRoom).child("messages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                messageList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Message message = postSnapshot.getValue(Message.class);
                    messageList.add(message);
                }
                messageAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        //add message to database
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = messageBox.getText().toString();
                Message messageObject = new Message(message, senderUid);

                scanMessageForCommand(message);

                mDbRef.child("chats").child(senderRoom).child("messages").push().setValue(messageObject).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        mDbRef.child("chats").child(receiverRoom).child("messages").push().setValue(messageObject);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        System.out.println("fail ");
                    }
                });
                messageBox.setText("");
            }
        });
    }

    private void scanMessageForCommand(String message) {

    }

    public void openCalendarActivity() {
        Intent intent = new Intent(this, CalendarActivity.class);
        startActivity(intent);
    }
}
