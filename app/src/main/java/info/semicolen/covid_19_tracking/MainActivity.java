package info.semicolen.covid_19_tracking;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import info.semicolen.covid_19_tracking.Models.CountyData;
import info.semicolen.covid_19_tracking.Repository.CovidRepository;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    CustomAdapterForGridView customAdapterForGridView;
    GridView gridView;
    Button allCountreisButton;
    ArrayList<String> globalData = new ArrayList<>();

    CovidRepository covidRepository;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        covidRepository = CovidRepository.getInstance();
        covidRepository.startFetchingCovidData();

        init();
        setOnClickListener();
        startObservers();


    }

    private void startObservers() {
        covidRepository.getGlobalCountryData().observe(this, new Observer<CountyData>() {
            @Override
            public void onChanged(CountyData countyData) {
                if(countyData.getTotalConfirmed() != 0) {
                    globalData.add("Total Recovered: \n" + countyData.getTotalRecovered());
                    globalData.add("New Recovered: \n" + countyData.getNewRecovered());
                    globalData.add("Total Deaths: \n" + countyData.getTotalDeaths());
                    globalData.add("New Deaths: \n" + countyData.getNewDeaths());
                    globalData.add("Total Confirmed: \n" + countyData.getTotalConfirmed());
                    globalData.add("New Confirmed: \n" + countyData.getNewConfirmed());
                    gridView.setAdapter(customAdapterForGridView);
                }

            }
        });


    }


    private void init() {
        allCountreisButton = (Button) findViewById(R.id.allCountriesButton);
        customAdapterForGridView = new CustomAdapterForGridView();
        gridView = (GridView) findViewById(R.id.gridView);
    }

    private void setOnClickListener() {
        allCountreisButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(), AllCountriesActivity.class));
            }
        });
    }



    private class CustomAdapterForGridView extends BaseAdapter {

        @Override
        public int getCount() {
            return globalData.size();
        }

        @Override
        public Object getItem(int position) {
            return null;
        }

        @Override
        public long getItemId(int position) {
            return 0;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View vieww1 = getLayoutInflater().inflate(R.layout.custom_grid_view_row,null);
            TextView itemName = vieww1.findViewById(R.id.itemName);

            itemName.setText(globalData.get(position));

            return vieww1;
        }
    }
}