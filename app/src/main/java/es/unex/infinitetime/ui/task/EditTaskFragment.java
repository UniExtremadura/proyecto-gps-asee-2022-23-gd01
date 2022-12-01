package es.unex.infinitetime.ui.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.databinding.FragmentTaskBinding;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskState;
import es.unex.infinitetime.viewmodel.TaskViewModel;
import es.unex.infinitetime.viewmodel.UserViewModel;

public class EditTaskFragment extends Fragment {

    private FragmentTaskBinding binding;

    private TaskViewModel taskViewModel;

    public EditTaskFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTaskBinding.inflate(inflater, container, false);
        taskViewModel = ViewModelProviders.of(getActivity()).get(TaskViewModel.class);
        return binding.getRoot();
    }

    private int getSpinnerPosition(long effort){
        for(int i = 0; i < binding.spinnerTaskEffort.getAdapter().getCount(); i++){
            if(binding.spinnerTaskEffort.getAdapter().getItem(i).equals(String.valueOf(effort))){
                return i;
            }
        }
        return -1;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskViewModel.getSelectedTask().observe(getViewLifecycleOwner(), task -> {
            binding.nameTask.setText(task.getName());
            binding.descriptionTask.setText(task.getDescription());
            binding.spinnerTaskEffort.setSelection(getSpinnerPosition(task.getEffort()));
            binding.priorityTask.setText(String.valueOf(task.getPriority()));
            binding.spinnerTaskState.setSelection(task.getState().ordinal());

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            try {
                binding.dateTask.setText(sdf.format(task.getDeadline()));
            }
            catch (Exception e){
                e.printStackTrace();
                Snackbar.make(binding.getRoot(), "Fecha introducida anteriormente no valida", Snackbar.LENGTH_LONG).show();
            }
        });

        binding.cancelTaskBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });

        binding.acceptTaskBtn.setOnClickListener(v -> {
            Task task = taskViewModel.getSelectedTask().getValue();
            task.setName(binding.nameTask.getText().toString());
            task.setDescription(binding.descriptionTask.getText().toString());
            task.setEffort(Long.parseLong(binding.spinnerTaskEffort.getSelectedItem().toString()));

            if(binding.priorityTask.getText().toString().equals("")){
                task.setPriority(0);
            }
            else {
                task.setPriority(Integer.parseInt(binding.priorityTask.getText().toString()));
            }

            if(binding.spinnerTaskState.getSelectedItem().toString().equals("Por hacer")){
                task.setState(TaskState.TODO);
            }
            else if(binding.spinnerTaskState.getSelectedItem().toString().equals("En progreso")){
                task.setState(TaskState.DOING);
            }
            else if(binding.spinnerTaskState.getSelectedItem().toString().equals("Hechas")){
                task.setState(TaskState.DONE);
            }

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
            taskViewModel.updateTask(task);
            Navigation.findNavController(v).navigateUp();
        });

    }
}