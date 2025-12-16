package com.fugood.reactnativeintentlauncher

import android.app.Activity
import android.content.Intent
import android.content.ComponentName
import android.net.Uri
import android.os.Bundle
import com.facebook.react.bridge.ActivityEventListener
import com.facebook.react.bridge.BaseActivityEventListener
import com.facebook.react.bridge.Promise
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.bridge.ReadableMap
import com.facebook.react.bridge.WritableMap
import com.facebook.react.bridge.Arguments
import com.facebook.react.module.annotations.ReactModule

@ReactModule(name = ReactNativeIntentLauncherModule.NAME)
class ReactNativeIntentLauncherModule(reactContext: ReactApplicationContext) :
  NativeReactNativeIntentLauncherSpec(reactContext), ActivityEventListener {

  private var mPromise: Promise? = null

  init {
    reactContext.addActivityEventListener(this)
  }

  override fun getName(): String {
    return NAME
  }

  private fun createIntentFromParams(params: ReadableMap): Intent {
    val intent = Intent()

    if (params.hasKey("action")) {
      intent.action = params.getString("action")
    }

    if (params.hasKey("data")) {
      intent.data = Uri.parse(params.getString("data"))
    }

    if (params.hasKey("type")) {
      if (params.hasKey("data")) {
        intent.setDataAndType(Uri.parse(params.getString("data")), params.getString("type"))
      } else {
        intent.type = params.getString("type")
      }
    }

    if (params.hasKey("category")) {
      intent.addCategory(params.getString("category"))
    }

    if (params.hasKey("packageName")) {
      if (params.hasKey("className")) {
        intent.component = ComponentName(
          params.getString("packageName")!!,
          params.getString("className")!!
        )
      } else {
        intent.setPackage(params.getString("packageName"))
      }
    }

    if (params.hasKey("flags")) {
      intent.flags = params.getInt("flags")
    }

    if (params.hasKey("extra")) {
      val extra = params.getMap("extra")
      val bundle = Arguments.toBundle(extra)
      if (bundle != null) {
        intent.putExtras(bundle)
      }
    }

    return intent
  }

  private fun bundleToMap(bundle: Bundle): WritableMap {
    val map = Arguments.createMap()
    for (key in bundle.keySet()) {
      val value = bundle.get(key)
      when (value) {
        is String -> map.putString(key, value)
        is Int -> map.putInt(key, value)
        is Double -> map.putDouble(key, value)
        is Boolean -> map.putBoolean(key, value)
        is Bundle -> map.putMap(key, bundleToMap(value))
        else -> map.putString(key, value.toString())
      }
    }
    return map
  }

  override fun startActivity(params: ReadableMap, promise: Promise) {
    val activity = currentActivity
    if (activity == null) {
      promise.reject("ACTIVITY_NOT_FOUND", "Activity doesn't exist")
      return
    }

    try {
      val intent = createIntentFromParams(params)
      mPromise = promise
      activity.startActivityForResult(intent, REQUEST_CODE)
    } catch (e: Exception) {
      mPromise = null
      promise.reject("START_ACTIVITY_ERROR", e.message)
    }
  }

  override fun startService(params: ReadableMap, promise: Promise) {
    val context = reactApplicationContext
    try {
      val intent = createIntentFromParams(params)
      val foreground = params.hasKey("foreground") && params.getBoolean("foreground")
      if (foreground && android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
        context.startForegroundService(intent)
      } else {
        context.startService(intent)
      }
      promise.resolve(null)
    } catch (e: Exception) {
      promise.reject("START_SERVICE_ERROR", e.message)
    }
  }

  override fun onActivityResult(activity: Activity?, requestCode: Int, resultCode: Int, data: Intent?) {
    if (requestCode == REQUEST_CODE) {
      if (mPromise != null) {
        val result = Arguments.createMap()
        result.putInt("resultCode", resultCode)
        if (data != null) {
          result.putString("data", data.dataString)
          if (data.extras != null) {
            result.putMap("extra", bundleToMap(data.extras!!))
          }
        }
        mPromise?.resolve(result)
        mPromise = null
      }
    }
  }

  override fun onNewIntent(intent: Intent?) {
    // No-op
  }

  companion object {
    const val NAME = "ReactNativeIntentLauncher"
    const val REQUEST_CODE = 1234
  }
}
