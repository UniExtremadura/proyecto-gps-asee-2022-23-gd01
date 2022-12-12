package es.unex.infinitetime.ui.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.unex.infinitetime.databinding.FragmentTaskBinding;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskState;
import es.unex.infinitetime.viewmodel.TaskViewModel;
import es.unex.infinitetime.viewmodel.UserViewModel;


public class AddTaskFragment extends Fragment {

    private FragmentTaskBinding binding;
    private TaskViewModel taskViewModel;
    private UserViewModel userViewModel;

    public AddTaskFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        taskViewModel = ViewModelProviders.of(getActivity()).get(TaskViewModel.class);
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        binding = FragmentTaskBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.acceptTaskBtn.setOnClickListener(v -> {

            Task task = new Task();
            if(binding.nameTask.getText().toString().isEmpty()
            || binding.nameTask.getText().toString() == null
            || binding.nameTask.getText().toString() == ""){
                task.setName("Tarea sin nombre");
            }else{
                task.setName(binding.nameTask.getText().toString());
            }
            task.setDescription(binding.descriptionTask.getText().toString());
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
            if(binding.dateTask.getText().toString().equals("")){
                task.setDeadline(new Date());
            }
            else{
                try{
                    task.setDeadline(sdf.parse(binding.dateTask.getText().toString()));
                }
                catch (Exception e){
                    task.setDeadline(new Date());
                }
            }

            if(binding.spinnerTaskState.getSelectedItem().toString().equals("Por hacer")){
                task.setState(TaskState.TODO);
            } else if(binding.spinnerTaskState.getSelectedItem().toString().equals("En progreso")){
                task.setState(TaskState.DOING);
            } else if(binding.spinnerTaskState.getSelectedItem().toString().equals("Hechas")){
                task.setState(TaskState.DONE);
            }

            if(binding.priorityTask.getText().toString().equals("")){
                task.setPriority(0);
            }
            else {
                task.setPriority(Integer.parseInt(binding.priorityTask.getText().toString()));
            }

            task.setEffort(Long.parseLong(binding.spinnerTaskEffort.getSelectedItem().toString()));

            task.setUserId(userViewModel.getUserId());
            task.setProjectId(taskViewModel.getProjectId());
            taskViewModel.insertTask(task);

            Navigation.findNavController(v).navigateUp();
        });

        binding.cancelTaskBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }
}