package com.quotes.app.cards.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.quotes.app.cards.R;
import com.quotes.app.cards.model.BackgroundImage;
import com.quotes.app.cards.utils.Util;

import java.util.List;

/**
 * Created by akhan on 3/3/2016.
 */
public class ImageListAdapter extends RecyclerView.Adapter<ImageListAdapter.ViewHolder> {

    private List<BackgroundImage> backgroundImageList;
    private Context mContext;
    // Define listener member variable
    private static OnItemClickListener listener;
    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }

    public ImageListAdapter(List<BackgroundImage> backgroundImageList,Context context) {
        this.backgroundImageList = backgroundImageList;
        this.mContext = context;
    }

    @Override
    public ImageListAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.image_list_item, parent, false);

        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageListAdapter.ViewHolder viewHolder, int position) {
        ImageView imageView = viewHolder.imageView;
        if(position!=0) {
            BackgroundImage backgroundImage = backgroundImageList.get(position);

            // Set item views based on the data model
            Bitmap bitmap = Util.decodeSampledBitmapFromResource(mContext.getResources(), backgroundImage.getImageId(), 100, 100);
            imageView.setImageBitmap(bitmap);
        }else if(position==0){
            imageView.setImageResource(R.drawable.ic_camera_enhance_black_24dp);
        }

    }

    @Override
    public int getItemCount() {
        return backgroundImageList.size();
    }

    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public static class ViewHolder extends RecyclerView.ViewHolder {
        // Your holder should contain a member variable
        // for any view that will be set as you render a row
        public ImageView imageView;

        // We also create a constructor that accepts the entire item row
        // and does the view lookups to find each subview
        public ViewHolder(final View itemView) {
            // Stores the itemView in a public final member variable that can be used
            // to access the context from any ViewHolder instance.
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.image);
            // Setup the click listener
            imageView.setOnClickListener(new View.OnClickListener() {
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
