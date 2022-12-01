package es.unex.infinitetime.ui.project;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentAddProjectBinding;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.repository.PersistenceUser;
import es.unex.infinitetime.viewmodel.ProjectViewModel;
import es.unex.infinitetime.viewmodel.UserViewModel;

public class AddProjectFragment extends Fragment {

    FloatingActionButton confirmNewProjectBtn;
    FloatingActionButton cancelNewProjectBtn;

    FragmentAddProjectBinding binding;
    ProjectViewModel projectViewModel;
    UserViewModel userViewModel;

    public AddProjectFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentAddProjectBinding.inflate(inflater, container, false);
        projectViewModel = ViewModelProviders.of(getActivity()).get(ProjectViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        confirmNewProjectBtn = binding.confirmAddProjectBtn;
        confirmNewProjectBtn.setOnClickListener(v -> {
            Project project = new Project();
            String project_name = binding.ProjectNameEdit.getText().toString();
            String project_description = binding.ProjectDescriptionEdit.getText().toString();

            if(!project_name.equals("")) {
                project.setUserId(userViewModel.getUserId());
                project.setName(project_name);
                project.setDescription(project_description);
                projectViewModel.insertProject(project);

                Snackbar.make(v, "Proyecto " + project.getName() + " creado", Snackbar.LENGTH_SHORT).show();
                Navigation.findNavController(v)
                        .navigate(R.id.action_addProjectFragment_to_projects);
            }
            else{
                Snackbar.make(v, "El nombre del proyecto no puede estar vacío", Snackbar.LENGTH_SHORT).show();
            }
        });

        cancelNewProjectBtn = binding.cancelAddProjectBtn;
        cancelNewProjectBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_addProjectFragment_to_projects);
        });
    }
}