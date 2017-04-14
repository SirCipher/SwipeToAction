package com.type2labs.sample;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas M. Klapwijk on 14/04/17.
 */

public class NewUserActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = "NewUserActivity";
    private EditText mName, mAge, mLocation, mUid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initUI();
    }

    private void initUI() {
        setContentView(R.layout.activity_new_user);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mLocation = (EditText) findViewById(R.id.field_location);
        mName =(EditText) findViewById(R.id.field_name);
        mAge = (EditText) findViewById(R.id.field_age);
        mUid = (EditText) findViewById(R.id.field_uid);
    }

    @Override
    public void onClick(View v) {

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
                if(fieldsInvalid()) {
                    break;
                }
                checkAndAddPostToDatabase();
                backToActivity();
                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private void backToActivity(){
        Intent intent = new Intent(NewUserActivity.this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private boolean fieldsInvalid() {
        boolean error = false;

        EditText[] userFields = {mName, mAge, mLocation, mUid};

        for (EditText field : userFields) {
            if (field.getText().toString().equals("")) {
                error = true;
            }
        }

        if (error) {
            Toast.makeText(NewUserActivity.this, "Fields cannot be empty.", Toast.LENGTH_SHORT).show();
        }

        return error;
    }

    private void checkAndAddPostToDatabase() {
        DatabaseReference ref = FirebaseUtil.getBaseRef();
        Query usersQuery = ref.child(FirebaseUtil.getUsersPath()).orderByChild("uId").equalTo(mUid.getText().toString());

        usersQuery.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChildren()) {
                    Toast.makeText(NewUserActivity.this, "User already exists.", Toast.LENGTH_SHORT).show();
                } else {
                    addPostToDatabase();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

    private void addPostToDatabase() {
        final DatabaseReference databaseReference = FirebaseUtil.getBaseRef();

        String userName = mName.getText().toString();
        String userAge = mAge.getText().toString();
        String userLocation = mLocation.getText().toString();
        String userID= mUid.getText().toString();

        User user = new User();
        user.setName(userName);
        user.setAge(userAge);
        user.setLocation(userLocation);
        user.setuId(userID);

        String key = FirebaseUtil.getUsersRef().push().getKey();

        Map<String, Object> updatedUserData = new HashMap<>();

        updatedUserData.put(FirebaseUtil.getUsersPath() + "/" + key + "/", user.toMap());

        databaseReference.updateChildren(updatedUserData, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError error, DatabaseReference databaseReference) {
                if (error != null) {
                    Log.w(TAG, "Error posting response: " + error.getMessage());
                    Toast.makeText(NewUserActivity.this, "Error adding user.", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(NewUserActivity.this, "User added.", Toast.LENGTH_SHORT).show();

                    EditText[] userFields = {mName, mAge, mLocation, mUid};
                    for (EditText field : userFields) {
                        field.setText("");
                    }
                }
            }
        });
    }
}
