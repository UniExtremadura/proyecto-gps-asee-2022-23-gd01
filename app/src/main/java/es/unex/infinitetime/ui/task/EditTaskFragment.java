package es.unex.infinitetime.ui.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentItemTaskBinding;
import es.unex.infinitetime.databinding.FragmentTaskBinding;
import es.unex.infinitetime.persistence.InfiniteDatabase;
import es.unex.infinitetime.persistence.Project;
import es.unex.infinitetime.persistence.Task;
import es.unex.infinitetime.persistence.TaskState;
import es.unex.infinitetime.persistence.User;
import es.unex.infinitetime.ui.login.PersistenceUser;

public class EditTaskFragment extends Fragment {

    private static final String ARG_PARAM1 = "task_id";

    private long taskId;

    private FragmentTaskBinding binding;

    public EditTaskFragment() {

    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentTaskBinding.inflate(inflater, container, false);
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

        taskId = getArguments().getLong(ARG_PARAM1);;

        // Borrar el siguiente código al integrar el caso de uso
        Task insertTask = new Task();
        insertTask.setId(taskId);
        insertTask.setName("Task 1");
        insertTask.setEffort(5);
        insertTask.setPriority(123);
        insertTask.setDescription("Description 1");
        insertTask.setDeadline(new Date());
        insertTask.setProjectId(4003);
        insertTask.setState(TaskState.DOING);
        insertTask.setUserId(PersistenceUser.getInstance().getUserId());

        User user = new User();
        user.setId(PersistenceUser.getInstance().getUserId());
        user.setUsername("username1234");
        user.setEmail("hola@gmail.com");
        user.setPassword("1234");

        Project project = new Project();
        project.setId(4003);
        project.setName("Project 1");
        project.setDescription("Description 1");
        project.setUserId(user.getId());

        AppExecutors.getInstance().diskIO().execute(() -> {
            if(InfiniteDatabase.getDatabase(getContext()).userDAO().getUser(user.getId()) == null){
                InfiniteDatabase.getDatabase(getContext()).userDAO().insert(user);
            }
            if(InfiniteDatabase.getDatabase(getContext()).projectDAO().getProject(project.getId()) == null){
                InfiniteDatabase.getDatabase(getContext()).projectDAO().insert(project);
            }
            if(InfiniteDatabase.getDatabase(getContext()).taskDAO().getTask(taskId) == null){
                InfiniteDatabase.getDatabase(getContext()).taskDAO().insert(insertTask);
            }
        });
        // Borrar el código anterior cuando se realice la integración

        AppExecutors.getInstance().diskIO().execute(() -> {
            Task task = InfiniteDatabase.getDatabase(getContext()).taskDAO().getTask(taskId);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

            AppExecutors.getInstance().mainThread().execute(() -> {
                binding.nameTask.setText(task.getName());
                binding.descriptionTask.setText(task.getDescription());
                binding.spinnerTaskEffort.setSelection(getSpinnerPosition(task.getEffort()));
                binding.priorityTask.setText(String.valueOf(task.getPriority()));
                binding.spinnerTaskState.setSelection(task.getState().ordinal());
                binding.dateTask.setText(sdf.format(task.getDeadline()));

                binding.cancelTaskBtn.setOnClickListener(v -> {
                    Navigation.findNavController(v).navigate(R.id.action_editTaskFragment_to_listTasksFragment);
                });
                binding.acceptTaskBtn.setOnClickListener(v -> {
                    task.setId(task.getId());
                    task.setName(binding.nameTask.getText().toString());
                    task.setDescription(binding.descriptionTask.getText().toString());
                    task.setEffort(binding.spinnerTaskEffort.getSelectedItemPosition());
                    task.setPriority(Long.parseLong(binding.priorityTask.getText().toString()));
                    //task.setState(binding.spinnerTaskState.getSelectedItemPosition());
                    String date= binding.dateTask.getText().toString();
                    Date date1 = null;

                    try {
                        date1 = new SimpleDateFormat("dd/MM/yyyy").parse(date);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                    task.setDeadline(date1);


                    AppExecutors.getInstance().diskIO().execute(()->{

                        InfiniteDatabase.getDatabase(getContext()).taskDAO().update(task);

                    });



                    Navigation.findNavController(v).navigate(R.id.action_editTaskFragment_to_listTasksFragment);
                });


            });

        });

    }
}