package es.unex.infinitetime.ui.project;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentListOfProjectsBinding;
import es.unex.infinitetime.viewmodel.ProjectViewModel;
import es.unex.infinitetime.viewmodel.SharedViewModel;
import es.unex.infinitetime.viewmodel.TaskViewModel;


public class ListOfProjectsFragment extends Fragment {


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListProjectAdapter mAdapter;
    private FloatingActionButton addNewProject;

    private FragmentListOfProjectsBinding binding;

    private ProjectViewModel projectViewModel;
    private TaskViewModel taskViewModel;
    private SharedViewModel sharedViewModel;

    public ListOfProjectsFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentListOfProjectsBinding.inflate(inflater, container, false);
        projectViewModel = ViewModelProviders.of(getActivity()).get(ProjectViewModel.class);
        taskViewModel = ViewModelProviders.of(getActivity()).get(TaskViewModel.class);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListProjectAdapter(getActivity().getApplicationContext(), item -> {
            taskViewModel.setProjectId(item.getId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_projects_to_listTasksFragment);
        }, projectViewModel, sharedViewModel);

        addNewProject = binding.AddProject;
        addNewProject.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_projects_to_addProjectFragment);
        });

        mRecyclerView.setAdapter(mAdapter);
        projectViewModel.getProjectsByUser().observe(getViewLifecycleOwner(), projects -> {
            mAdapter.load(projects);
        });
    }
}