package com.example.emurgency13.Home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

public class HomeListViewModel extends ViewModel implements FirebaseRepository.OnFirestoreTaskComplete {

    private MutableLiveData<List<HomeListModel>> HomeListModelData = new MutableLiveData<>();

    public LiveData<List<HomeListModel>> getHomeListModelData() {
        return HomeListModelData;
    }

    private FirebaseRepository firebaseRepository = new FirebaseRepository(this);

    public HomeListViewModel() {
        firebaseRepository.getHomeData();
    }

    @Override
    public void quizListDataAdded(List<HomeListModel> HomeListModelsList) {
        HomeListModelData.setValue(HomeListModelsList);
    }

    @Override
    public void onError(Exception e) {

    }
}
