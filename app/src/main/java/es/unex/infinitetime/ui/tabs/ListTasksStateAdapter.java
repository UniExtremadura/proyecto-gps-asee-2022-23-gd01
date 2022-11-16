package es.unex.infinitetime.ui.tabs;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentItemTaskBinding;
import es.unex.infinitetime.persistence.Task;
import es.unex.infinitetime.persistence.TaskState;

public class ListTasksStateAdapter extends RecyclerView.Adapter<ListTasksStateAdapter.ViewHolder> {

    private List<Task> mItems = new ArrayList<>();
    Context mContext;

    public interface OnItemClickListener {
        void onItemClick(Task item);
    }

    private final ListTasksStateAdapter.OnItemClickListener listener;

    public ListTasksStateAdapter(Context context, ListTasksStateAdapter.OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;

        Task task = new Task(1, "Tarea 1",
                "Descripción tarea 1", TaskState.TODO,
                12, new Date(), 1, 1);

        mItems.add(task);

        task = new Task(2, "Tarea 2",
                "Descripción tarea 2", TaskState.TODO,
                17, new Date(), 1, 1);

        mItems.add(task);

        task = new Task(3, "Tarea 3",
                "Descripción tarea 3", TaskState.DONE,
                9, new Date(), 2, 1);

        mItems.add(task);

        task = new Task(4, "Tarea 4",
                "Descripción tarea 4", TaskState.DONE,
                7, new Date(), 1, 1);

        mItems.add(task);

        task = new Task(5, "Tarea 5",
                "Descripción tarea 5", TaskState.DOING,
                17, new Date(), 1, 1);

        mItems.add(task);
    }

    @NonNull
    @Override
    public ListTasksStateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.fragment_item_task, parent, false);

        return new ListTasksStateAdapter.ViewHolder(mContext,v);
    }

    @Override
    public void onBindViewHolder(ListTasksStateAdapter.ViewHolder holder, int position) {
        holder.bind(mItems.get(position),listener);

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(Task item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public void load(List<Task> items){

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public Object getItem(int pos) { return mItems.get(pos); }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;

        private FragmentItemTaskBinding binding;


        public ViewHolder(Context context, View itemView) {
            super(itemView);

            mContext = context;
            binding = FragmentItemTaskBinding.bind(itemView);

        }

        public void bind(final Task task, final ListTasksStateAdapter.OnItemClickListener listener) {

            binding.nameTaskItem.setText(task.getName());

            itemView.setOnClickListener(v -> listener.onItemClick(task));
        }
    }


}
