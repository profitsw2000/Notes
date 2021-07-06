package ru.profitsw2000.data;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FirebaseSource implements CardSource{

    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[FirebaseSource]";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    private List<MyNotes> dataSource = new ArrayList<MyNotes>();

    @Override
    public FirebaseSource init(final CardSourceResponse cardsSourceResponse) throws ParseException {
        collection.orderBy(MyNotesMapping.Fields.DATE,
                Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()) {
                            dataSource = new ArrayList<MyNotes>();
                            for (QueryDocumentSnapshot document : task.getResult()) {
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                MyNotes cardData = MyNotesMapping.toMyNotes(id,
                                        doc);
                                dataSource.add(cardData);
                            }
                            Log.d(TAG, "success " + dataSource.size() + " qnt");
                            cardsSourceResponse.initialized(FirebaseSource.this);
                        } else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "get failed with ", e);
                    }
                });

        return this;
    }

    @Override
    public MyNotes getMyNotes(int position) {
        return dataSource.get(position);
    }

    @Override
    public int size() {
        if (dataSource == null) {
            return 0;
        }
        return dataSource.size();
    }

    @Override
    public void deleteNote(int position) {
        collection.document(dataSource.get(position).getId()).delete()  ;
        dataSource.remove(position) ;
    }

    @Override
    public void updateNote(int position, MyNotes myNotes) {
        String id = myNotes.getId();
        collection.document(id).set(MyNotesMapping.toDocument(myNotes)) ;
    }

    @Override
    public void addNote(MyNotes myNotes) {
        collection.add(MyNotesMapping.toDocument(myNotes)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                myNotes.setId(documentReference.getId());
            }
        });
    }

    @Override
    public void clearNote() {
        for (MyNotes myNotes:
             dataSource) {
            collection.document(myNotes.getId()).delete()   ;
            dataSource = new ArrayList<MyNotes>()  ;
        }
    }


}
