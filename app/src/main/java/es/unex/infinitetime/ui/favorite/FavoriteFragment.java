package es.unex.infinitetime.ui.favorite;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentFavoriteBinding;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.repository.PersistenceUser;
import es.unex.infinitetime.viewmodel.TaskViewModel;


public class FavoriteFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private FavoriteAdapter mAdapter;


    private FragmentFavoriteBinding binding;
    private TaskViewModel taskViewModel;

    public FavoriteFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentFavoriteBinding.inflate(inflater, container, false);
        taskViewModel = ViewModelProviders.of(getActivity()).get(TaskViewModel.class);

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
            taskViewModel.selectTask(item);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_favorite_to_editTaskFragment);
        }, taskViewModel);

        mRecyclerView.setAdapter(mAdapter);

        taskViewModel.getFavoriteTasks().observe(getViewLifecycleOwner(), tasks -> {
            mAdapter.load(tasks);
        });

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}