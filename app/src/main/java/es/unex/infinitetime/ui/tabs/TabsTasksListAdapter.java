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
        return ListTasksStateFragment.newInstance(TaskState.values()[position]);
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
