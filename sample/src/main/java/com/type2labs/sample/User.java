package com.type2labs.sample;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Thomas M. Klapwijk on 14/04/17.
 */

public class User {
    private String name;
    private String uId;
    private String age;

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    private String location;

    public User(String name, String location, String uId, String age) {
        this.name = name;
        this.location=location;
        this.uId = uId;
        this.age = age;
    }

    public User(){
        // Need an empty constructor so FireBase can serialize it.
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();

        result.put("name", name);
        result.put("location", location);
        result.put("uId", uId);
        result.put("age", age);

        return result;
    }
}
