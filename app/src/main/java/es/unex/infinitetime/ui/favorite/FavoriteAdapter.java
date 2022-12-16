package es.unex.infinitetime.ui.favorite;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.unex.infinitetime.databinding.FragmentItemTaskBinding;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskWithFavorite;
import es.unex.infinitetime.viewmodel.TaskViewModel;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final OnItemClickListener listener;

    private List<TaskWithFavorite> mItems = new ArrayList<>();
    Context mContext;

    private FragmentItemTaskBinding binding;
    private TaskViewModel taskViewModel;


    public interface OnItemClickListener {
        void onItemClick(Task item);
    }


    public FavoriteAdapter(Context context, OnItemClickListener listener, TaskViewModel taskViewModel) {
        mContext = context;
        this.listener = listener;
        this.taskViewModel = taskViewModel;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        binding = FragmentItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mContext,binding.getRoot(),taskViewModel);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(TaskWithFavorite item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public void load(List<TaskWithFavorite> items) {

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public Object getItem(int pos) { return mItems.get(pos); }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;

        private FragmentItemTaskBinding binding;
        private TaskViewModel taskViewModel;

        public ViewHolder(Context context, View itemView, TaskViewModel taskViewModel) {
            super(itemView);

            mContext = context;
            binding = FragmentItemTaskBinding.bind(itemView);
            this.taskViewModel = taskViewModel;

        }

        public void bind(final TaskWithFavorite task, OnItemClickListener listener) {

            binding.nameTaskItem.setText(task.getName());

            itemView.setOnClickListener(v -> listener.onItemClick(task));

            binding.checkboxFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                taskViewModel.switchFavorite(task.getId(), isChecked);
            });

            binding.checkboxFavorite.setChecked(task.isFavorite());
            binding.deleteButtonTask.setOnClickListener(v -> {
                taskViewModel.deleteTask(task.getId());
                Snackbar.make(v, "Tarea -" + task.getName() + "- borrada", Snackbar.LENGTH_SHORT).show();
            });

        }
    }
}
