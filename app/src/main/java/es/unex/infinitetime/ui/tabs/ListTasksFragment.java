package es.unex.infinitetime.ui.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import es.unex.infinitetime.ui.project.ListProjectAdapter;
import es.unex.infinitetime.R;
import es.unex.infinitetime.persistence.TaskState;
import es.unex.infinitetime.ui.stats.StatsFragment;
import es.unex.infinitetime.databinding.FragmentListTasksBinding;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link StatsFragment #newInstance} factory method to
 * create an instance of this fragment.
 */
public class ListTasksFragment extends Fragment {

    private static final String Tag = "ListTasksFragment";

    public static final String ARG_PARAM1 = "project_id";

    private long projectId;

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private FragmentListTasksBinding binding;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private ListProjectAdapter mAdapter;

    private FloatingActionButton addNewTask;


    public ListTasksFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param projectId .
     * @return
     */
    // TODO: Rename and change types and number of parameters
    public static ListTasksFragment newInstance(long projectId) {
        Log.d(Tag, "ListTasksFragment");
        ListTasksFragment fragment = new ListTasksFragment();
        Bundle args = new Bundle();
        args.putLong(ARG_PARAM1, projectId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d(Tag, "onCreate");
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            projectId = getArguments().getLong(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Log.d(Tag, "onCreateView");


        binding = FragmentListTasksBinding.inflate(inflater, container, false);

        tabLayout = binding.tabLayout;
        viewPager = binding.viewPager;

        tabLayout.setupWithViewPager(viewPager);

        Bundle bundle = getArguments();

        TabsTasksListAdapter tabsTasksListAdapter = new TabsTasksListAdapter(getParentFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);

        tabsTasksListAdapter.addFragment(ListTasksStateFragment.newInstance(TaskState.TODO, projectId), "Sin empezar");
        tabsTasksListAdapter.addFragment(ListTasksStateFragment.newInstance(TaskState.DOING, projectId), "En progreso");
        tabsTasksListAdapter.addFragment(ListTasksStateFragment.newInstance(TaskState.DONE, projectId), "Hechas");
        viewPager.setAdapter(tabsTasksListAdapter);

        addNewTask= binding.AddProject;
        addNewTask.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_listTasksFragment_to_addTaskFragment, bundle);

            Log.d("Depurando", "Se ha pulsado el botón de añadir tarea");
        });


        return binding.getRoot();
    }
}