package es.unex.infinitetime.ui.tabs;

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
import es.unex.infinitetime.databinding.FragmentTasksBinding;
import es.unex.infinitetime.model.TaskState;
import es.unex.infinitetime.viewmodel.TaskViewModel;


public class ListTasksStateFragment extends Fragment {

    public static final String ARG_PARAM1 = "taskState";

    private TaskState state;

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
        Application application = getActivity().getApplication();
        AppContainer appContainer = ((InfiniteTime) application).getAppContainer();
        taskViewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(TaskViewModel.class);

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

        switch(state){
            case TODO:
                taskViewModel.getTasksToDo().observe(getViewLifecycleOwner(), tasks -> adapter.load(tasks));
                break;
            case DOING:
                taskViewModel.getTasksDoing().observe(getViewLifecycleOwner(), tasks -> adapter.load(tasks));
                break;
            case DONE:
                taskViewModel.getTasksDone().observe(getViewLifecycleOwner(), tasks -> adapter.load(tasks));
                break;
        }

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