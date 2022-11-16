package es.unex.infinitetime.ui.stats;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;


import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentStatsBinding;


public class StatsFragment extends Fragment {

    private TextView todoTv, doingTv, doneTv;
    private PieChart pieChart;

    private FragmentStatsBinding binding;

    public StatsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.d("Depurando", "onCreateView stadisticas");


        binding = FragmentStatsBinding.inflate(inflater, container, false);

        todoTv = binding.TareasPorHacer;
        doingTv = binding.TareasEmpezadas;
        doneTv = binding.TareasHechas;

        pieChart = binding.piechart;
        setData();


        Log.d("Depurando", "onCreateView de list of stats");

        return binding.getRoot();
    }


    private void setData()
    {

        todoTv.setText(Integer.toString(40));
        doingTv.setText(Integer.toString(30));
        doneTv.setText(Integer.toString(5));

        pieChart.addPieSlice(
                new PieModel(
                        "Tareas por hacer",
                        Integer.parseInt(todoTv.getText().toString()),
                        Color.parseColor("#FFA726")));
        pieChart.addPieSlice(
                new PieModel(
                        "Tareas en progreso",
                        Integer.parseInt(doingTv.getText().toString()),
                        Color.parseColor("#66BB6A")));
        pieChart.addPieSlice(
                new PieModel(
                        "Tareas terminadas",
                        Integer.parseInt(doneTv.getText().toString()),
                        Color.parseColor("#EF5350")));


        // To animate the pie chart
        pieChart.startAnimation();
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d("Depurando", "onResume de list of stats");
    }






}