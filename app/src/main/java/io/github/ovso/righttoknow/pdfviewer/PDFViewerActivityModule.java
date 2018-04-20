package io.github.ovso.righttoknow.pdfviewer;

import com.fsn.cauly.CaulyAdInfo;
import com.fsn.cauly.CaulyAdInfoBuilder;
import com.fsn.cauly.CaulyAdView;
import dagger.Module;
import dagger.Provides;
import io.github.ovso.righttoknow.Security;
import javax.inject.Singleton;

@Module public abstract class PDFViewerActivityModule {
  @Singleton @Provides public static CaulyAdView provideCaulyAdView(PDFViewerActivity activity) {
    CaulyAdView view;
    CaulyAdInfo info =
        new CaulyAdInfoBuilder(Security.CAULY_APP_CODE.getValue()).effect(
            CaulyAdInfo.Effect.Circle.toString())
            .build();
    view = new CaulyAdView(activity.getApplicationContext());
    view.setAdInfo(info);
    return view;
  }
}