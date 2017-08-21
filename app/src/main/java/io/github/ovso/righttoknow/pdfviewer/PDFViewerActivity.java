package io.github.ovso.righttoknow.pdfviewer;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.github.barteksc.pdfviewer.PDFView;
import io.github.ovso.righttoknow.R;

/**
 * Created by jaeho on 2017. 8. 21
 */

public class PDFViewerActivity extends AppCompatActivity {
  @BindView(R.id.pdfView) PDFView pdfView;

  @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.content_pdfviewer);
    ButterKnife.bind(this);

    pdfView.fromAsset("k.pdf").load();
    /*
    StorageReference storageRef = FirebaseStorage.getInstance().getReference();
    StorageReference childReference = storageRef.child("child_certified/k.pdf");
    childReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
      @Override public void onSuccess(Uri uri) {
        Log.d("OJH", uri.toString());
        pdfView.fromUri(Uri.parse("https://drive.google.com/file/d/0B2WmHbdd_4NuSzhHRFBKcHpDbU0/view?usp=sharing")).load();
      }
    }).addOnFailureListener(new OnFailureListener() {
      @Override public void onFailure(@NonNull Exception e) {
        Log.d("OJH", e.getMessage());
      }
    });
    */

  }
}
