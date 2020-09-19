package com.quy.myapplication;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ProgressBar;

import java.util.List;


public class ListFragment extends Fragment implements QuizListAdapter.OnQuizListItemClicked {

    // Navigate
    private NavController navController;
    private RecyclerView listView;
    private QuizListViewModel quizListViewModel;
    ProgressBar listProgress;

    private Animation fadeInAnim;
    private Animation fadeOutAnim;

    private QuizListAdapter adapter;

    public ListFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        Log.e("VIEW_LOG","onCreateView");
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.e("VIEW_LOG","onViewCreated");

        navController = Navigation.findNavController(view);

        listView = view.findViewById(R.id.list_view);
        adapter = new QuizListAdapter(this);

        listView.setLayoutManager(new LinearLayoutManager(getContext()));
        listProgress = view.findViewById(R.id.list_progress);
        listView.setHasFixedSize(true);
        listView.setAdapter(adapter);

        fadeInAnim = AnimationUtils.loadAnimation(getContext(),R.anim.fade_in);
        fadeOutAnim = AnimationUtils.loadAnimation(getContext(),R.anim.fade_out);



    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Log.e("VIEW_LOG","onActivityCreated");
        quizListViewModel = new ViewModelProvider(getActivity()).get(QuizListViewModel.class);
        quizListViewModel.getQuizListModelData().observe(getViewLifecycleOwner(), new Observer<List<QuizListModel>>() {
            @Override
            public void onChanged(List<QuizListModel> quizListModels) {
                Log.e("VIEW_LOG","onChanged");
                listView.startAnimation(fadeInAnim);
                listProgress.startAnimation(fadeOutAnim);
                adapter.setQuizListModels(quizListModels);
                adapter.notifyDataSetChanged();
            }
        });
    }

    @Override
    public void onItemClicked(int position) {
        ListFragmentDirections.ActionListFragmentToDetailsFragment action = ListFragmentDirections.actionListFragmentToDetailsFragment();
        action.setPosition(position);
        Log.e("POSITION",position+"");
        navController.navigate(action);

    }
}
