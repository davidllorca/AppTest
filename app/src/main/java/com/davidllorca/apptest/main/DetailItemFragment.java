package com.davidllorca.apptest.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidllorca.apptest.model.Item;
import com.davidllorca.apptest.R;

/**
 * Item detail.
 *
 * Created by David Llorca <davidllorcabaron@gmail.com> on 7/17/15.
 */
public class DetailItemFragment extends Fragment {

    public DetailItemFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_detail_item, container, false);
        // Get extras
        Bundle arguments = getArguments();
        Item item = null;
        if(arguments != null){
            item = arguments.getParcelable("item");
        }
        // Get data to show
        String text = item.getText();
        byte[] img = item.getImg();
        Bitmap mBitmap = BitmapFactory.decodeByteArray(img, 0, img.length);

        // References components
        TextView tv = (TextView) rootView.findViewById(R.id.text_detail);
        ImageView imageView = (ImageView) rootView.findViewById(R.id.img_detail);

        // Set item's info
        tv.setText(text);
        imageView.setImageBitmap(mBitmap);

        return rootView;
    }
}
