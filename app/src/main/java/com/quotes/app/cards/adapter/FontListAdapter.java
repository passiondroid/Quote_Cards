package com.quotes.app.cards.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.quotes.app.cards.R;
import com.quotes.app.cards.utils.CustomFontsLoader;

/**
 * Created by akhan on 3/9/2016.
 */
public class FontListAdapter extends RecyclerView.Adapter<FontListAdapter.ViewHolder> {

    public int[] fontList = {0,1,2,3,4};
    private Context mContext;
    // Define listener member variable
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public FontListAdapter(Context context) {
        this.mContext = context;
    }

    @Override
    public FontListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.font_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(FontListAdapter.ViewHolder viewHolder, int position) {
        // Set item views based on the data model
        TextView textView = viewHolder.textView;
        Typeface typeface = CustomFontsLoader.getTypeface(mContext, position);
        textView.setTypeface(typeface);

    }

    @Override
    public int getItemCount() {
        return fontList.length;
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public TextView textView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            textView = (TextView) itemView.findViewById(R.id.text);
            // Setup the click listener
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
}
