package io.github.ovso.righttoknow.violator;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.violator.vo.Violator;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 3
 */

public class ViolatorInteractor {

  private DatabaseReference databaseReference;

  public void req() {
    onViolationFacilityResultListener.onPre();
    databaseReference = FirebaseDatabase.getInstance().getReference().child("child_violator");
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        List<Violator> violators = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
          Violator violator = snapshot.getValue(Violator.class);
          violators.add(violator);
        }
        onViolationFacilityResultListener.onResult(violators);
        onViolationFacilityResultListener.onPost();
      }

      @Override public void onCancelled(DatabaseError databaseError) {
        onViolationFacilityResultListener.onError();
      }
    });
  }

  public void cancel() {
    databaseReference.onDisconnect();
  }

  @Getter @Setter private OnViolationResultListener onViolationFacilityResultListener;
}
