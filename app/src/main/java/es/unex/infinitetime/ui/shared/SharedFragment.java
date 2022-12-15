package es.unex.infinitetime.ui.shared;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import es.unex.infinitetime.databinding.FragmentSharedBinding;
import es.unex.infinitetime.viewmodel.SharedViewModel;


public class SharedFragment extends Fragment {

    public static final String ARGS_PARAM1 = "project_id";

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private SharedAdapter mAdapter;
    private SharedViewModel sharedViewModel;

    private FragmentSharedBinding binding;

    private long projectId;

    public SharedFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("Depurando", "onCreateView favorite");

        binding = FragmentSharedBinding.inflate(inflater, container, false);
        sharedViewModel = ViewModelProviders.of(getActivity()).get(SharedViewModel.class);

        sharedViewModel.getUsersShared().observe(getViewLifecycleOwner(), users -> {
            mAdapter.load(users);
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mRecyclerView = binding.recyclerView;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new SharedAdapter(getActivity().getApplicationContext(), sharedViewModel);

        mRecyclerView.setAdapter(mAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

}