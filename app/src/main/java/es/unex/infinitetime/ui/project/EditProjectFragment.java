package es.unex.infinitetime.ui.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unex.infinitetime.databinding.FragmentEditProjectBinding;
import es.unex.infinitetime.R;

/**
 */
public class EditProjectFragment extends Fragment {

    private long projectId;
    private static String ARG_PARAM1 = "project_id";
    private FragmentEditProjectBinding binding;

    FloatingActionButton confirmEditCheck;
    FloatingActionButton cancelEditCheck;

    public EditProjectFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentEditProjectBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        projectId = getArguments().getLong(ARG_PARAM1);
        // Recuperar el proyecto con el id projectId
        // Poner los datos del proyecto en los campos de texto

        confirmEditCheck = binding.checkEditBtn;
        cancelEditCheck = binding.cancelEditBtn;

        confirmEditCheck.setOnClickListener(v -> {
            // Implementar lógica de edición de proyecto
            Navigation.findNavController(v).navigate(R.id.action_editProjectFragment_to_projects);
        });

        cancelEditCheck.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_editProjectFragment_to_projects);
        });
    }
}
