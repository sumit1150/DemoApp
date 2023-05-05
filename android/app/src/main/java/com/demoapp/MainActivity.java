package com.demoapp;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import com.clevertap.android.sdk.CleverTapAPI;
import com.clevertap.react.CleverTapModule;
import com.facebook.react.ReactActivity;
import com.facebook.react.ReactActivityDelegate;
import com.facebook.react.defaults.DefaultNewArchitectureEntryPoint;
import com.facebook.react.defaults.DefaultReactActivityDelegate;

public class MainActivity extends ReactActivity {

  CleverTapAPI cleverTapAPI;

  /**
   * Returns the name of the main component registered from JavaScript. This is used to schedule
   * rendering of the component.
   */
  @Override
  protected String getMainComponentName() {
    return "DemoApp";
  }

  /**
   * Returns the instance of the {@link ReactActivityDelegate}. Here we use a util class {@link
   * DefaultReactActivityDelegate} which allows you to easily enable Fabric and Concurrent React
   * (aka React 18) with two boolean flags.
   */
  @Override
  protected ReactActivityDelegate createReactActivityDelegate() {
    return new DefaultReactActivityDelegate(
        this,
        getMainComponentName(),
        // If you opted-in for the New Architecture, we enable the Fabric Renderer.
        DefaultNewArchitectureEntryPoint.getFabricEnabled(), // fabricEnabled
        // If you opted-in for the New Architecture, we enable Concurrent React (i.e. React 18).
        DefaultNewArchitectureEntryPoint.getConcurrentReactEnabled() // concurrentRootEnabled
        );
  }

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    CleverTapModule.setInitialUri(getIntent().getData());
    cleverTapAPI=CleverTapAPI.getDefaultInstance(getApplicationContext());
  }

  @Override
  public void onNewIntent(final Intent intent) {
    super.onNewIntent(intent);
    /**
     * On Android 12, Raise notification clicked event when Activity is already running in activity backstack
     */
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
      cleverTapAPI.pushNotificationClickedEvent(intent.getExtras());
    }
  }
}
