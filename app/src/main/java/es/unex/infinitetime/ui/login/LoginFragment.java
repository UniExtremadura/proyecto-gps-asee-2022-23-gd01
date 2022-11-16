package es.unex.infinitetime.ui.login;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import es.unex.infinitetime.DrawerLocker;
import es.unex.infinitetime.databinding.FragmentLoginBinding;

import es.unex.infinitetime.R;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private Button registerButton;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        return binding.getRoot();

    }

    public static LoginFragment newInstance() {
        return new LoginFragment();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final TextInputEditText usernameEditText = binding.textEditUsernameLogin;
        final TextInputEditText passwordEditText = binding.textEditPasswordLogin;
        final Button loginButton = binding.btnLogin;

        PersistenceUser persistenceUser = PersistenceUser.getInstance();
        persistenceUser.deleteUserId();

        loginButton.setOnClickListener(v -> {
            // Crear la lógica del login

            ((DrawerLocker) getActivity()).setDrawerEnabled(true);
            persistenceUser.setUserId(12);
            persistenceUser.saveUserId();
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_favorite);
        });

        registerButton = binding.btnRegister;
        registerButton.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("username", usernameEditText.getText().toString());
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment, bundle);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}