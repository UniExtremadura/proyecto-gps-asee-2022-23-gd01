package es.unex.infinitetime.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import es.unex.infinitetime.databinding.FragmentUserBinding;
import es.unex.infinitetime.ui.login.PersistenceUser;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextInputEditText textView = binding.textEditUsernameUser;
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        PersistenceUser persistenceUser = PersistenceUser.getInstance();
        long user_id = persistenceUser.getUserId();

        binding.btnUser.setOnClickListener(v -> {
            // Implementar la lógica de editar usuario
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}