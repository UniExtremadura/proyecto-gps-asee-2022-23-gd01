package es.unex.infinitetime.ui.favorite;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.infinitetime.AppContainer;
import es.unex.infinitetime.InfiniteTime;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentFavoriteBinding;
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

        Application application = getActivity().getApplication();
        AppContainer appContainer = ((InfiniteTime) application).getAppContainer();
        taskViewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(TaskViewModel.class);

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

        taskViewModel.getFavoriteTasks().observe(getViewLifecycleOwner(), tasks -> mAdapter.load(tasks));

    }

    @Override
    public void onResume() {
        super.onResume();
    }
}