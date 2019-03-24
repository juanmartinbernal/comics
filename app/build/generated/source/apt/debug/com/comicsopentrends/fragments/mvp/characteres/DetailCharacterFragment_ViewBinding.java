// Generated code from Butter Knife. Do not modify!
package com.comicsopentrends.fragments.mvp.characteres;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.comicsopentrends.R;
import java.lang.IllegalStateException;
import java.lang.Override;

public class DetailCharacterFragment_ViewBinding implements Unbinder {
  private DetailCharacterFragment target;

  private View view7f080025;

  private View view7f080026;

  private View view7f080024;

  @UiThread
  public DetailCharacterFragment_ViewBinding(final DetailCharacterFragment target, View source) {
    this.target = target;

    View view;
    target.imgCharacterDetail = Utils.findRequiredViewAsType(source, R.id.imgCharacterDetail, "field 'imgCharacterDetail'", ImageView.class);
    target.txtNameCharacter = Utils.findRequiredViewAsType(source, R.id.txtNameCharacter, "field 'txtNameCharacter'", TextView.class);
    target.txtDescription = Utils.findRequiredViewAsType(source, R.id.txtDescription, "field 'txtDescription'", TextView.class);
    view = Utils.findRequiredView(source, R.id.btnDetail, "field 'btnDetail' and method 'seeDetail'");
    target.btnDetail = Utils.castView(view, R.id.btnDetail, "field 'btnDetail'", Button.class);
    view7f080025 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.seeDetail(p0);
      }
    });
    target.mPager = Utils.findRequiredViewAsType(source, R.id.pager, "field 'mPager'", ViewPager.class);
    view = Utils.findRequiredView(source, R.id.btnWiki, "method 'seeWiki'");
    view7f080026 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.seeWiki(p0);
      }
    });
    view = Utils.findRequiredView(source, R.id.btnComics, "method 'seeComics'");
    view7f080024 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.seeComics(p0);
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    DetailCharacterFragment target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.imgCharacterDetail = null;
    target.txtNameCharacter = null;
    target.txtDescription = null;
    target.btnDetail = null;
    target.mPager = null;

    view7f080025.setOnClickListener(null);
    view7f080025 = null;
    view7f080026.setOnClickListener(null);
    view7f080026 = null;
    view7f080024.setOnClickListener(null);
    view7f080024 = null;
  }
}
