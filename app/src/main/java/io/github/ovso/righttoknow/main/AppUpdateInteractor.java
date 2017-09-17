package io.github.ovso.righttoknow.main;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import io.github.ovso.righttoknow.main.vo.AppUpdate;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class AppUpdateInteractor {

  private DatabaseReference databaseReference;

  public void req() {
    if(onChildResultListener != null) onChildResultListener.onPre();
    databaseReference = FirebaseDatabase.getInstance().getReference().child("app_update");
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        AppUpdate update = dataSnapshot.getValue(AppUpdate.class);
        if (onChildResultListener != null) {
          onChildResultListener.onResult(update);
          onChildResultListener.onPost();
        }
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        if (onChildResultListener != null) {
          onChildResultListener.onError();
        }
      }
    });
  }

  @DebugLog public void cancel() {
    databaseReference.onDisconnect();
    onChildResultListener = null;
  }

  @Getter @Setter private OnChildResultListener onChildResultListener;
}