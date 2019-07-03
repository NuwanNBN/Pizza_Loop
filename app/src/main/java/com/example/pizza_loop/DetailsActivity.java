package com.example.pizza_loop;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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

public class DetailsActivity extends AppCompatActivity {

    //Pizza pizza;

    TextView name;
    TextView details;
    TextView price;
    ImageView image;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        name =findViewById(R.id.textname);
        details=findViewById(R.id.textdescription);
        image = findViewById(R.id.imageView);
        price = findViewById(R.id.textprice);
        Intent intent = getIntent();

        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/demo/findByPizzaId?id="+intent.getStringExtra("position");

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,
                null, new HTTPResponseListner(), new HTTPErrorListner());
        queue.add(request);
    }

    public void buy(){
        RequestQueue queue = Volley.newRequestQueue(this);
        String url = "http://10.0.2.2:8080/demo/addCart?name="+Dataholder.getName()+"&imageUrl="+Dataholder.getImageURL()+"&price="+Dataholder.getPrice();
        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url,null, null, null);
        queue.add(request);
    }
    public void shopping(View view) {

        buy();
        Toast toast = Toast.makeText(this, "Successful add this item to cart !", Toast.LENGTH_LONG);
        toast.show();
    }

    public void cart(View view) {
        Intent intent = new Intent(DetailsActivity.this,Pizza_cart.class);
        startActivity(intent);
    }

    class HTTPResponseListner implements Response.Listener<JSONArray> {
        @Override
        public void onResponse(JSONArray jsonArray) {
            for (int i = 0; i < jsonArray.length(); i++) {
                try {

                    JSONObject object = jsonArray.getJSONObject(i);
                    //pizza = new Pizza();
                    Dataholder.setPizzaId(Integer.parseInt(object.get("pizzaId").toString()));
                    Dataholder.setPrice(Float.parseFloat(object.get("price").toString()));
                    Dataholder.setName(object.get("name").toString());
                    Dataholder.setDetails(object.get("description").toString());
                    Dataholder.setImageURL(object.get("imageUrl").toString());

                    if (Dataholder.getName() != null){
                        name.setText(Dataholder.getName());
                        details.setText(Dataholder.getDetails());
                        price.setText("Rs."+Float.toString(Dataholder.getPrice()));
                        Picasso.get().load(Dataholder.getImageURL()).resize(500,400).into(image);
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private class HTTPErrorListner implements Response.ErrorListener {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }
}
