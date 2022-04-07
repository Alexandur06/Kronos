package com.example.kronosapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import com.example.kronosapp.calendar.WelcomeActivity;
import com.example.kronosapp.group_chat.Group;
import com.example.kronosapp.group_chat.GroupAdapter;
import com.example.kronosapp.group_chat.GroupChatActivity;
import com.example.kronosapp.private_chat.User;
import com.example.kronosapp.private_chat.UserAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    private RecyclerView groupRecyclerView;
    private ArrayList<Group> groupList;
    private GroupAdapter groupAdapter;

    private RecyclerView userRecyclerView;
    private ArrayList<User> userList;
    private UserAdapter userAdapter;

    private Button changeToGroupChat;

    private FirebaseAuth mAuth;
    private DatabaseReference mDbRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        mDbRef = FirebaseDatabase.getInstance("https://kronos-app-tues-default-rtdb.europe-west1.firebasedatabase.app").getReference();

        groupList = new ArrayList();
        groupAdapter = new GroupAdapter(this, groupList);

        groupRecyclerView = findViewById(R.id.groupRecyclerView);
        groupRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        groupRecyclerView.setAdapter(groupAdapter);

        mDbRef.child("groups").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                groupList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Group currentUser = postSnapshot.getValue(Group.class);
                    assert currentUser != null;
                    if(Objects.requireNonNull(mAuth.getCurrentUser()).getUid().equals(currentUser.getUid())){
                        currentUser.setName("To myself");
                    }
                    groupList.add(currentUser);
                }
                groupAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });

        userList = new ArrayList();
        userAdapter = new UserAdapter(this, userList);

        userRecyclerView = findViewById(R.id.userRecyclerView);
        userRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRecyclerView.setAdapter(userAdapter);

        mDbRef.child("user").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                userList.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    User currentUser = postSnapshot.getValue(User.class);
                    assert currentUser != null;
                    if(Objects.requireNonNull(mAuth.getCurrentUser()).getUid().equals(currentUser.getUid())){
                        currentUser.setName("To myself");
                    }
                    userList.add(currentUser);
                }
                userAdapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {}
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser == null){
            startActivity(new Intent(this, WelcomeActivity.class));
        }
    }

    public void openGroupChatActivity() {
        Intent intent = new Intent(this, GroupChatActivity.class);
        startActivity(intent);
    }
}