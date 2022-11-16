package es.unex.infinitetime.ui.favorite;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.infinitetime.R;
import es.unex.infinitetime.databinding.FragmentItemTaskBinding;
import es.unex.infinitetime.persistence.Task;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private final OnItemClickListener listener;

    private List<Task> mItems = new ArrayList<>();
    Context mContext;

    private FragmentItemTaskBinding binding;


    public interface OnItemClickListener {
        void onItemClick(Task item);
    }
    

    public FavoriteAdapter(Context context, OnItemClickListener listener) {
        mContext = context;
        this.listener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        binding = FragmentItemTaskBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mContext,binding.getRoot());
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
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
        Log.d("Depurando", "load Adapter stats");

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

        public void bind(final Task task, OnItemClickListener listener) {

            binding.nameTaskItem.setText(task.getName());

            itemView.setOnClickListener(v -> listener.onItemClick(task));

            boolean isFavorite = false;
            // Comprobar si la tarea es favorita en la base de datos
            binding.checkboxFavorite.setChecked(isFavorite);

            binding.checkboxFavorite.setOnCheckedChangeListener((compoundButton, isChecked) -> {
                if(isChecked){
                    // Lógica para añadir a favoritos
                }
                else {
                    // Lógica para quitar de favoritos
                }
            });

            binding.deleteButtonTask.setOnClickListener(v -> {
                // Lógica para eliminar la tarea
            });

        }
    }

}
