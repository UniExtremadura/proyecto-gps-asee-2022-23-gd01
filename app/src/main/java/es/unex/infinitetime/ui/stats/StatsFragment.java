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


import java.util.Date;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentStatsBinding;
import es.unex.infinitetime.persistence.InfiniteDatabase;
import es.unex.infinitetime.persistence.Project;
import es.unex.infinitetime.persistence.Task;
import es.unex.infinitetime.persistence.TaskState;
import es.unex.infinitetime.persistence.User;
import es.unex.infinitetime.ui.login.PersistenceUser;


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

        Task insertTask = new Task();
        insertTask.setId(112);
        insertTask.setName("Task 1");
        insertTask.setEffort(5);
        insertTask.setPriority(123);
        insertTask.setDescription("Description 1");
        insertTask.setDeadline(new Date());
        insertTask.setProjectId(4003);
        insertTask.setState(TaskState.DOING);
        insertTask.setUserId(PersistenceUser.getInstance().getUserId());

        User user = new User();
        user.setId(insertTask.getUserId());
        user.setUsername("username1234");
        user.setEmail("hola@gmail.com");
        user.setPassword("1234");

        Project project = new Project();
        project.setId(4003);
        project.setName("Project 1");
        project.setDescription("Description 1");
        project.setUserId(PersistenceUser.getInstance().getUserId());

        AppExecutors.getInstance().diskIO().execute(() -> {
            if(InfiniteDatabase.getDatabase(getContext()).userDAO().getUser(user.getUsername()) == null){
                InfiniteDatabase.getDatabase(getContext()).userDAO().insert(user);
            }
            if(InfiniteDatabase.getDatabase(getContext()).projectDAO().getProject(project.getId()) == null){
                InfiniteDatabase.getDatabase(getContext()).projectDAO().insert(project);
            }
            if(InfiniteDatabase.getDatabase(getContext()).taskDAO().getTask(insertTask.getId()) == null){
                InfiniteDatabase.getDatabase(getContext()).taskDAO().insert(insertTask);
                Log.d("Depurando", "Tarea Insertada:" );
            }
        });

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



        PersistenceUser persistenceUser = PersistenceUser.getInstance();
        InfiniteDatabase db = InfiniteDatabase.getDatabase(getContext());

        AppExecutors.getInstance().diskIO().execute(() ->{
            int numTodo= db.taskDAO().getTasksNum(PersistenceUser.getInstance().getUserId(), TaskState.TODO.ordinal());
            int numDoing= db.taskDAO().getTasksNum(PersistenceUser.getInstance().getUserId(), TaskState.DOING.ordinal());
            int numDone= db.taskDAO().getTasksNum(PersistenceUser.getInstance().getUserId(), TaskState.DONE.ordinal());

            Log.d("Depurando", "numTodo:" + numTodo);
            Log.d("Depurando", "numDoing:" + numDoing);
            Log.d("Depurando", "numDone:" + numDone);

//            numDone=2;
//            numDoing=4;
//            numTodo=3;

            todoTv.setText(Integer.toString(numTodo));
            doingTv.setText(Integer.toString(numDoing));
            doneTv.setText(Integer.toString(numDone));

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


        });

        pieChart.startAnimation();
    }


    @Override
    public void onResume() {
        super.onResume();

        Log.d("Depurando", "onResume de list of stats");
    }






}