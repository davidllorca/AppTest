package com.davidllorca.apptest.model;

import android.content.Context;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.davidllorca.apptest.R;

import java.util.List;

/**
 * Listview adapter model.
 *
 * Created by David Llorca <davidllorcabaron@gmail.com> on 7/17/15.
 */
public class ListAdapter extends BaseAdapter {

    private final List<Item> objects;
    private final LayoutInflater layoutInflater;

    public ListAdapter(Context context, List<Item> objects) {
        super();
        this.objects = objects;
        this.layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    static class ViewHolder {
        ImageView img;
        TextView text;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = new ViewHolder();

        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);

            holder.img = (ImageView) convertView
                    .findViewById(R.id.img);
            holder.text = (TextView) convertView
                    .findViewById(R.id.description);

            // Asociamos la estructura del layout con con la vista que retornaremos en el método
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        // Asignamos los datos a los componentes del holder
        if (objects.get(position).getImg() != null) {
            holder.img.setImageBitmap(BitmapFactory.decodeByteArray(
                    objects.get(position).getImg(), 0, objects.get(position)
                            .getImg().length));
        }
        holder.text.setText(""
                + objects.get(position).getText().toString());
        Log.i("item text", objects.get(position).getText());

        return convertView;
    }
}
