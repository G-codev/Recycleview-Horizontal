package com.gustiramadhan.rvactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.os.Bundle;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private RecyclerView rvPlayer;

    private PlayerAdapter adapter;
    private ArrayList<Player> players;
    private Gson gson;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvPlayer = findViewById(R.id.rv_player);
        adapter = new PlayerAdapter(this);
        players = new ArrayList<>();
        gson = new Gson();

        ambilData();

        LinearLayoutManager lm = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
     GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
    StaggeredGridLayoutManager lm3 = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.HORIZONTAL);
        DividerItemDecoration divider = new DividerItemDecoration(this, lm.getOrientation());
        rvPlayer.setLayoutManager(gridLayoutManager);
        rvPlayer.setAdapter(adapter);
        rvPlayer.addItemDecoration(divider);

        adapter.setListener(new OnClickListener() {
            @Override
            public void aksiKlik(int Position) {
                Intent intent = new Intent(MainActivity.this, DetailActivity.class);
                intent.putExtra("idPlayer", players.get(Position).getIdPlayer());
                startActivity(intent);

            }
        });


    }

    public void ambilData(){
//        meminta request dengan vooley
//        jika request berhasil, tampilkan pada recycle view via adapter
        RequestQueue queue = Volley.newRequestQueue(this);
        String url ="https://www.thesportsdb.com/api/v1/json/1/searchplayers.php?t=Arsenal";
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //ambil data dari response -> json -> ar
    PlayerResult result =gson.fromJson(response,PlayerResult.class);
    players = result.getPlayer();
//    tampilkan data via adapter
                adapter.setPlayers(players);

            }

        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        );
        queue.add(stringRequest);
    }
}
