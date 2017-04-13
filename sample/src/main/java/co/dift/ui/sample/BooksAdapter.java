package co.dift.ui.sample;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import co.dift.ui.SwipeToAction;


public class BooksAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Book> items;


    /**
     * Constructor
     **/
    public BooksAdapter(List<Book> items) {
        this.items = items;
    }

    @Override
    public int getItemViewType(int position) {
        return 0;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_view, parent, false);

        return new BookViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        Book item = items.get(position);
        BookViewHolder vh = (BookViewHolder) holder;
        vh.titleView.setText(item.getTitle());
        vh.authorView.setText(item.getAuthor());
        vh.data = item;
    }

    /**
     * References to the views for each data item
     **/
    public class BookViewHolder extends SwipeToAction.ViewHolder<Book> {
        public TextView titleView;
        public TextView authorView;

        public BookViewHolder(View v) {
            super(v);

            titleView = (TextView) v.findViewById(R.id.title);
            authorView = (TextView) v.findViewById(R.id.author);
        }
    }
}