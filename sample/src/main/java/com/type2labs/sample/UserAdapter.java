package com.type2labs.sample;

/**
 * Created by Thomas M. Klapwijk on 14/04/17.
 */

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.dift.ui.SwipeToAction;

class UserAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<User> users;

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return users.size();
    }

    public UserAdapter(List<User> users) {
        this.users = users;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        User user = users.get(position);
        UserViewHolder viewHolder = (UserAdapter.UserViewHolder) holder;

        viewHolder.nameView.setText(user.getName());
        viewHolder.uIdView.setText(user.getuId());
        viewHolder.ageView.setText(user.getAge());
        viewHolder.locationView.setText(user.getLocation());

        viewHolder.data = user;
    }

    /**
     * References to the views for each data item
     **/
    private class UserViewHolder extends SwipeToAction.ViewHolder<User> {
        TextView nameView;
        TextView locationView;
        TextView ageView;
        TextView uIdView;

        UserViewHolder(View v) {
            super(v);

            nameView = (TextView) v.findViewById(R.id.name);
            locationView=(TextView) v.findViewById(R.id.location);
            ageView=(TextView)  v.findViewById(R.id.age);
            uIdView=(TextView) v.findViewById(R.id.uid);
        }
    }
}