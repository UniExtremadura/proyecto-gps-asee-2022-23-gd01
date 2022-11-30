package es.unex.infinitetime.ui.tabs;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import es.unex.infinitetime.model.TaskState;

public class TabsTasksListAdapter extends FragmentStateAdapter {

    Fragment fragment;

    public TabsTasksListAdapter(@NonNull Fragment fragment) {
        super(fragment);
        this.fragment = fragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        long projectId = fragment.getArguments().getLong(ListTasksFragment.ARG_PARAM1);
        return ListTasksStateFragment.newInstance(TaskState.values()[position], projectId);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
