package es.unex.infinitetime.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputEditText;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentUserBinding;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.repository.PersistenceUser;
import es.unex.infinitetime.viewmodel.UserViewModel;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private UserViewModel userViewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentUserBinding.inflate(inflater, container, false);
        View root = binding.getRoot();
        userViewModel = ViewModelProviders.of(getActivity()).get(UserViewModel.class);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userViewModel.getUser().observe(getViewLifecycleOwner(), user -> {
            binding.textEditUsernameUser.setText(user.getUsername());
            binding.textEditEmailUser.setText(user.getEmail());
            binding.textEditPasswordUser.setText(user.getPassword());
        });

        binding.btnUser.setOnClickListener(v -> {

            if(!binding.textEditUsernameUser.getText().toString().equals("")
                    && !binding.textEditEmailUser.getText().toString().equals("")
                    && !binding.textEditPasswordUser.getText().toString().equals("")){

                    AppExecutors.getInstance().diskIO().execute(() -> {
                        User user = new User();
                        user.setUsername(binding.textEditUsernameUser.getText().toString());
                        user.setEmail(binding.textEditEmailUser.getText().toString());
                        user.setPassword(binding.textEditPasswordUser.getText().toString());
                        user.setId(userViewModel.getUserId());
                        userViewModel.updateUser(user);
                    });
            }
            else{
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
}