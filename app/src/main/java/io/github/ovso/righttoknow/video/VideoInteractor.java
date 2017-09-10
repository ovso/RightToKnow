package io.github.ovso.righttoknow.video;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import io.github.ovso.righttoknow.video.vo.Video;
import java.util.ArrayList;
import java.util.List;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 9. 8
 */

public class VideoInteractor {
  private DatabaseReference databaseReference;

  public void req() {
    if (onChildResultListener != null) onChildResultListener.onPre();
    databaseReference = FirebaseDatabase.getInstance().getReference().child("child_care_video");
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        List<Video> items = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
          Video video = snapshot.getValue(Video.class);
          items.add(video);
        }
        if (onChildResultListener != null) {
          onChildResultListener.onResult(items);
          onChildResultListener.onPost();
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        if (onChildResultListener != null) onChildResultListener.onError();
      }
    });
  }

  public void cancel() {
    databaseReference.onDisconnect();
  }

  @Setter private OnChildResultListener onChildResultListener;
}
