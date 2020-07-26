package jp.yuiki.flutter_kotlin_native_example

import androidx.annotation.NonNull
import io.flutter.embedding.android.FlutterActivity
import io.flutter.embedding.engine.FlutterEngine
import io.flutter.plugin.common.MethodChannel
import jp.yuiki.common.testApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : FlutterActivity() {
    override fun configureFlutterEngine(@NonNull flutterEngine: FlutterEngine) {
        super.configureFlutterEngine(flutterEngine)

        MethodChannel(flutterEngine.dartExecutor.binaryMessenger, "api").setMethodCallHandler { _, result ->
            testApi {
                GlobalScope.launch(Dispatchers.Main) {
                    result.success(it.size.toString())
                }
            }
        }
    }
}
