package com.example.kirsguo.listviewtest;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

/**
 * Created by Kirsguo on 2017/4/6.
 */

public class FruitAdapter extends ArrayAdapter<Fruit> {
//    layout的id
    private int resourceID;

    class ViewHolder
    {
        ImageView fruitImage;
        TextView fruitName;
    }


    public FruitAdapter(Context context, int textViewResourceID, List<Fruit> objects) {
        super(context, textViewResourceID, objects);
        resourceID = textViewResourceID;

    }

    @Override
    public View getView(int position,  View convertView,  ViewGroup parent) {

        Fruit fruit = getItem(position);

//        View view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);
        View view;
        ViewHolder viewHolder;
        if (convertView == null){
            view = LayoutInflater.from(getContext()).inflate(resourceID,parent,false);

            viewHolder = new ViewHolder();
            viewHolder.fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
            viewHolder.fruitName = (TextView)view.findViewById(R.id.fruit_name);
            view.setTag(viewHolder);
        } else{

            view = convertView;
            viewHolder = (ViewHolder) view.getTag();
        }
        viewHolder.fruitImage.setImageResource(fruit.getImageID());
        viewHolder.fruitName.setText(fruit.getName());

//        ImageView fruitImage = (ImageView) view.findViewById(R.id.fruit_image);
//        TextView fruitName = (TextView)view.findViewById(R.id.fruit_name);

//        fruitImage.setImageResource(fruit.getImageID());
//        fruitName.setText(fruit.getName());
        return view;
    }
}
