package es.unex.infinitetime.ui.user;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.textfield.TextInputEditText;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentUserBinding;
import es.unex.infinitetime.persistence.InfiniteDatabase;
import es.unex.infinitetime.persistence.User;
import es.unex.infinitetime.ui.login.PersistenceUser;

public class UserFragment extends Fragment {

    private FragmentUserBinding binding;
    private long user_id;

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

        InfiniteDatabase db = InfiniteDatabase.getDatabase(getContext());

        PersistenceUser persistenceUser = PersistenceUser.getInstance();
        user_id = persistenceUser.getUserId();

        // quitar este codigo en la integracion
        User u = new User();
        u.setId(user_id);
        u.setEmail("prueba@gmail.com");
        u.setPassword("prueba");
        u.setUsername("prueba");


        AppExecutors.getInstance().diskIO().execute(() -> {
            Log.d("DEPURANDO - INSERTAR USUARIO", "Insertando usuario con id: " + user_id);
            if(db.userDAO().getUser(u.getId()) != null){
                Log.d("DEPURANDO - INSERTAR USUARIO", "El usuario ya existe");
            }
            else{
                db.userDAO().insert(u);
            }
        });

        AppExecutors.getInstance().diskIO().execute(() -> {
            Log.d("DEPURANDO - OBTENER USUARIO", "Obteniendo usuario con id: " + user_id);
            User user = db.userDAO().getUser(user_id);
            Log.d("DEPURANDO - OBTENER USUARIO", "Usuario obtenido: " + user.getUsername());

            AppExecutors.getInstance().mainThread().execute(() -> {
                binding.textEditUsernameUser.setText(user.getUsername());
                binding.textEditEmailUser.setText(user.getEmail());
                binding.textEditPasswordUser.setText(user.getPassword());
            });
        });

        binding.btnUser.setOnClickListener(v -> {

            if(binding.textEditUsernameUser.getText().toString() != null
                    && binding.textEditEmailUser.getText().toString() != null
                    && binding.textEditPasswordUser.getText().toString() != null
                    && binding.textEditUsernameUser.getText().toString().equals("") == false
                    && binding.textEditEmailUser.getText().toString().equals("") == false
                    && binding.textEditPasswordUser.getText().toString().equals("") == false){
                AppExecutors.getInstance().diskIO().execute(() -> {
                    Log.d("DEPURANDO - ACTUALIZAR USUARIO", "Actualizando usuario con id: " + user_id);
                    User user = db.userDAO().getUser(user_id);
                    user.setUsername(binding.textEditUsernameUser.getText().toString());
                    user.setEmail(binding.textEditEmailUser.getText().toString());
                    user.setPassword(binding.textEditPasswordUser.getText().toString());
                    db.userDAO().update(user);
                    Log.d("DEPURANDO - ACTUALIZAR USUARIO", "Usuario actualizado: " + user.getUsername());
                });
            }
        });

        binding.btnDeleteUser.setOnClickListener(v -> {

            //Implementar la logica de borrado de usuario
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}