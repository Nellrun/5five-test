package ru.a5five;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ActionMode;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

public class MainActivity extends AppCompatActivity {

    public interface GetUsersInterface {
        @GET("data.json")
        Call<Users> getUsers();
    }

    private String BASE_URL = "https://agency5-mobile-school.firebaseio.com/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = (ListView) findViewById(R.id.listView);

        Button[] b = {new Button(this), new Button(this)};
        ArrayAdapter<Button> aB = new ArrayAdapter<Button>(this, R.layout.support_simple_spinner_dropdown_item, b);
        lv.setAdapter(aB);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GetUsersInterface g = retrofit.create(GetUsersInterface.class);
        Call<Users> users = g.getUsers();

        users.enqueue(new Callback<Users>() {

            @Override
            public void onResponse(Call<Users> call, Response<Users> response) {
                try {

                    Toast.makeText(getApplicationContext(),response.body().getItems().get(0).getAbout(), Toast.LENGTH_LONG);
                } catch (Exception e) {

                }
                if (response.isSuccessful()) {
                    Toast.makeText(getApplicationContext(),"Данные успешно загружены", Toast.LENGTH_LONG);
                } else {
                    Toast.makeText(getApplicationContext(),"Не удалось загрузить список", Toast.LENGTH_LONG);
                }
            }

            @Override
            public void onFailure(Call<Users> call, Throwable t) {

            }
        });
    }
}
