package com.type2labs.sample;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

/**
 * Created by Thomas M. Klapwijk on 14/04/17.
 */

class FirebaseUtil {

    static DatabaseReference getBaseRef() {
        return FirebaseDatabase.getInstance().getReference();
    }

    static DatabaseReference getUsersRef() {
        return getBaseRef().child("users");
    }

    static String getUsersPath(){
        return "users/";
    }

}
