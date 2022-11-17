package es.unex.infinitetime.ui.favorite;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentFavoriteBinding;
import es.unex.infinitetime.datosEjemplo.ExampleData;
import es.unex.infinitetime.persistence.InfiniteDatabase;
import es.unex.infinitetime.persistence.Project;
import es.unex.infinitetime.persistence.Task;
import es.unex.infinitetime.persistence.TaskState;
import es.unex.infinitetime.persistence.User;
import es.unex.infinitetime.ui.login.PersistenceUser;


public class FavoriteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FavoriteAdapter mAdapter;

    private FragmentFavoriteBinding binding;

    public FavoriteFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("Depurando", "onCreateView favorite");

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = binding.recyclerView;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new FavoriteAdapter(getActivity().getApplicationContext(), item -> {
            Bundle bundle = new Bundle();
            bundle.putLong("task_id", item.getId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_favorite_to_editTaskFragment, bundle);
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        loadItems();
        Log.d("Depurando", "onResume de favorite");
    }


    // Load stored ToDoItems
    private void loadItems() {

        // Conseguir el identificador del usuario actual
        // Obtener todas las tareas favoritas de ese usuario
        InfiniteDatabase db = InfiniteDatabase.getDatabase(getContext());

        Log.d("Depurando", "load de favorite");


        AppExecutors.getInstance().diskIO().execute(()->{

            User insertUser = new User();
            insertUser.setId(PersistenceUser.getInstance().getUserId());
            insertUser.setUsername("username1234");
            insertUser.setEmail("hola@gmail.com");
            insertUser.setPassword("1234");

            Task insertTask = new Task();
            insertTask.setName("Task 1");
            insertTask.setEffort(5);
            insertTask.setPriority(123);
            insertTask.setDescription("Description 1");
            insertTask.setDeadline(new Date());
            insertTask.setProjectId(4003);
            insertTask.setState(TaskState.DONE);
            insertTask.setUserId(insertUser.getId());

            Task insertTask2 = new Task();
            insertTask2.setName("Task 2");
            insertTask2.setEffort(5);
            insertTask2.setPriority(123);
            insertTask2.setDescription("Description 2");
            insertTask2.setDeadline(new Date());
            insertTask2.setProjectId(4003);
            insertTask2.setState(TaskState.DONE);
            insertTask2.setUserId(insertUser.getId());



            Project project = new Project();
            project.setId(4003);
            project.setName("Project 1");
            project.setDescription("Description 1");
            project.setUserId(insertUser.getId());


            if(InfiniteDatabase.getDatabase(getContext()).userDAO().getUser(insertUser.getUsername()) == null){
                InfiniteDatabase.getDatabase(getContext()).userDAO().insert(insertUser);
                Log.d("Depurando", "Insertada:" );
            }
            if(InfiniteDatabase.getDatabase(getContext()).projectDAO().getProject(project.getId()) == null){
                InfiniteDatabase.getDatabase(getContext()).projectDAO().insert(project);
            }
            if(InfiniteDatabase.getDatabase(getContext()).taskDAO().getTask(insertTask.getId()) == null){
                InfiniteDatabase.getDatabase(getContext()).taskDAO().insert(insertTask);
                Log.d("Depurando", "Insertada:" );
            }
            if(InfiniteDatabase.getDatabase(getContext()).taskDAO().getTask(insertTask2.getId()) == null){
                InfiniteDatabase.getDatabase(getContext()).taskDAO().insert(insertTask2);
                Log.d("Depurando", "Insertada:" );
            }

            List<Task> mItems = db.projectDAO().getTasks(4003);
            if(InfiniteDatabase.getDatabase(getContext()).userDAO().getFavorite(PersistenceUser.getInstance().getUserId(), mItems.get(0).getId()) == null){
                InfiniteDatabase.getDatabase(getContext()).taskDAO().addFavorite(PersistenceUser.getInstance().getUserId(), mItems.get(0).getId());
            }
            List<Task> m = db.userDAO().getFavoriteTasks(PersistenceUser.getInstance().getUserId());
            AppExecutors.getInstance().mainThread().execute(() -> {
                mAdapter.load(m);
                Log.d("Depurando", "Entra en favoriteFragment" +m.size());
            });

        });


    }



}