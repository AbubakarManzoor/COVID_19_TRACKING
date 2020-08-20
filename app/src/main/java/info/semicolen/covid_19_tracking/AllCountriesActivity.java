package info.semicolen.covid_19_tracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import info.semicolen.covid_19_tracking.Adapter.AllCountriesRecyclerViewAdapter;
import info.semicolen.covid_19_tracking.Models.CountyData;
import info.semicolen.covid_19_tracking.Repository.CovidRepository;

import android.os.Bundle;

import java.util.ArrayList;

public class AllCountriesActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    androidx.appcompat.widget.SearchView searchView;

    AllCountriesRecyclerViewAdapter adapter;
    CovidRepository covidRepository;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_countries);

        covidRepository = CovidRepository.getInstance();

        init();
        startingObservers();

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                settingUpRecyclerView(covidRepository.getCountryData().getValue() , newText);
                return false;
            }
        });

    }

    private void init() {
        searchView = (androidx.appcompat.widget.SearchView) findViewById(R.id.searchView);
        recyclerView = (RecyclerView) findViewById(R.id.countriesRecyclerView);
    }

    private void startingObservers() {
        covidRepository.getCountryData().observe(this, new Observer<ArrayList<CountyData>>() {
            @Override
            public void onChanged(ArrayList<CountyData> countyData) {
                settingUpRecyclerView(countyData, " ");
            }
        });
    }

    private void settingUpRecyclerView(ArrayList<CountyData> countyData, String type) {
        if(type.equals(" ")) {
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new AllCountriesRecyclerViewAdapter(getApplicationContext(), AllCountriesActivity.this, countyData);
            recyclerView.setAdapter(adapter);
        }else {
            ArrayList<CountyData> newData = new ArrayList<>();
            for (int i = 0; i < countyData.size();i++) {
                if(countyData.get(i).getCountry().toLowerCase().contains(type.toLowerCase())) {
                    newData.add(countyData.get(i));
                }
            }
            recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
            adapter = new AllCountriesRecyclerViewAdapter(getApplicationContext(), AllCountriesActivity.this, newData);
            recyclerView.setAdapter(adapter);
        }

    }

}