package com.example.hack.jsonview;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.InputStream;
import java.util.ArrayList;

/**
 * Created by HackPC on 3/2/2016.
 */
public class ListRowAdapter extends ArrayAdapter<ListRow> {
     ArrayList<ListRow> ListRowV;
     LayoutInflater vi;
     int Resource;
     ViewHolder holder;


    public ListRowAdapter(Context context, int resource, ArrayList<ListRow> objects) {
        super(context, resource, objects);
        vi = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        Resource = resource;
        ListRowV = objects;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // convert view = design
        View v = convertView;
        if (v == null) {
            holder = new ViewHolder();
            v = vi.inflate(Resource, null);
            holder.imageview = (ImageView) v.findViewById(R.id.ivImage);
            holder.tvTitle = (TextView)v.findViewById(R.id.tvTitle);
            holder.tvTime = (TextView) v.findViewById(R.id.tvTime);
            holder.tvDes = (TextView) v.findViewById(R.id.tvDes);
            v.setTag(holder);
        } else {
            holder = (ViewHolder) v.getTag();
        }
        holder.imageview.setImageResource(R.drawable.ic_launcher);
        String simage = ListRowV.get(position).getSin_image();
        try {
            byte[] rawImage = Base64.decode(simage, Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(rawImage, 0, rawImage.length);

            holder.imageview.setImageBitmap(bmp);

        } catch (Exception e) {
            e.getMessage();

        }
        holder.tvTitle.setText(ListRowV.get(position).getTitle());
        holder.tvTime.setText(ListRowV.get(position).getTime());
        holder.tvDes.setText(ListRowV.get(position).getDes());
        return v;
    }



    static class ViewHolder {
        public ImageView imageview;
        public TextView tvTitle;
        public TextView tvTime;
        public TextView tvDes;
    }


}
