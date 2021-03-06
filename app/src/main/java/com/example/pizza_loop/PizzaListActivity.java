package com.example.pizza_loop;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

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

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class PizzaListActivity extends AppCompatActivity {
    private List<Pizza> pizzaDetails = new ArrayList<>();

    ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pizza_list);

        listView = findViewById(R.id.listView1);

        ArrayAdapter<Pizza> adapter=new ArrayAdapter<>(this, android.R.layout.simple_list_item_2, android.R.id.text1, pizzaDetails);
        listView.setAdapter(adapter);

        /*listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String pos = Long.toString(id +1);
                Intent intent = new Intent(PizzaListActivity.this, DetailsActivity.class);
                intent.putExtra("position",pos);
                startActivity(intent);
                      *//*Toast.makeText(this,pos,Toast.LENGTH_SHORT).show();*//*
            }

        });*/

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/demo/all";

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new HTTPResponseListner(), new HTTPErrorListner());
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
                            String pos = Integer.toString(position+1);
                            Intent intent = new Intent(PizzaListActivity.this, DetailsActivity.class);
                            intent.putExtra("position",pos);
                            startActivity(intent);
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
            if(convertView == null) {
                convertView = getLayoutInflater().from(getContext()).inflate(R.layout.list_item, parent, false);
            }
            Pizza item = itemsList.get(position);
            TextView textView1 =  convertView.findViewById(R.id.textViewName);
            TextView textView2 =  convertView.findViewById(R.id.textViewPrice);
            ImageView imageView = convertView.findViewById(R.id.imageView);
            Picasso.get().load(item.getImageURL()).resize(500,400).into(imageView);
            textView1.setText(item.getName());
            textView2.setText("Rs." + Float.toString(item.getPrice()));
            /*convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Pizza send = (Pizza) v.getTag();

                    String id = Integer.toString(send.getPizzaId());

                    Intent intent = new Intent(PizzaListActivity.this, DetailsActivity.class);
                    intent.putExtra("id",id);
                    startActivity(intent);
                }
            });*/

            return convertView;
        }

           /* public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String Templistview = pizzaDetails.get(position).toString();
                *//*Intent intent = new Intent(PizzaListActivity.this, DetailsActivity.class);
                intent.putExtra("Listviewclickvalue",Templistview);
                startActivity(intent);*//*
                Toast toast = (Toast) Toast.makeText(contex,Templistview,Toast.LENGTH_SHORT);
                toast.show();
            }
        });*/
    }

}
