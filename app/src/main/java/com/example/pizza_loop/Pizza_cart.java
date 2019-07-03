package com.example.pizza_loop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Pizza_cart extends AppCompatActivity {
    private List<Pizza> pizzaDetails = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_cart);

        listView = findViewById(R.id.listView1);

        ArrayAdapter<Pizza> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, android.R.id.text1, pizzaDetails);
        listView.setAdapter(adapter);

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/demo/cart";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new Pizza_cart.HTTPResponseListner(), new Pizza_cart.HTTPErrorListner());
        queue.add(request);
    }

    class HTTPResponseListner implements Response.Listener<JSONArray> {
        @Override
        public void onResponse(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {

                    JSONObject object = jsonArray.getJSONObject(i);
                    Pizza pizza = new Pizza();
                    pizza.setPizzaId(Integer.parseInt(object.get("pizzaId").toString()));
                    pizza.setPrice(Float.parseFloat(object.get("price").toString()));
                    pizza.setName(object.get("name").toString());
                    pizza.setImageURL(object.get("imageUrl").toString());
                    pizzaDetails.add(pizza);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            RequestQueue queue = Volley.newRequestQueue(Pizza_cart.this);
                            String pos = Integer.toString(position+1);
                            String url = "http://10.0.2.2:8080/demo/deleteByPizzaId?id="+pos;

                            JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, null, null);
                            queue.add(request);
                        }

                    });

                    ListView pizzaList = findViewById(R.id.listView1);
                    CustomAdapter listAdapter = new CustomAdapter(getApplicationContext(), R.layout.list_item, pizzaDetails);
                    pizzaList.setAdapter(listAdapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    class HTTPErrorListner implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {
        }
    }

    private class CustomAdapter extends ArrayAdapter<Pizza> {
        private List<Pizza> itemsList;

        CustomAdapter(Context context, int resource, List<Pizza> items) {
            super(context, resource, items);
            this.itemsList = items;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null) {
                convertView = getLayoutInflater().from(getContext()).inflate(R.layout.list_item, parent, false);
            }
            Pizza item = itemsList.get(position);
            TextView textView1 = convertView.findViewById(R.id.textViewName);
            TextView textView2 = convertView.findViewById(R.id.textViewPrice);
            ImageView imageView = convertView.findViewById(R.id.imageView);
            Picasso.get().load(item.getImageURL()).resize(500, 400).into(imageView);
            textView1.setText(item.getName());
            textView2.setText("Rs." + Float.toString(item.getPrice()));

            return convertView;
        }
    }

}

