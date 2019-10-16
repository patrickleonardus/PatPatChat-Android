package com.example.patpatchat;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
public class ComposeFragment extends Fragment implements OnItemClickListener {


    private FirebaseDatabase database;
    private DatabaseReference reference;

    private View v;
    private Context mContext;

    private RecyclerViewAdapter adapter;

    private List<User> userList;


    public ComposeFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        v = inflater.inflate(R.layout.fragment_compose, container, false);
        initVar();
        return v;
    }

    private void initVar() {
        mContext = v.getContext();
        userList = new ArrayList<>();

        adapter = new RecyclerViewAdapter(userList, mContext, this);


        initRecyclerView();

        fetchUser();
    }

    private void fetchUser() {
        database = FirebaseDatabase.getInstance();
        reference = database.getReference().child("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                userList.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    User user = snapshot.getValue(User.class);
                    userList.add(user);
                }
                adapter.setUserList(userList);
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void initRecyclerView() {
        RecyclerView recyclerView = v.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onItemClick(int pos) {
        // Here we can manipulate data
        // Don't set logic in adapter
        User user = userList.get(pos);
        String nameTemp = user.getName();

        Intent intent = new Intent(getActivity(),ChatLogActivity.class);
        intent.putExtra("title",nameTemp);
        startActivity(intent);

    }
}
