package info.semicolen.covid_19_tracking.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import info.semicolen.covid_19_tracking.Models.CountyData;
import info.semicolen.covid_19_tracking.R;

public class AllCountriesRecyclerViewAdapter extends RecyclerView.Adapter<AllCountriesRecyclerViewAdapter.ViewHolder> {


    Context context;
    Activity activity;
    ArrayList<CountyData> contries;

    public AllCountriesRecyclerViewAdapter(Context context, Activity activity, ArrayList<CountyData> contries) {
        this.context = context;
        this.activity = activity;
        this.contries = contries;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View view = inflater.inflate(R.layout.custom_countries_list, viewGroup, false);
        return new ViewHolder(view);
    }


    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {

        holder.newConfirmed.setText("N Confirmed: " + contries.get(position).getNewConfirmed());
        holder.newDeaths.setText("N Deaths: " + contries.get(position).getNewDeaths());
        holder.newRecovered.setText("N Recoverd: " + contries.get(position).getNewRecovered());
        holder.totalConfirmed.setText("T Confirmed: " + contries.get(position).getTotalConfirmed());
        holder.totalDeaths.setText("T Deaths: " + contries.get(position).getTotalDeaths());
        holder.totalRecovered.setText("T Recovered: " + contries.get(position).getTotalRecovered());
        holder.date.setText("Date: " + contries.get(position).getDate());
        holder.countryName.setText(contries.get(position).getCountry());


    }

    @Override
    public int getItemCount() {
        return contries.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView newConfirmed, totalConfirmed, newDeaths, totalDeaths, newRecovered, totalRecovered, date;
        TextView countryName;
        ImageView image;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            newConfirmed = itemView.findViewById(R.id.newConfirmed);
            totalConfirmed = itemView.findViewById(R.id.totalConfirmed);
            newDeaths = itemView.findViewById(R.id.newDeaths);
            totalDeaths = itemView.findViewById(R.id.totalDeaths);
            newRecovered = itemView.findViewById(R.id.newRecoverd);
            totalRecovered = itemView.findViewById(R.id.totalRecovered);
            date = itemView.findViewById(R.id.date);
            countryName = itemView.findViewById(R.id.countryName);


        }


    }

}
