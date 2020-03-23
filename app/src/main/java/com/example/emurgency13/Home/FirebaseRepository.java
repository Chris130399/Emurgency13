package com.example.emurgency13.Home;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.List;

public class FirebaseRepository {

    private OnFirestoreTaskComplete onFirestoreTaskComplete;

    private FirebaseFirestore firebaseFirestore = FirebaseFirestore.getInstance();
    private CollectionReference quizRef = firebaseFirestore.collection("MeasuresList");

    public FirebaseRepository(OnFirestoreTaskComplete onFirestoreTaskComplete) {
        this.onFirestoreTaskComplete = onFirestoreTaskComplete;
    }

    public void getHomeData() {
        quizRef.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    onFirestoreTaskComplete.quizListDataAdded(task.getResult().toObjects(HomeListModel.class));
                } else {
                    onFirestoreTaskComplete.onError(task.getException());
                }
            }
        });
    }

    public interface OnFirestoreTaskComplete {
        void quizListDataAdded(List<HomeListModel> HomeListModelsList);
        void onError(Exception e);
    }

}
