package es.unex.infinitetime.ui.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;

import java.util.ArrayList;
import java.util.List;

import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentItemTaskBinding;
import es.unex.infinitetime.model.Task;
import es.unex.infinitetime.model.TaskWithFavorite;
import es.unex.infinitetime.viewmodel.TaskViewModel;

public class ListTasksStateAdapter extends RecyclerView.Adapter<ListTasksStateAdapter.ViewHolder> {

    private List<TaskWithFavorite> mItems = new ArrayList<>();
    private TaskViewModel taskViewModel;
    Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Task item);
    }

    private final OnItemClickListener listener;

    public ListTasksStateAdapter(Context context, OnItemClickListener listener, TaskViewModel taskViewModel) {
        mContext = context;
        this.listener = listener;
        this.taskViewModel = taskViewModel;
    }

    @NonNull
    @Override
    public ListTasksStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_task, parent, false);

        return new ListTasksStateAdapter.ViewHolder(mContext, v, taskViewModel);
    }

    @Override
    public void onBindViewHolder(ListTasksStateAdapter.ViewHolder holder, int position) {
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

    public void load(List<TaskWithFavorite> items){

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

        public void bind(final TaskWithFavorite task, final ListTasksStateAdapter.OnItemClickListener listener) {

            binding.nameTaskItem.setText(task.getName());
            itemView.setOnClickListener(v -> listener.onItemClick(task));

            Button delete = binding.deleteButtonTask;

            delete.setOnClickListener(v -> {
                taskViewModel.deleteTask(task.getId());
                Snackbar.make(v, "Tarea "+ task.getName()+"- borrada", Snackbar.LENGTH_SHORT).show();
            });

            binding.checkboxFavorite.setChecked(task.isFavorite());
            binding.checkboxFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                taskViewModel.switchFavorite(task.getId(), isChecked);
            });
        }
    }


}
