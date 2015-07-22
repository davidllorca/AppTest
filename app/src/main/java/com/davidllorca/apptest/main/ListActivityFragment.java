package com.davidllorca.apptest.main;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.davidllorca.apptest.model.Item;
import com.davidllorca.apptest.model.ListAdapter;
import com.davidllorca.apptest.R;
import com.davidllorca.apptest.http.Request;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;


/**
 * Item's list
 *
 * Created by David Llorca <davidllorcabaron@gmail.com> on 7/17/15.
 */
public class ListActivityFragment extends Fragment {
    //Constants
    private static final String URL = "https://randomapi.com/api/?key=LMW0-SW97-ISC4-FF25&id=t60ldyb&results=20";

    private ArrayList<Item> items;

    public static ListActivityFragment newInstance(Bundle arguments) {
        ListActivityFragment fragment = new ListActivityFragment();
        if (arguments != null) {
            fragment.setArguments(arguments);
        }
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_list, container, false);


        items = new ArrayList<Item>();
        LoadData task = new LoadData();
        task.execute(URL);
        try {
            task.get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // View's references
        final ListView listView = (ListView) rootView.findViewById(R.id.list_items);
        ListAdapter adapter = new ListAdapter(getActivity(), items);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                launchDetailFragment((Item) items.get(position));
            }
        });

        return rootView;
    }

    /**
     * Async task class to get JSON data
     */
    public class LoadData extends AsyncTask<String, Void, Void> {

        @Override
        protected Void doInBackground(String... params) {
            Request request = new Request();
            String result = request.GET(params[0]);
            handleResult(result);
            return null;
        }

        private void handleResult(String result) {

            if (!result.equals("IO error")) try {
                JSONObject json = new JSONObject(result);
                JSONArray entities = json.getJSONArray("results");
                Log.i("n entities", entities.length() + "");
                for (int i = 0; i < entities.length(); i++) {
                    JSONObject entity = entities.getJSONObject(i);
                    JSONObject dataEntity = entity.getJSONObject("entity");

                    Item item = new Item();
                    //Get text
                    item.setText(dataEntity.getString("descritpion"));
                    //Get image
                    String urlImg = dataEntity.getString("thumbnail");
                    Bitmap mBitmap = getBitmapFromURL(urlImg);
                    if (mBitmap != null) {
                        ByteArrayOutputStream stream = new ByteArrayOutputStream();
                        mBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                        byte[] img = stream.toByteArray();
                        item.setImg(img);
                    }
                    //Add new object to list
                    items.add(item);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            else {
                showToast("Bad request");
            }
        }
    }

    /**
     * Get image from url.
     *
     * @param src image url.
     * @return Bitmap object.
     */
    private Bitmap getBitmapFromURL(String src) {
        try {
            URL url = new URL(src);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap mBitmap = BitmapFactory.decodeStream(input);
            return mBitmap;
        } catch (IOException e) {
            // Log exception
            return null;
        }
    }

    private void launchDetailFragment(Item item) {
        // Add item in Bundle object
        Bundle arguments = new Bundle();
        arguments.putParcelable("item", item);
        DetailItemFragment fragment = new DetailItemFragment();
        fragment.setArguments(arguments);
        //Show fragment with detail info
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.main_activity_content_frame, fragment, "detailsItem");
        ft.addToBackStack(null);
        ft.commit();
    }

    /**
     * Show toast info message.
     *
     * @param message
     */
    private void showToast(String message) {
        Toast.makeText(getActivity(), "Bad request", Toast.LENGTH_SHORT).show();
    }
}
