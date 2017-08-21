package io.github.ovso.righttoknow.pdfviewer;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.barteksc.pdfviewer.PDFView;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FileDownloadTask;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import io.github.ovso.righttoknow.R;
import java.io.File;
import java.io.IOException;

/**
 * Created by jaeho on 2017. 8. 21
 */

public class PDFViewerActivity extends AppCompatActivity {
  @BindView(R.id.pdfView) PDFView pdfView;

  private File localFile;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_pdfviewer);
    ButterKnife.bind(this);

    //pdfView.fromAsset("k.pdf").load();

    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    try {
      localFile = File.createTempFile("kkk", "pdf");
    } catch (IOException e) {
      e.printStackTrace();
    }
    StorageReference childReference = storageRef.child("child_certified/k.pdf");
    childReference.getFile(localFile)
        .addOnSuccessListener(new OnSuccessListener<FileDownloadTask.TaskSnapshot>() {
          @Override public void onSuccess(FileDownloadTask.TaskSnapshot taskSnapshot) {
            Log.d("OJH", localFile.toString());
            pdfView.fromFile(
                new File(localFile.toString())).load();
          }
        })
        .addOnFailureListener(new OnFailureListener() {
          @Override public void onFailure(@NonNull Exception e) {
            e.printStackTrace();
          }
        });
  }
}