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
        long user_id = persistenceUser.getUserId();

        User u = new User();
        u.setId(user_id);

        //Prueba para ver que funcione el eliminar
        //Se eliminara en la integracion

        PersistenceUser persistenceUser2 = PersistenceUser.getInstance();
        persistenceUser2.setUserId(0);
        User u2 = new User();
        u2.setId(persistenceUser2.getUserId());
        u2.setUsername("prueba");
        Log.d("DEPURANDO", "Añadido usuario con id: " + u2.getId());


        binding.btnUser.setOnClickListener(v -> {
            // Implementar la lógica de editar usuario
        });

        binding.btnDeleteUser.setOnClickListener(v -> {

            /*
            Usando el identificador del usuario obtenido desde PersistenceUser borrar el usuario de la base de datos.
            Navegar dentro de la aplicación a la pantalla de login.
            */
            //Eliminar el siguiente bloque para la integracion porque es una prueba

            Log.d("DEPURANDO - BORRAR USUARIO", "Borrando usuario con id: " + u2.getId());
            AppExecutors.getInstance().diskIO().execute(() -> {
                db.userDAO().delete(u2);
                Log.d("DEPURANDO", "Obteniendo usuario borrado: " + db.userDAO().getUser("prueba"));
            });


            AppExecutors.getInstance().diskIO().execute(() -> {
                Log.d("DEPURANDO - BORRAR USUARIO", "Borrando usuario con id: " + user_id);
                db.userDAO().delete(u);
            });
            Navigation.findNavController(v).navigate(R.id.loginFragment);
        });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}