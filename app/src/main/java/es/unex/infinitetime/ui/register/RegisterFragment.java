package es.unex.infinitetime.ui.register;

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
import es.unex.infinitetime.InfiniteTime;
import es.unex.infinitetime.R;
import es.unex.infinitetime.cryptography.Hash;
import es.unex.infinitetime.databinding.FragmentRegisterBinding;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.viewmodel.UserViewModel;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;
    private UserViewModel viewModel;

    public RegisterFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentRegisterBinding.inflate(inflater, container, false);

        Application application = getActivity().getApplication();
        AppContainer appContainer = ((InfiniteTime) application).getAppContainer();
        viewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(UserViewModel.class);
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Button btnRegister = binding.btnRegister;

        TextInputEditText tvUsername = binding.textEditUsernameRegister;
        TextInputEditText tvPassword = binding.textEditPasswordRegister;
        TextInputEditText tvEmail = binding.textEditEmailRegister;

        btnRegister.setOnClickListener(v1 -> {

            User user = new User();

            user.setUsername(tvUsername.getText().toString());
            user.setEmail(tvEmail.getText().toString());

            String password = tvPassword.getText().toString();

            if(!user.getUsername().equals("") && !password.equals("") && !user.getEmail().equals("")) {
                AppExecutors.getInstance().diskIO().execute(() -> {
                    boolean exists = viewModel.usernameExists(user.getUsername());
                    AppExecutors.getInstance().mainThread().execute(() -> {
                        if(exists) {
                            Snackbar.make(v1, "El usuario ya existe", Snackbar.LENGTH_LONG).show();
                        }
                        else {
                            Hash hash = new Hash();
                            byte[] salt = hash.getSalt();
                            byte[] hashPassword = hash.getHash(salt, password);
                            user.setHash(hashPassword);
                            user.setSalt(salt);
                            viewModel.insertUser(user);
                            Navigation
                                    .findNavController(v1)
                                    .navigate(R.id.action_registerFragment_to_loginFragment);
                        }
                    });
                });
            }
            else{
                Snackbar.make(v1, "Rellena todos los campos", Snackbar.LENGTH_LONG).show();
            }
        });
    }
}