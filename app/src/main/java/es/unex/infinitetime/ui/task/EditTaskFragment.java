package es.unex.infinitetime.ui.task;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentItemTaskBinding;
import es.unex.infinitetime.databinding.FragmentTaskBinding;

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

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        taskId = getArguments().getLong(ARG_PARAM1);
        // Recuperar la tarea con el id taskId de la base de datos
        // y rellenar los campos del formulario con los datos de la tarea

        binding.acceptTaskBtn.setOnClickListener(v -> {
            // Actualizar la tarea con los datos del formulario
            Navigation.findNavController(v).navigateUp();
        });

        binding.cancelTaskBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();
        });
    }
}