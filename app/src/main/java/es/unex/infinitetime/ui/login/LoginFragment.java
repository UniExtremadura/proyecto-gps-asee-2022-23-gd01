package es.unex.infinitetime.ui.login;


import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import es.unex.infinitetime.AppContainer;
import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.DrawerLocker;
import es.unex.infinitetime.InfiniteTime;
import es.unex.infinitetime.R;
import es.unex.infinitetime.cryptography.Hash;
import es.unex.infinitetime.databinding.FragmentLoginBinding;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.viewmodel.UserViewModel;

public class LoginFragment extends Fragment {

    private FragmentLoginBinding binding;
    private Button registerButton;
    private UserViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        binding = FragmentLoginBinding.inflate(inflater, container, false);
        Application application = getActivity().getApplication();
        AppContainer appContainer = ((InfiniteTime) application).getAppContainer();
        viewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(UserViewModel.class);
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

        viewModel.closeSession();

        loginButton.setOnClickListener(v -> {
            String username = usernameEditText.getText().toString();
            String password = passwordEditText.getText().toString();
            if(!username.equals("") && !password.equals("")){
                AppExecutors.getInstance().diskIO().execute(() -> {
                    User user = viewModel.getUserByUsername(username);
                    AppExecutors.getInstance().mainThread().execute(() -> {
                        Hash hash = new Hash();
                        if(user == null || !hash.compareHash(user.getSalt(), password, user.getHash())) {
                            Snackbar.make(v, "Usuario no encontrado o la contraseña es incorrecta", Snackbar.LENGTH_LONG).show();
                        }
                        else{
                            viewModel.openSession(user.getId());
                            ((DrawerLocker) getActivity()).setDrawerEnabled(true);
                            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_favorite);
                        }
                    });
                });
            }
            else{
                Snackbar.make(v, "Rellena todos los campos", Snackbar.LENGTH_LONG).show();
            }
        });

        registerButton = binding.btnRegister;
        registerButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_loginFragment_to_registerFragment);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}