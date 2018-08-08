package pl.saramak.geo.ghostbusters

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import pl.saramak.geo.ghostbusters.wifi.WifiAndroidProvider
import android.net.wifi.WifiManager
import android.content.Intent
import android.support.v4.app.ActivityCompat.startActivityForResult
import android.os.Build
import android.content.Context.WIFI_SERVICE



class MainActivity : AppCompatActivity() {

    @SuppressLint("WifiManagerLeak")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val REQUEST_SCAN_ALWAYS_AVAILABLE = 11

        val wifiManager = getSystemService(Context.WIFI_SERVICE) as WifiManager

        if (Build.VERSION.SDK_INT >= 18 && !wifiManager.isScanAlwaysAvailable) {
            startActivityForResult(Intent(WifiManager.ACTION_REQUEST_SCAN_ALWAYS_AVAILABLE), REQUEST_SCAN_ALWAYS_AVAILABLE)
        }
        launch(UI) {
            val list =  WifiAndroidProvider(this@MainActivity).getWifiList();
            wifiData.text = ""
            list.forEach {
                wifiData.text = wifiData.text.toString() +" ${it.ssid} ${it.rssiLevel}  ${it.capability} ${it.BSSID} \n";
            }

        }
    }
}
