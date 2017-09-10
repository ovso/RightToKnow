package io.github.ovso.righttoknow.pdfviewer;

import android.text.TextUtils;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import hugo.weaving.DebugLog;
import io.github.ovso.righttoknow.app.MyApplication;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import java.io.File;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by jaeho on 2017. 8. 22
 */

class PDFViewerInteractor {
  private File localFile;
  @Setter private String fileName;
  private FileDownloadTask fileDownloadTask;

  public void req() {
    if (onChildResultListener != null) onChildResultListener.onPre();
    if (!TextUtils.isEmpty(fileName)) {
      File file = new File(MyApplication.getInstance().getFilesDir() + "/" + fileName);
      if (file.exists()) {
        if (onChildResultListener != null) {
          onChildResultListener.onResult(file);
          onChildResultListener.onPost();
        }
        return;
      }
      StorageReference storageRef = FirebaseStorage.getInstance().getReference();
      try {
        localFile = new File(MyApplication.getInstance().getFilesDir(), fileName);
        StorageReference childReference = storageRef.child("child_certified/" + fileName);
        fileDownloadTask = childReference.getFile(localFile);
        fileDownloadTask.addOnSuccessListener(taskSnapshot -> {
          if (onChildResultListener != null) {
            onChildResultListener.onResult(localFile);
            onChildResultListener.onPost();
          }
        }).addOnFailureListener(e -> {
          e.printStackTrace();
          if (onChildResultListener != null) onChildResultListener.onError();
        });
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  @Setter @Getter private OnChildResultListener onChildResultListener;

  @DebugLog public void cancel() {
    if (fileDownloadTask != null) {
      fileDownloadTask.cancel();
    }
  }
}
