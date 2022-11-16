package es.unex.infinitetime.ui.shared;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.infinitetime.databinding.FragmentItemSharedBinding;
import es.unex.infinitetime.databinding.FragmentItemTaskBinding;
import es.unex.infinitetime.persistence.User;

public class SharedAdapter extends RecyclerView.Adapter<SharedAdapter.ViewHolder> {
    private List<User> mItems = new ArrayList<>();
    Context mContext;

    private FragmentItemSharedBinding binding;
    private long projectId;


    public interface OnItemClickListener {
        void onItemClick(User item);
    }
    

    public SharedAdapter(Context context, long projectId) {
        mContext = context;
        this.projectId = projectId;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        binding = FragmentItemSharedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mContext,binding.getRoot(), projectId);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(User item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear(){

        mItems.clear();
        notifyDataSetChanged();

    }

    public void load(List<User> items){

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public Object getItem(int pos) { return mItems.get(pos); }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;

        private FragmentItemSharedBinding binding;

        private long projectId;

        public ViewHolder(Context context, View itemView, long projectId) {
            super(itemView);

            mContext = context;
            binding = FragmentItemSharedBinding.bind(itemView);
            this.projectId = projectId;

        }

        public void bind(final User item) {

            binding.textUsernameShared.setText(item.getUsername());

            boolean isShared = false;
            // Comprobar si el proyecto está compartido con el usuario
            // usando el id del proyecto y el id del usuario
            // Si está compartido, mostrar el checkbox marcado
            // Si no está compartido, mostrar el checkbox desmarcado

            binding.sharedCheckBox.setChecked(isShared);

            binding.sharedCheckBox.setOnCheckedChangeListener((v, isChecked) -> {
                if (isChecked) {
                    // Compartir el proyecto con el usuario
                    // Insertar en la tabla de proyectos compartidos
                } else {
                    // Quitar la compartición del proyecto con el usuario
                    // Eliminar de la tabla de proyectos compartidos
                }
            });


        }
    }

}
