package es.unex.infinitetime.ui.stats;

import android.app.Application;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;

import es.unex.infinitetime.AppContainer;
import es.unex.infinitetime.InfiniteTime;
import es.unex.infinitetime.databinding.FragmentStatsBinding;
import es.unex.infinitetime.viewmodel.StatisticsViewModel;


public class StatsFragment extends Fragment {

    private TextView todoTv, doingTv, doneTv;
    private PieChart pieChart;

    private FragmentStatsBinding binding;
    private StatisticsViewModel viewModel;

    public StatsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentStatsBinding.inflate(inflater, container, false);

        todoTv = binding.TareasPorHacer;
        doingTv = binding.TareasEmpezadas;
        doneTv = binding.TareasHechas;

        pieChart = binding.piechart;
        Application application = getActivity().getApplication();
        AppContainer appContainer = ((InfiniteTime) application).getAppContainer();
        viewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(StatisticsViewModel.class);
        setData();

        return binding.getRoot();
    }

    private void setData() {

        viewModel.getTasksNumToDo().observe(getViewLifecycleOwner(), num -> {
            todoTv.setText(String.valueOf(num));
            pieChart.addPieSlice(
                    new PieModel(
                            "Tareas por hacer",
                            num,
                            Color.parseColor("#FFA726")));
        });

        viewModel.getTasksNumDoing().observe(getViewLifecycleOwner(), num -> {
            doingTv.setText(String.valueOf(num));
            pieChart.addPieSlice(
                    new PieModel(
                            "Tareas en progreso",
                            num,
                            Color.parseColor("#66BB6A")));
        });

        viewModel.getTasksNumDone().observe(getViewLifecycleOwner(), num -> {
            doneTv.setText(String.valueOf(num));
            pieChart.addPieSlice(
                    new PieModel(
                            "Tareas hechas",
                            num,
                            Color.parseColor("#EF5350")));
        });

        pieChart.startAnimation();
    }


    @Override
    public void onResume() {
        super.onResume();
    }






}