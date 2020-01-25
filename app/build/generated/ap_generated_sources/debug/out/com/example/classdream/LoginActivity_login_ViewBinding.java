// Generated code from Butter Knife. Do not modify!
package com.example.classdream;

import android.support.annotation.CallSuper;
import android.support.annotation.UiThread;
import android.view.View;
import android.widget.ImageView;
import butterknife.Unbinder;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LoginActivity_login_ViewBinding implements Unbinder {
  private LoginActivity_login target;

  @UiThread
  public LoginActivity_login_ViewBinding(LoginActivity_login target) {
    this(target, target.getWindow().getDecorView());
  }

  @UiThread
  public LoginActivity_login_ViewBinding(LoginActivity_login target, View source) {
    this.target = target;

    target.sharedElements = Utils.listOf(
        Utils.findRequiredViewAsType(source, R.id.logo, "field 'sharedElements'", ImageView.class));
  }

  @Override
  @CallSuper
  public void unbind() {
    LoginActivity_login target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.sharedElements = null;
  }
}
