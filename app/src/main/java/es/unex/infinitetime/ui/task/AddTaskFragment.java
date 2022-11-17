package es.unex.infinitetime.ui.task;

import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentTaskBinding;
import es.unex.infinitetime.persistence.DateConverter;
import es.unex.infinitetime.persistence.InfiniteDatabase;
import es.unex.infinitetime.persistence.Project;
import es.unex.infinitetime.persistence.Task;
import es.unex.infinitetime.persistence.TaskState;
import es.unex.infinitetime.persistence.User;
import es.unex.infinitetime.ui.login.PersistenceUser;


public class AddTaskFragment extends Fragment {

    private FragmentTaskBinding binding;
    private Long mProjectId;

    public AddTaskFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //mProjectId = savedInstanceState.getLong("project_id");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.acceptTaskBtn.setOnClickListener(v -> {
            // Crear una nueva tarea con los datos del formulario
            // y añadirla a la base de datos
            InfiniteDatabase db = InfiniteDatabase.getDatabase(getContext());
            Task task = new Task();
            task.setName(binding.nameTask.getText().toString());
            task.setDescription(binding.descriptionTask.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                task.setDeadline(sdf.parse(binding.dateTask.getText().toString()));
            } catch (Exception e) {
                e.printStackTrace();
            }

            if(binding.spinnerTaskState.getSelectedItem().toString().equals("Por hacer")){
                task.setState(TaskState.TODO);
            } else if(binding.spinnerTaskState.getSelectedItem().toString().equals("En progreso")){
                task.setState(TaskState.DOING);
            } else if(binding.spinnerTaskState.getSelectedItem().toString().equals("Hechas")){
                task.setState(TaskState.DONE);
            }
            Log.d("Depurando - ANTES DE PARSEAR PRIORIDAD", binding.priorityTask.getText().toString());
            task.setPriority(Long.parseLong(binding.priorityTask.getText().toString()));
            Long debug1 = Long.parseLong(binding.priorityTask.getText().toString());
            Log.d("Depurando - DESPUES DE PARSEAR PRIORIDAD", debug1.toString());

            Log.d("Depurando - ANTES DE PARSEAR ESFUERZO", binding.spinnerTaskEffort.getSelectedItem().toString());
            task.setEffort(Long.parseLong(binding.spinnerTaskEffort.getSelectedItem().toString()));
            Long debug2 = Long.parseLong(binding.spinnerTaskEffort.getSelectedItem().toString());
            Log.d("Depurando - DESPUES DE PARSEAR ESFUERZO", debug2.toString());

            //usuario con persistence
            //projecto con el proyecto en que estes metido
            PersistenceUser persistenceUser = PersistenceUser.getInstance();
            task.setUserId(persistenceUser.getUserId());

            Long debug3 = getArguments().getLong("project_id");
            Log.d("Depurando - ID DEL PROYECTO", debug3.toString());
            task.setProjectId(getArguments().getLong("project_id"));

            //task.setProjectId(mProjectId);

            AppExecutors.getInstance().diskIO().execute(() -> {
                //InfiniteDatabase db = InfiniteDatabase.getDatabase(getContext());
                User u = new User();
                u.setId(task.getUserId());
                u.setUsername("usuario prueba");
                u.setEmail("email prueba");
                u.setPassword("password prueba");
                db.userDAO().insert(u);
                Project p = new Project();
                p.setId(task.getProjectId());
                p.setName("Proyecto de prueba");
                p.setDescription("Descripcion de prueba");
                p.setUserId(task.getUserId());
                db.projectDAO().insert(p);
                db.taskDAO().insert(task);
            });

            Navigation.findNavController(v).navigateUp();
        });

        binding.cancelTaskBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }
}