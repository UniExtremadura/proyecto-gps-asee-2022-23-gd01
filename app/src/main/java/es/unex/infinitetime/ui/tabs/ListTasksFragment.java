package es.unex.infinitetime.ui.tabs;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentListTasksBinding;


public class ListTasksFragment extends Fragment {

    public static final String[] namesTabs = {"Sin empezar", "En progreso", "Hechas"};

    public ListTasksFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        FragmentListTasksBinding binding = FragmentListTasksBinding.inflate(inflater, container, false);

        TabLayout tabLayout = binding.tabLayout;
        ViewPager2 viewPager = binding.viewPager;

        TabsTasksListAdapter tabsTasksListAdapter = new TabsTasksListAdapter(this);
        viewPager.setAdapter(tabsTasksListAdapter);

        new TabLayoutMediator(tabLayout, viewPager,
                (tab, position) -> tab.setText(namesTabs[position])
        ).attach();

        FloatingActionButton addNewTask = binding.AddProject;
        addNewTask.setOnClickListener(view -> {
            Navigation.findNavController(view).navigate(R.id.action_listTasksFragment_to_addTaskFragment);
        });

        return binding.getRoot();
    }
}