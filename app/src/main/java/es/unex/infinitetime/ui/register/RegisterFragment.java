package es.unex.infinitetime.ui.register;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.google.android.material.textfield.TextInputEditText;

import es.unex.infinitetime.DrawerLocker;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentRegisterBinding;


public class RegisterFragment extends Fragment {

    private FragmentRegisterBinding binding;

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
        return binding.getRoot();

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        Log.d("Debug", "onViewCreated RegisterFragment");
        ((DrawerLocker) getActivity()).setDrawerEnabled(false);

        Button btnRegister = binding.btnRegister;

        TextInputEditText tvUsername = binding.textEditUsernameRegister;
        TextInputEditText tvPassword = binding.textEditPasswordRegister;
        TextInputEditText tvEmail = binding.textEditEmailRegister;

        tvUsername.setText(getArguments().getString("username"));

        btnRegister.setOnClickListener(v1 -> {
            // Crear la lógica del registro
            Navigation.findNavController(v1).navigate(R.id.action_registerFragment_to_loginFragment);
        });
    }
}