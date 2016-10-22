package ru.a5five;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class userDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        Intent intent = getIntent();

        ((TextView) findViewById(R.id.userFirstnameText)).setText(intent.getStringExtra("firstName"));
        ((TextView) findViewById(R.id.userLastnameText)).setText(intent.getStringExtra("lastName"));

        ((TextView) findViewById(R.id.userId)).setText(String.valueOf(intent.getIntExtra("id", -1)));
        ((TextView) findViewById(R.id.userCompany)).setText(intent.getStringExtra("company"));
        ((TextView) findViewById(R.id.userAddress)).setText(intent.getStringExtra("address"));

        ((TextView) findViewById(R.id.userPhone)).setText(intent.getStringExtra("phone"));
        ((TextView) findViewById(R.id.userEmail)).setText(intent.getStringExtra("email"));

        ((TextView) findViewById(R.id.userAbout)).setText(intent.getStringExtra("about"));

        ImageView img = (ImageView) findViewById(R.id.userPhoto);

        Picasso.with(getApplicationContext())
                .load(intent.getStringExtra("photo"))
                .resize(100, 100)
                .placeholder(R.mipmap.ic_launcher)
                .into(img);
    }
}
