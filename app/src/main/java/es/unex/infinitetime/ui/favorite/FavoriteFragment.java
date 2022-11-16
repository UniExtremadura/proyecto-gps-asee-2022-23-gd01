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

import java.util.List;

import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentFavoriteBinding;
import es.unex.infinitetime.datosEjemplo.ExampleData;
import es.unex.infinitetime.persistence.Task;



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

        binding.addTaskFavorite.setOnClickListener(v -> {
            // Crear una nueva tarea con los datos del formulario
            // y añadirla a la base de datos
            Navigation.findNavController(v).navigate(R.id.action_favorite_to_addTaskFragment);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter.getItemCount() == 0)
            loadItems();
        Log.d("Depurando", "onResume de favorite");
    }


    // Load stored ToDoItems
    private void loadItems() {
        ExampleData ed = new ExampleData();
        List<Task> mItems = ed.getTasksList();
        // Conseguir el identificador del usuario actual
        // Obtener todas las tareas favoritas de ese usuario
        Log.d("Depurando", "load de favorite");

        mAdapter.load(mItems);
    }



}