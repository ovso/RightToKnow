package io.github.ovso.righttoknow.news;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import io.github.ovso.righttoknow.news.vo.News;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 9. 2
 */

class NewsInteractor {

  private DatabaseReference databaseReference;

  public void req() {
    onChildResultListener.onPre();
    databaseReference = FirebaseDatabase.getInstance().getReference().child("child_care_news");
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        List<News> items = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
          News news = snapshot.getValue(News.class);
          items.add(news);
        }
        onChildResultListener.onResult(items);
        onChildResultListener.onPost();
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        onChildResultListener.onError();
      }
    });
  }

  public void cancel() {
    databaseReference.onDisconnect();
  }

  @Setter private OnChildResultListener onChildResultListener;
}
