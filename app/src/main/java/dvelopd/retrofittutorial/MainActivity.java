package dvelopd.retrofittutorial;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.ybq.android.spinkit.SpinKitView;

import java.util.Collections;
import java.util.List;

import dvelopd.retrofittutorial.adapters.VerticalAdapter;
import dvelopd.retrofittutorial.api.ApiInterface;
import dvelopd.retrofittutorial.api.RetrofitApiClient;
import dvelopd.retrofittutorial.helpers.GridSpacingItemDecoration;
import dvelopd.retrofittutorial.helpers.NetworkCheckingClass;
import dvelopd.retrofittutorial.models.MovieResults;
import dvelopd.retrofittutorial.models.Search;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static dvelopd.retrofittutorial.Constants.API_KEY;

public class MainActivity extends AppCompatActivity implements SearchView.OnQueryTextListener {
    RecyclerView recyclerViewVertical;
    VerticalAdapter verticalAdapter;
    List<Search> dataList;
    SpinKitView spin_kit;
    RelativeLayout relativeLayout;
    private ApiInterface apiInterface;
    SearchView searchView;
    private String searchParam = "marvel";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(this);
        relativeLayout =  findViewById(R.id.activitymain);
        recyclerViewVertical = findViewById(R.id.vertical_recycler_view);
        recyclerViewVertical.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.VERTICAL, false));

        spin_kit = findViewById(R.id.spin_kit);
        apiInterface = RetrofitApiClient.getClient().create(ApiInterface.class);

        if (NetworkCheckingClass.isNetworkAvailable(this)) {
            spin_kit.setVisibility(View.VISIBLE);
            fetchData(searchParam);
        } else {
            spin_kit.setVisibility(View.GONE);
            Toast.makeText(this, "No internet Connection", Toast.LENGTH_LONG).show();
        }

    }

    private void fetchData(String s) {
        Call<MovieResults> call = apiInterface.getMovies(s, API_KEY);
        call.enqueue(new Callback<MovieResults>() {
            @Override
            public void onResponse(Call<MovieResults> call, Response<MovieResults> response) {
                MovieResults jsonData = response.body();
                dataList = jsonData.getSearch();

                verticalAdapter = new VerticalAdapter(MainActivity.this, dataList);
                recyclerViewVertical.setAdapter(verticalAdapter);
                spin_kit.setVisibility(View.GONE);
            }

            @Override
            public void onFailure(Call<MovieResults> call, Throwable t) {
                spin_kit.setVisibility(View.GONE);
                Toast.makeText(getApplicationContext(), t.toString(), Toast.LENGTH_LONG).show();
            }
        });
    }


    @Override
    public boolean onQueryTextSubmit(String s) {
        fetchData(s);
        verticalAdapter.notifyDataSetChanged();
        return false;
    }

    @Override
    public boolean onQueryTextChange(String s) {
        fetchData(s);
        verticalAdapter.notifyDataSetChanged();
        return false;
    }
}
