// Generated code from Butter Knife. Do not modify!
package com.example.classdream;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.view.ViewGroup;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class AuthFragment_login_ViewBinding implements Unbinder {
  private AuthFragment_login target;

  private View view2131296801;

  @UiThread
  public AuthFragment_login_ViewBinding(final AuthFragment_login target, View source) {
    this.target = target;

    View view;
    target.caption = Utils.findRequiredViewAsType(source, R.id.caption, "field 'caption'", VerticalTextView_login.class);
    view = Utils.findRequiredView(source, R.id.root, "field 'parent' and method 'unfold'");
    target.parent = Utils.castView(view, R.id.root, "field 'parent'", ViewGroup.class);
    view2131296801 = view;
    view.setOnClickListener(new DebouncingOnClickListener() {
      @Override
      public void doClick(View p0) {
        target.unfold();
      }
    });
  }

  @Override
  @CallSuper
  public void unbind() {
    AuthFragment_login target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.caption = null;
    target.parent = null;

    view2131296801.setOnClickListener(null);
    view2131296801 = null;
  }
}
