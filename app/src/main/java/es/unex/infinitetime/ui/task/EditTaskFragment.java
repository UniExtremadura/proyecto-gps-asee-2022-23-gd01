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
            task.setPriority(Long.parseLong(binding.priorityTask.getText().toString()));
            if(binding.spinnerTaskState.getSelectedItem().toString().equals("Por hacer")){
                task.setState(TaskState.TODO);
            }
            else if(binding.spinnerTaskState.getSelectedItem().toString().equals("En progreso")){
                task.setState(TaskState.DOING);
            }
            else if(binding.spinnerTaskState.getSelectedItem().toString().equals("Hechas")){
                task.setState(TaskState.DONE);
            }
            String date= binding.dateTask.getText().toString();
            Date dateUpdate = null;

            try {
                dateUpdate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            } catch (Exception e) {
                e.printStackTrace();
                Snackbar.make(v, "Error al parsear la fecha. El formato debe ser: dd/MM/yyyy", Snackbar.LENGTH_LONG).show();
            }
            task.setDeadline(dateUpdate);
            taskViewModel.updateTask(task);
            Navigation.findNavController(v).navigateUp();
        });

    }
}