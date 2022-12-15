package es.unex.infinitetime.ui.cloud;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.google.android.material.snackbar.Snackbar;

import es.unex.infinitetime.AppContainer;
import es.unex.infinitetime.InfiniteTime;
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

        Application application = getActivity().getApplication();
        AppContainer appContainer = ((InfiniteTime) application).getAppContainer();

        btnSyncFromCloud.setOnClickListener(v -> {
            appContainer.repository.downloadFromAPI();
            Snackbar.make(root, "Obteniendo los datos de la nube...", Snackbar.LENGTH_LONG).show();
        });

        btnSyncToCloud.setOnClickListener(v -> {
            appContainer.repository.uploadToAPI();
            Snackbar.make(root, "Subiendo los datos a la nube...", Snackbar.LENGTH_LONG).show();
        });

        return root;
    }

}