package com.type2labs.sample;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import co.dift.ui.SwipeToAction;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    UserAdapter adapter;
    SwipeToAction swipeToAction;

    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = (RecyclerView) findViewById(R.id.recycler);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);

        adapter = new UserAdapter(this.users);
        recyclerView.setAdapter(adapter);

        swipeToAction = new SwipeToAction(recyclerView, new SwipeToAction.SwipeListener<User>() {
            @Override
            public boolean swipeLeft(final User user) {
                final int pos = removeUser(user);
                displaySnackbar(user.getName() + " removed", "Undo", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        addUser(pos, user);
                    }
                });
                return true;
            }

            @Override
            public boolean swipeRight(User user) {
                displaySnackbar(user.getName() + " loved", null, null);
                return true;
            }

            @Override
            public void onClick(User user) {
                displaySnackbar(user.getName() + " clicked", null, null);
            }

            @Override
            public void onLongClick(User user) {
                displaySnackbar(user.getName() + " long clicked", null, null);
            }
        });

        populate();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.add_user:
                startActivity(new Intent(MainActivity.this, NewUserActivity.class));
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void populate() {
        User testUser = new User();
        testUser.setAge("21");
        testUser.setLocation("Keks");
        testUser.setName("banta");
        testUser.setuId("12312312");
        users.add(testUser);
        adapter.notifyItemChanged(users.size()-1);
    }

    private void displaySnackbar(String text, String actionName, View.OnClickListener action) {
        Snackbar snack = Snackbar.make(findViewById(android.R.id.content), text, Snackbar.LENGTH_LONG)
                .setAction(actionName, action);

        View v = snack.getView();
        v.setBackgroundColor(getResources().getColor(R.color.red));
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_text)).setTextColor(Color.WHITE);
        ((TextView) v.findViewById(android.support.design.R.id.snackbar_action)).setTextColor(Color.BLACK);

        snack.show();
    }

    private int removeUser(User User) {
        int pos = users.indexOf(User);
        users.remove(User);
        adapter.notifyItemRemoved(pos);
        return pos;
    }

    private void addUser(int pos, User User) {
        users.add(pos, User);
        adapter.notifyItemInserted(pos);
    }
}
