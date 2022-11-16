package es.unex.infinitetime.ui.cloud;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import es.unex.infinitetime.databinding.FragmentCloudBinding;


public class CloudFragment extends Fragment {

    private FragmentCloudBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentCloudBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button btnSyncFromCloud = binding.btnSyncFromCloud;
        Button btnSyncToCloud = binding.btnSyncToCloud;

        btnSyncFromCloud.setOnClickListener(v -> {
            // Implementar obtener datos desde la nube
        });

        btnSyncToCloud.setOnClickListener(v -> {
            // Implementar subida de datos a la nube
        });

        return root;
    }

}