package ru.a5five;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;

/**
 * Created by root on 21.10.16.
 */

public class ListAdapter extends SimpleAdapter {
    private ArrayList<HashMap<String, Object>> data;
    private Context context;

    ListAdapter(Context context, ArrayList<HashMap<String, Object>> data, int res, String[] from, int[] to) {
        super(context, data, res, from, to);
        this.data = data;
        this.context = context;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(R.layout.row, null);
        }


        TextView nameTextView = (TextView) v.findViewById(R.id.userName);
        nameTextView.setText(data.get(position).get("name").toString());

        ImageView imgImageView = (ImageView) v.findViewById(R.id.userImg);

        Picasso.with(context)
                .load(data.get(position).get("img").toString())
                .resize(100, 100)
                .placeholder(R.mipmap.ic_launcher)
                .into(imgImageView);

        return v;
//        return super.getView(position, convertView, parent);
    }
}
