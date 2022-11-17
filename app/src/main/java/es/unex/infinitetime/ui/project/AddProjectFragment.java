package es.unex.infinitetime.ui.project;

import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentAddProjectBinding;
import es.unex.infinitetime.persistence.InfiniteDatabase;
import es.unex.infinitetime.persistence.Project;
import es.unex.infinitetime.persistence.User;
import es.unex.infinitetime.ui.login.PersistenceUser;

public class AddProjectFragment extends Fragment {

    FloatingActionButton confirmNewProjectBtn;
    FloatingActionButton cancelNewProjectBtn;


    InfiniteDatabase db = InfiniteDatabase.getDatabase(getContext());
    FragmentAddProjectBinding binding;

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
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        User user = new User();
        user.setEmail("TestUser1");
        user.setUsername("TestUser1");
        user.setPassword("1111");
        user.setId(PersistenceUser.getInstance().getUserId());

        AppExecutors.getInstance().diskIO().execute(() -> {
            if(db.userDAO().getUser(user.getUsername()) != null) {
                Snackbar.make(view, "El usuario ya existe", Snackbar.LENGTH_LONG).show();
            }
            else {
                db.userDAO().insert(user);
            }
        });


        confirmNewProjectBtn = binding.confirmAddProjectBtn;
        confirmNewProjectBtn.setOnClickListener(v -> {
            // Añadir proyecto a la base de datos
            Project project = new Project();
            String project_name = binding.ProjectNameEdit.getText().toString();
            String project_description = binding.ProjectDescription.getText().toString();
            PersistenceUser persistenceUser = PersistenceUser.getInstance();

            if(!project_name.equals("")){
                AppExecutors.getInstance().diskIO().execute(() -> {
                    if(db.projectDAO().getProject(project.getId()) != null) {
                        Snackbar.make(v, "El proyecto ya existe",Snackbar.LENGTH_SHORT).show();
                    }
                    else {
                        //project.setUserId(persistenceUser.getUserId());
                        project.setUserId(PersistenceUser.getInstance().getUserId());
                        project.setName(project_name);
                        project.setDescription(project_description);
                        db.projectDAO().insert(project);

                        Snackbar.make(v, "Proyecto -"+ project.getName()+"- creado", Snackbar.LENGTH_SHORT).show();

                        Bundle bundle = new Bundle();
                        bundle.putString("project_name", project.getName());
                        bundle.putString("description", project.getDescription());
                        AppExecutors.getInstance().mainThread().execute(() -> Navigation
                                .findNavController(v)
                                .navigate(R.id.action_addProjectFragment_to_projects, bundle));
                    }
                });
            }
        });

        cancelNewProjectBtn = binding.cancelAddProjectBtn;
        cancelNewProjectBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_addProjectFragment_to_projects);
        });
    }
}