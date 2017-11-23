package io.github.ovso.righttoknow.network;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import io.github.ovso.righttoknow.network.model.AppTextContent;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class AppTextNetwork {

  private DatabaseReference databaseReference;

  public void req() {
    if(onChildResultListener != null) onChildResultListener.onPre();
    databaseReference = FirebaseDatabase.getInstance().getReference().child("app_text");
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        AppTextContent update = dataSnapshot.getValue(AppTextContent.class);
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