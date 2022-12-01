package es.unex.infinitetime.ui.tabs;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentTasksBinding;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskState;
import es.unex.infinitetime.viewmodel.TaskViewModel;


public class ListTasksStateFragment extends Fragment {

    public static final String ARG_PARAM1 = "taskState";
    public static final String ARG_PARAM2 = "projectId";

    private TaskState state;
    private long projectId;

    ListTasksStateAdapter adapter;
    private FragmentTasksBinding binding;
    private TaskViewModel taskViewModel;

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        Bundle bundle = getArguments();

        state = TaskState.values()[bundle.getInt("taskState")];

        binding = FragmentTasksBinding.inflate(inflater, container, false);
        taskViewModel = ViewModelProviders.of(getActivity()).get(TaskViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        RecyclerView mRecyclerView = binding.recyclerView;
        mRecyclerView.setHasFixedSize(true);
        LinearLayoutManager mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        adapter = new ListTasksStateAdapter(getActivity().getApplicationContext(), item -> {
            taskViewModel.selectTask(item);
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_listTasksFragment_to_editTaskFragment);
        }, taskViewModel);

        taskViewModel.getTasksProject().observe(getViewLifecycleOwner(), tasks -> {
            tasks.removeIf(task -> task.getState() != state);
            adapter.load(tasks);
        });

        mRecyclerView.setAdapter(adapter);
    }

    public static ListTasksStateFragment newInstance(TaskState taskState) {
        ListTasksStateFragment fragment = new ListTasksStateFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, taskState.ordinal());
        fragment.setArguments(args);
        return fragment;
    }
}