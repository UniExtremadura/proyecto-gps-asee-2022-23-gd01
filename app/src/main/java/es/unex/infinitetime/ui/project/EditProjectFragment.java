package es.unex.infinitetime.ui.project;

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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unex.infinitetime.AppContainer;
import es.unex.infinitetime.InfiniteTime;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentEditProjectBinding;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.viewmodel.ProjectViewModel;


public class EditProjectFragment extends Fragment {

    private static final String ARG_PARAM1 = "project_id";

    private long projectId;

    private FragmentEditProjectBinding binding;

    FloatingActionButton confirmEditCheck;
    FloatingActionButton cancelEditCheck;

    private ProjectViewModel projectViewModel;

    public EditProjectFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditProjectBinding.inflate(inflater, container, false);

        Application application = getActivity().getApplication();
        AppContainer appContainer = ((InfiniteTime) application).getAppContainer();
        projectViewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(ProjectViewModel.class);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        confirmEditCheck = binding.checkEditBtn;
        cancelEditCheck = binding.cancelEditBtn;

        projectViewModel.getSelectedProject().observe(getViewLifecycleOwner(), project -> {
            binding.ProjectNameEdit.setText(project.getName());
            binding.ProjectDescriptionEdit.setText(project.getDescription());
        });

        confirmEditCheck.setOnClickListener(v -> {
            String projectName = binding.ProjectNameEdit.getText().toString();
            String projectDescription = binding.ProjectDescriptionEdit.getText().toString();

            Project updateProject = projectViewModel.getSelectedProject().getValue();

            updateProject.setName(projectName);
            updateProject.setDescription(projectDescription);

            projectViewModel.updateProject(updateProject);
            Navigation.findNavController(v).navigate(R.id.action_editProjectFragment_to_projects);

        });

        cancelEditCheck.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_editProjectFragment_to_projects);
        });
    }
}
