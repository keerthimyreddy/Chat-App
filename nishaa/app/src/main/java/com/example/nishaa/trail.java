package com.example.nishaa;

import android.app.Notification;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.google.firebase.database.FirebaseDatabase;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.appcompat.app.ActionBarDrawerToggle;

import android.view.MenuItem;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.DatabaseReference;
import com.squareup.picasso.Callback;
import com.squareup.picasso.MemoryPolicy;
import com.squareup.picasso.Picasso;
import com.example.nishaa.Common.Common;
import com.example.nishaa.Interface.ItemClickListener;
import com.example.nishaa.Model.Category;
import com.example.nishaa.Service.ListenOrder;
import com.example.nishaa.ViewHolder.MenuViewHolder;

import androidx.drawerlayout.widget.DrawerLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Menu;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import io.paperdb.Paper;

public class trail extends AppCompatActivity {

    FirebaseDatabase database;
    DatabaseReference category;
    TextView TextFullName;
    RecyclerView recycler_menu;
    RecyclerView.LayoutManager linearLayoutManager;
    FirebaseRecyclerAdapter<Category, MenuViewHolder> adapter;



    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.trail);

        database = FirebaseDatabase.getInstance();
        category = database.getReference("Category");
//        category.keepSynced(true);
        recycler_menu = findViewById(R.id.recycler_menu);
        recycler_menu.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recycler_menu.setLayoutManager(linearLayoutManager);

        if (Common.isConnectedToInternet(getBaseContext())) {
            loadMenu();

        } else {
            Toast.makeText(trail.this, "Please Check Internet Connection", Toast.LENGTH_LONG).show();
            return;
        }
        // register the service
        Intent service = new Intent(trail.this, ListenOrder.class);
        startService(service);

    }
    private void loadMenu() {
        FirebaseRecyclerOptions<Category> options =
                new FirebaseRecyclerOptions.Builder<Category>()
                        .setQuery(category, Category.class)
                        .build();

        adapter = new FirebaseRecyclerAdapter<Category, MenuViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull final MenuViewHolder menuViewHolder, int i,
                                            @NonNull final Category category) {

                Picasso.get().load(category.getImage()).placeholder(R.drawable.loading).fit().into(menuViewHolder.imageView);

                menuViewHolder.txtMenuName.setText(category.getName());
//                final Category clickItem = category;
                menuViewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        // get category id and send it to the new activity.
                        Intent foodIntent = new Intent(trail.this, FoodList.class);
                        // category id is key,we just get the key of this item
                        foodIntent.putExtra("CategoryId", adapter.getRef(position).getKey());
                        startActivity(foodIntent);
                    }
                });
            }


            @NonNull
            @Override
            public MenuViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.menu_item, parent, false);
                return new MenuViewHolder(view);
            }
        };
        adapter.startListening();
        adapter.notifyDataSetChanged();
        recycler_menu.setAdapter(adapter);


    }

}
