package es.unex.infinitetime.ui.project;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.R;
//import es.unex.infinitetime.databinding.FragmentItemProjectBinding;
import es.unex.infinitetime.databinding.FragmentItemProjectBinding;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.viewmodel.ProjectViewModel;
import es.unex.infinitetime.viewmodel.SharedViewModel;

public class ListProjectAdapter extends RecyclerView.Adapter<ListProjectAdapter.ViewHolder> {
    private final OnItemClickListener listener;

    private List<Project> mItems = new ArrayList<>();
    Context mContext;
    private FragmentItemProjectBinding binding;
    private ProjectViewModel projectViewModel;
    private SharedViewModel sharedViewModel;

    public interface OnItemClickListener {
        void onItemClick(Project item);
    }

    public ListProjectAdapter(Context context, OnItemClickListener listener, ProjectViewModel projectViewModel, SharedViewModel sharedViewModel) {
        mContext = context;
        this.listener = listener;
        this.projectViewModel = projectViewModel;
        this.sharedViewModel = sharedViewModel;
    }


    @NonNull
    @Override
    public ListProjectAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        binding = FragmentItemProjectBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new ViewHolder(mContext,binding.getRoot(), projectViewModel, sharedViewModel);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Project item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public void load(List<Project> items){

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public Object getItem(int pos) { return mItems.get(pos); }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private FragmentItemProjectBinding binding;
        private ProjectViewModel projectViewModel;
        private SharedViewModel sharedViewModel;

        public ViewHolder(Context context, View itemView, ProjectViewModel projectViewModel, SharedViewModel sharedViewModel) {
            super(itemView);

            mContext = context;
            binding = FragmentItemProjectBinding.bind(itemView);
            this.projectViewModel = projectViewModel;
            this.sharedViewModel = sharedViewModel;

        }

        public void bind(final Project project, final OnItemClickListener listener) {

            binding.tvItemProjectName.setText(project.getName());

            itemView.setOnClickListener(v -> listener.onItemClick(project));

            FloatingActionButton edit = binding.editProjectItemBtn;
            FloatingActionButton delete = binding.deleteProjectBtn;
            FloatingActionButton share = binding.shareProjectItemBtn;

            edit.setOnClickListener(v -> {
                projectViewModel.selectProject(project);
                Navigation.findNavController(v).navigate(R.id.action_projects_to_editProjectFragment);
            });

            delete.setOnClickListener(v -> {
                projectViewModel.deleteProject(project.getId());
            });

            share.setOnClickListener(v -> {
                sharedViewModel.setProjectId(project.getId());
                Navigation.findNavController(v).navigate(R.id.action_projects_to_sharedFragment);
            });

        }
    }

}
