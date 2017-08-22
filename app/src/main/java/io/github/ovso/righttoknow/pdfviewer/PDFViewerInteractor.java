package io.github.ovso.righttoknow.pdfviewer;

import android.text.TextUtils;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import java.io.File;
import java.io.IOException;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 22
 */

class PDFViewerInteractor {
  private File localFile;
  @Setter private String fileName;

  public void req() {
    onChildResultListener.onPre();
    if (!TextUtils.isEmpty(fileName)) {
      StorageReference storageRef = FirebaseStorage.getInstance().getReference();
      try {
        localFile = File.createTempFile(fileName, "pdf");
        StorageReference childReference = storageRef.child("child_certified/" + fileName);
        childReference.getFile(localFile).addOnSuccessListener(taskSnapshot -> {
          onChildResultListener.onResult(localFile);
          onChildResultListener.onPost();
        }).addOnFailureListener(e -> {
          e.printStackTrace();
          onChildResultListener.onError();
        });
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  @Setter @Getter private OnChildResultListener onChildResultListener;
}
