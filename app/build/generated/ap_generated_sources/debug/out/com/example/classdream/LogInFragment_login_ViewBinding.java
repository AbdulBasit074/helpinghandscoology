// Generated code from Butter Knife. Do not modify!
package com.example.classdream;

import android.support.annotation.UiThread;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import butterknife.internal.Utils;
import java.lang.IllegalStateException;
import java.lang.Override;

public class LogInFragment_login_ViewBinding extends AuthFragment_login_ViewBinding {
  private LogInFragment_login target;

  @UiThread
  public LogInFragment_login_ViewBinding(LogInFragment_login target, View source) {
    super(target, source);

    this.target = target;

    target.views = Utils.listOf(
        Utils.findRequiredViewAsType(source, R.id.email_input_edit, "field 'views'", TextInputEditText.class), 
        Utils.findRequiredViewAsType(source, R.id.password_input_edit, "field 'views'", TextInputEditText.class));
  }

  @Override
  public void unbind() {
    LogInFragment_login target = this.target;
    if (target == null) throw new IllegalStateException("Bindings already cleared.");
    this.target = null;

    target.views = null;

    super.unbind();
  }
}
