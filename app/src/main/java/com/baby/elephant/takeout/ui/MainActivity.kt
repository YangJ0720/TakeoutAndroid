package com.baby.elephant.takeout.ui

import android.content.Intent
import android.os.Bundle
import com.baby.elephant.takeout.R
import io.flutter.app.FlutterFragmentActivity
import io.flutter.facade.Flutter
import io.flutter.facade.FlutterFragment
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugins.GeneratedPluginRegistrant
import io.flutter.view.FlutterMain
import io.flutter.view.FlutterView

/**
 * @author YangJ
 */
class MainActivity : FlutterFragmentActivity() {

    private lateinit var mFlutterFragment: FlutterFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        FlutterMain.startInitialization(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        GeneratedPluginRegistrant.registerWith(this)
        // 添加FlutterView
//        val view = Flutter.createView(this, lifecycle, "main")
//        val params = FrameLayout.LayoutParams(
//            FrameLayout.LayoutParams.MATCH_PARENT,
//            FrameLayout.LayoutParams.MATCH_PARENT
//        )
//        addContentView(view, params)
        // 添加FlutterFragment
        val transaction = supportFragmentManager.beginTransaction()
        mFlutterFragment = Flutter.createFragment("MainFragment")
        transaction.replace(R.id.someContainer, mFlutterFragment)
        transaction.commitNow()
        // 发送消息到flutter
//        EventChannel(view, "event").setStreamHandler(
//            object : EventChannel.StreamHandler {
//                override fun onListen(p0: Any?, eventSink: EventChannel.EventSink?) {
//                    eventSink?.success("我是Android客户端，你好吗？")
//                }
//
//                override fun onCancel(p0: Any?) {
//
//                }
//
//            })
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (!hasFocus) return
        // 监听flutter发送过来的消息
        MethodChannel(mFlutterFragment.view as FlutterView, "com.example.main")
            .setMethodCallHandler { call, result ->
                println("call = $call, result = $result")
                if ("startWebView" == call.method) {
                    startActivity(Intent(this, WebViewActivity::class.java))
                }
            }
    }

    /**
     * 处理flutter界面跳转点击返回键返回上一个界面
     */
    override fun onBackPressed() {
        val flutterView = mFlutterFragment.view as FlutterView
        if (flutterView == null) {
            super.onBackPressed()
        } else {
            flutterView.popRoute()
        }
    }
}
