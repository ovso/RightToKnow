package io.github.ovso.righttoknow.certified;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.github.ovso.righttoknow.certified.vo.ChildCertified;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 21
 */

class CertifiedInteractor {
  private DatabaseReference databaseReference;

  public void req() {
    onChildResultListener.onPre();
    databaseReference = FirebaseDatabase.getInstance().getReference().child("child_certified");
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        List<ChildCertified> childCertifieds = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
          ChildCertified certified = snapshot.getValue(ChildCertified.class);
          childCertifieds.add(certified);
        }
        onChildResultListener.onResult(childCertifieds);
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

  @Getter @Setter private OnChildResultListener onChildResultListener;
}
