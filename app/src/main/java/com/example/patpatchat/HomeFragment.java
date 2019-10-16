package com.example.patpatchat;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.patpatchat.model.Messages;
import com.example.patpatchat.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


/**
 * A simple {@link Fragment} subclass.
 */
public class HomeFragment extends Fragment implements OnItemClickListener {

    DatabaseReference reference;
    DatabaseReference messageReference;
    DatabaseReference userReference;
    SharedPreferences sharedPreferences;

    private List<Messages> messageList;
    private List<User> userList;

    private View v;
    private Context mContext;

    private RecyclerViewAdapter adapter;

    private Messages messagesUser;


    public HomeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_home,container,false);

        initVar();

        return v;
    }

    public void initVar() {

        messageList = new ArrayList<>();
        userList = new ArrayList<>();

        observeUserMessage();
    }

    public void observeUserMessage() {

        sharedPreferences = this.getActivity().getSharedPreferences("SaveData", Context.MODE_PRIVATE);
        final String uid = sharedPreferences.getString("uiduser", null);

        reference = FirebaseDatabase.getInstance().getReference();

        reference.child("user-messages").child(uid).addValueEventListener(new ValueEventListener() {

            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    final String messageId = snapshot.getKey();

                    messageReference = FirebaseDatabase.getInstance().getReference().child("messages").child(messageId);
                    messageReference.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            messageList.clear();


                            //ini datanya udh masuk ke model belum ya?
                            Messages messages = dataSnapshot.getValue(Messages.class);
                            messageList.add(messages);


                            //ini mau kelompokin semua message user berdasarkan sendernya

                            messagesUser = new Messages();
                            String id = " ";

                            if(messagesUser.getFromId() == uid){
                                id = messagesUser.getToId();
                            }

                            else {
                                id = messagesUser.getFromId();
                            }

                            Log.e("asdf",id);

                            userReference = FirebaseDatabase.getInstance().getReference().child("users").child(id);
                            userReference.addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    User user = dataSnapshot.getValue(User.class);
                                    userList.add(user);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    @Override
    public void onItemClick(int pos) {

    }
}
