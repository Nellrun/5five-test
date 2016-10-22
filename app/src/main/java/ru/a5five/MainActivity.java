package ru.a5five;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    private static Users usersList;

    public interface GetUsersInterface {
        @GET("data.json")
        Call<Users> getUsers();
    }

    private String BASE_URL = "https://agency5-mobile-school.firebaseio.com/";

    private void fillActivity() {
        ListView lv = (ListView) findViewById(R.id.userListView);

        ArrayList<HashMap<String, Object>> al = new ArrayList<HashMap<String, Object>>();

        HashMap<String, Object> elem;
        for (Item human: usersList.getItems()) {
            elem = new HashMap<String, Object>();
            elem.put("img", human.getPhoto());
            elem.put("name", human.getFirstName() + " " + human.getLastName());
            al.add(elem);
        }

        ListAdapter sa = new ListAdapter(getApplicationContext(), al, R.layout.row,
                new String[] {"img", "name"},
                new int[] {R.id.userImg, R.id.userName});

        lv.setAdapter(sa);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (usersList != null) {
            fillActivity();
            return;
        }

        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        ListView lv = (ListView) findViewById(R.id.userListView);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Item human = usersList.getItems().get(position);

                Intent intent = new Intent(MainActivity.this, userDetailActivity.class);

                intent.putExtra("firstName", human.getFirstName());
                intent.putExtra("lastName", human.getLastName());

                intent.putExtra("id", human.getId());
                intent.putExtra("photo", human.getPhoto());
                intent.putExtra("company", human.getCompany());
                intent.putExtra("address", human.getAddress());

                intent.putExtra("phone", human.getPhone());
                intent.putExtra("email", human.getEmail());

                intent.putExtra("about", human.getAbout());

                startActivity(intent);

            }
        });


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetUsersInterface g = retrofit.create(GetUsersInterface.class);
        Call<Users> users = g.getUsers();


        Toast.makeText(getApplicationContext(), "Получение списка...", Toast.LENGTH_LONG).show();


        users.enqueue(new Callback<Users>() {

                @Override
            public void onResponse(Call<Users> call, Response<Users> response) {

                findViewById(R.id.progressBar).setVisibility(View.INVISIBLE);

                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Данные успешно загружены", Toast.LENGTH_LONG).show();

                    usersList = response.body();
                    fillActivity();

                } else {
                    Toast.makeText(getApplicationContext(),"Не удалось загрузить список", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {
                Toast.makeText(getApplicationContext(),"Список не доступен", Toast.LENGTH_LONG).show();
            }

        });
    }
}
