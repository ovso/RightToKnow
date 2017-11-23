package io.github.ovso.righttoknow.main;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import io.github.ovso.righttoknow.R;
import io.github.ovso.righttoknow.framework.BaseAlertDialogFragment;
import io.github.ovso.righttoknow.framework.GlideApp;
import io.github.ovso.righttoknow.listener.OnChildResultListener;
import io.github.ovso.righttoknow.network.AppTextNetwork;
import io.github.ovso.righttoknow.network.model.AppTextContent;

/**
 * Created by jaeho on 2017. 11. 23
 */

public class DonationDialog extends BaseAlertDialogFragment {

  @BindView(R.id.imageview) ImageView imageview;
  @BindView(R.id.textview) TextView textview;

  @Override protected boolean isNegativeButton() {
    return false;
  }

  @Override protected boolean isPositiveButton() {
    return true;
  }

  @Override protected void onActivityCreate(Bundle savedInstanceState) {
    AppTextNetwork network = new AppTextNetwork();
    network.req();
    network.setOnChildResultListener(new OnChildResultListener<AppTextContent>() {
      @Override public void onPre() {

      }

      @Override public void onResult(AppTextContent result) {
        Log.d("DonationDialog", result.toString());
        GlideApp.with(DonationDialog.this)
            .load(result.getDonation_img_url())
            .into(imageview);
        textview.setText(result.getDonation());
      }

      @Override public void onPost() {

      }

      @Override public void onError() {

      }
    });
  }

  @Override protected boolean getAttatchRoot() {
    return false;
  }

  @Override protected int getLayoutResId() {
    return R.layout.fragment_donation;
  }

  @Override protected ViewGroup getInflateRoot() {
    return null;
  }

  @Override protected boolean isDialogCancelable() {
    return true;
  }

  @Override protected int getTitle() {
    return R.string.empty;
  }

  @Override protected View.OnClickListener onPositiveClickListener() {
    return view -> {
      dismiss();
    };
  }

  @Override protected View.OnClickListener onNegativeClickListener() {
    return null;
  }
}
