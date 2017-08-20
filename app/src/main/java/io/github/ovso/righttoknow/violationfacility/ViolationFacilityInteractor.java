package io.github.ovso.righttoknow.violationfacility;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import io.github.ovso.righttoknow.common.Constants;
import io.github.ovso.righttoknow.listener.OnViolationResultListener;
import io.github.ovso.righttoknow.violationfacility.vo.ViolationFacility;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 1
 */

public class ViolationFacilityInteractor {

  private DatabaseReference databaseReference;

  public void req() {
    String url = Constants.BASE_URL
        + Constants.VIOLATION_FACILITY_PATH
        + Constants.VIOLATION_FACILITY_PARAMS;
    //myAsyncTask.execute(new String[] { url });

    onViolationFacilityResultListener.onPre();
    databaseReference = FirebaseDatabase.getInstance().getReference().child("child_vio_fac");
    databaseReference.addValueEventListener(new ValueEventListener() {
      @Override public void onDataChange(DataSnapshot dataSnapshot) {
        List<ViolationFacility> violationFacilities = new ArrayList<>();
        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
          ViolationFacility violationFacility = snapshot.getValue(ViolationFacility.class);
          violationFacilities.add(violationFacility);
        }
        onViolationFacilityResultListener.onResult(violationFacilities);
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
