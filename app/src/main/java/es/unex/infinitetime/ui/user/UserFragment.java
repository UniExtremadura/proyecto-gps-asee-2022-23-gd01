package es.unex.infinitetime.ui.user;

import android.app.Application;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import com.google.android.material.snackbar.Snackbar;

import es.unex.infinitetime.AppContainer;
import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.InfiniteTime;
import es.unex.infinitetime.R;
import es.unex.infinitetime.cryptography.Hash;
import es.unex.infinitetime.databinding.FragmentUserBinding;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.viewmodel.UserViewModel;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private UserViewModel userViewModel;

    private String oldUsername;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Application application = getActivity().getApplication();
        AppContainer appContainer = ((InfiniteTime) application).getAppContainer();
        userViewModel = new ViewModelProvider(getActivity(), appContainer.factory).get(UserViewModel.class);

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            if (user != null) {
                binding.textEditUsernameUser.setText(user.getUsername());
                binding.textEditEmailUser.setText(user.getEmail());
                oldUsername = user.getUsername();
            }
        });

        binding.btnUser.setOnClickListener(v -> {

            String username = binding.textEditUsernameUser.getText().toString();
            String email = binding.textEditEmailUser.getText().toString();

            if (!username.equals("") && !email.equals("")) {
                AppExecutors.getInstance().diskIO().execute(() -> {

                    if (!usernameAlreadyExists(username)) {
                        String password = binding.textEditPasswordUser.getText().toString();

                        User user = userViewModel.getUserWithoutLiveData();
                        user.setUsername(username);
                        user.setEmail(email);

                        // Sí la contraseña no está vacía, se cambia
                        // y en caso contrario se mantiene la anterior
                        if (!password.equals("")) {
                            Hash hash = new Hash();
                            byte[] salt = hash.getSalt();
                            byte[] hashPassword = hash.getHash(salt, password);
                            user.setHash(hashPassword);
                            user.setSalt(salt);
                        }
                        userViewModel.updateUser(user);
                    } else {
                        AppExecutors.getInstance().mainThread().execute(() -> {
                            Snackbar.make(view, "Ese nombre de usuario ya existe", Snackbar.LENGTH_LONG).show();
                        });
                    }
                });

            } else {
                Snackbar.make(v, "Ninguno de los campos puede estar en blanco", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }

        });

        binding.btnDeleteUser.setOnClickListener(v -> {
            userViewModel.deleteUser(userViewModel.getUserId());
            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    private boolean usernameAlreadyExists(String username){
        return !oldUsername.equals(username) && userViewModel.usernameExists(username);
    }
}