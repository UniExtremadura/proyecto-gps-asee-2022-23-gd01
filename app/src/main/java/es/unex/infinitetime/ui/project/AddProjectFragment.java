package es.unex.infinitetime.ui.project;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentAddProjectBinding;

public class AddProjectFragment extends Fragment {

    FloatingActionButton confirmNewProjectBtn;
    FloatingActionButton cancelNewProjectBtn;

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

        confirmNewProjectBtn = binding.confirmAddProjectBtn;
        confirmNewProjectBtn.setOnClickListener(v -> {
            // Añadir proyecto a la base de datos
            Navigation.findNavController(v).navigate(R.id.action_addProjectFragment_to_projects);
        });

        cancelNewProjectBtn = binding.cancelAddProjectBtn;
        cancelNewProjectBtn.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_addProjectFragment_to_projects);
        });
    }
}