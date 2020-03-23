package com.example.emurgency13.Home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.example.emurgency13.Maps.MainActivity;
import com.example.emurgency13.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {


    private RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        listView = (RecyclerView) findViewById(R.id.list_view);

        //FLOATING BUTTONS
        final FloatingActionButton fab1 = findViewById(R.id.action1);
        fab1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Calling 101");
                dialContactPhone("101");

            }
        });

        final FloatingActionButton fab2 = findViewById(R.id.action2);
        fab2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showToast("Redirecting to Maps Page");
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

    }

    public void showToast(String Message){
        Toast.makeText(this, Message,Toast.LENGTH_LONG).show();

    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", phoneNumber, null)));
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        return super.onCreateOptionsMenu(menu);
    }
}
