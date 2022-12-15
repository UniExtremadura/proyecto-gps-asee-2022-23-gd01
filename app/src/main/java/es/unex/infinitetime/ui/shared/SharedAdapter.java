package es.unex.infinitetime.ui.shared;


import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import es.unex.infinitetime.AppExecutors;
import es.unex.infinitetime.databinding.FragmentItemSharedBinding;
import es.unex.infinitetime.model.InfiniteDatabase;
import es.unex.infinitetime.model.Project;
import es.unex.infinitetime.model.SharedProject;
import es.unex.infinitetime.model.User;
import es.unex.infinitetime.model.UserShared;
import es.unex.infinitetime.viewmodel.SharedViewModel;

public class SharedAdapter extends RecyclerView.Adapter<SharedAdapter.ViewHolder> {
    private List<UserShared> mItems = new ArrayList<>();
    private SharedViewModel sharedViewModel;
    Context mContext;

    private FragmentItemSharedBinding binding;


    public interface OnItemClickListener {
        void onItemClick(User item);
    }


    public SharedAdapter(Context context, SharedViewModel sharedViewModel) {
        mContext = context;
        this.sharedViewModel = sharedViewModel;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        binding = FragmentItemSharedBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ViewHolder(mContext, binding.getRoot(), sharedViewModel);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.bind(mItems.get(position));

    }

    @Override
    public int getItemCount() {
        return mItems.size();
    }

    public void add(UserShared item) {

        mItems.add(item);
        notifyDataSetChanged();

    }

    public void clear() {

        mItems.clear();
        notifyDataSetChanged();

    }

    public void load(List<UserShared> items) {

        mItems.clear();
        mItems = items;
        notifyDataSetChanged();

    }

    public Object getItem(int pos) {
        return mItems.get(pos);
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        private Context mContext;

        private FragmentItemSharedBinding binding;

        private SharedViewModel sharedViewModel;

        public ViewHolder(Context context, View itemView, SharedViewModel sharedViewModel) {
            super(itemView);

            mContext = context;
            binding = FragmentItemSharedBinding.bind(itemView);
            this.sharedViewModel = sharedViewModel;
        }

        public void bind(final UserShared item) {

            binding.textUsernameShared.setText(item.getUsername());
            binding.sharedCheckBox.setChecked(item.isShared());

            binding.sharedCheckBox.setOnCheckedChangeListener((v, isChecked) -> {
                sharedViewModel.switchShared(item.getId(), isChecked);
            });
        }

    }
}
