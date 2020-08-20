package info.semicolen.covid_19_tracking.Repository;

import android.provider.Settings;
import android.util.Log;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONArrayRequestListener;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import androidx.lifecycle.MutableLiveData;
import info.semicolen.covid_19_tracking.Models.CountyData;

public class CovidRepository {

    private static final String TAG = "CovidRepository";
    private static final String url = "https://api.covid19api.com/summary";

    private static CovidRepository instance;

    MutableLiveData<ArrayList<CountyData>> countryData = new MutableLiveData<>();
    ArrayList<CountyData> countryDataset = new ArrayList<>();
    MutableLiveData<CountyData> globalCountryData = new MutableLiveData<>();

    public static CovidRepository getInstance() {
        if(instance == null) {
            instance = new CovidRepository();
        }
        return instance;
    }


    public void startFetchingCovidData() {
        countryData.setValue(countryDataset);
        globalCountryData.setValue(new CountyData());
        fetchingData();
    }

    private void fetchingData() {
        Log.e(TAG, "fetchingData: " + url);
        AndroidNetworking.get(url+"")
                .setTag("test")
                .setPriority(Priority.HIGH)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONObject object = response.getJSONObject("Global");

                            int NNewConfirmed = object.getInt("NewConfirmed");
                            int NTotalConfirmed = object.getInt("TotalConfirmed");
                            int NNewDeaths = object.getInt("NewDeaths");
                            int NTotalDeaths = object.getInt("TotalDeaths");
                            int NNewRecovered = object.getInt("NewRecovered");
                            int NTotalRecovered = object.getInt("TotalRecovered");
                            CountyData globalData = new CountyData(NNewConfirmed, NTotalConfirmed, NNewDeaths, NTotalDeaths,
                                    NNewRecovered, NTotalRecovered);
                            globalCountryData.postValue(globalData);

                            JSONArray array = response.getJSONArray("Countries");

                            for (int i = 0;i<array.length();i++) {

                                JSONObject country = array.getJSONObject(i);

                                String Country = country.getString("Country");
                                Log.e(TAG, "onResponse: " + Country);
                                String CountryCode = country.getString("CountryCode");
                                String Slug = country.getString("Slug");
                                String Date = country.getString("Date");
                                int NewConfirmed = country.getInt("NewConfirmed");
                                int TotalConfirmed = country.getInt("TotalConfirmed");
                                int NewDeaths = country.getInt("NewDeaths");
                                int TotalDeaths = country.getInt("TotalDeaths");
                                int NewRecovered = country.getInt("NewRecovered");
                                int TotalRecovered = country.getInt("TotalRecovered");

                                CountyData countyData = new CountyData(Country, CountryCode, Slug, NewConfirmed,
                                                TotalConfirmed, NewDeaths, TotalDeaths, NewRecovered, TotalRecovered
                                                        , Date);
                                countryDataset.add(countyData);



                            }

                            countryData.postValue(countryDataset);

                        } catch (JSONException e) {
                            Log.e(TAG, "onResponse: " + e.getMessage());
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    public  MutableLiveData<ArrayList<CountyData>> getCountryData() {
        return countryData;
    }
    public MutableLiveData<CountyData> getGlobalCountryData() {
        return globalCountryData;
    }



}
