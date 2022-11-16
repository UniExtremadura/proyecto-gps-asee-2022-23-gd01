package es.unex.infinitetime.ui.project;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentListOfProjectsBinding;
import es.unex.infinitetime.datosEjemplo.ExampleData;
import es.unex.infinitetime.persistence.Project;
import es.unex.infinitetime.ui.tabs.ListTasksFragment;


public class ListOfProjectsFragment extends Fragment {

    private static final String ARG_PARAM1 = "user_id";

    private long user_id;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListProjectAdapter mAdapter;
    private FloatingActionButton addNewProject;

    private FragmentListOfProjectsBinding binding;

    public ListOfProjectsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        binding = FragmentListOfProjectsBinding.inflate(inflater, container, false);

        return binding.getRoot();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mAdapter.getItemCount() == 0)
            loadItems();
        Log.d("Depurando", "onResume de list of projects");


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = binding.recyclerView;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new ListProjectAdapter(getActivity().getApplicationContext(), item -> {
            Bundle bundle = new Bundle();
            bundle.putLong(ListTasksFragment.ARG_PARAM1, item.getId());
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_projects_to_listTasksFragment, bundle);
        });

        addNewProject = binding.AddProject;
        addNewProject.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_projects_to_addProjectFragment);
            Log.d("Depurando", "Se ha pulsado el botón de añadir proyecto");
        });

        mRecyclerView.setAdapter(mAdapter);
    }

    // Load stored ToDoItems
    private void loadItems() {
        ExampleData ed = new ExampleData();
        List<Project> mItems = ed.getProjectsList();
        /*ToDoItemCRUD crud = ToDoItemCRUD.getInstance(this);
        List<ToDoItem> items = crud.getAll();
        AppExecutors.getInstance().diskIO().execute(() -> {
            InfiniteDatabase db = InfiniteDatabase.getDatabase(getActivity().getApplicationContext());
            List<Project> mItems=db.projectDAO().getAllProject();

             mAdapter.load(mItems);

        });
        */
        mAdapter.load(mItems);
    }

    public void swapFragment(FloatingActionButton edit){

        edit.setOnClickListener(v -> {
            Navigation.findNavController(v).navigate(R.id.action_projects_to_editProjectFragment);
            Log.d("Depurando", "Se ha pulsado el botón de editar proyecto");
        });
    }
}