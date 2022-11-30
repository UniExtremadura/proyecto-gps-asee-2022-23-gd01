package es.unex.infinitetime.ui.cloud;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.snackbar.Snackbar;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.api.DownloadFromAPI;
import es.unex.infinitetime.api.UploadToAPI;
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
            AppExecutors.getInstance().networkIO().execute(new DownloadFromAPI());
            Snackbar.make(root, "Obteniendo los datos de la nube...", Snackbar.LENGTH_LONG).show();
        });

        btnSyncToCloud.setOnClickListener(v -> {
            AppExecutors.getInstance().networkIO().execute(new UploadToAPI());
            Snackbar.make(root, "Subiendo los datos a la nube...", Snackbar.LENGTH_LONG).show();
        });

        return root;
    }

}