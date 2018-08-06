package pl.saramak.geo.ghostbusters

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import pl.saramak.geo.ghostbusters.wifi.WifiAndroidProvider

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        launch(UI) {
            val list =  WifiAndroidProvider(this@MainActivity).getWifiList();
            wifiData.text = ""
            list.forEach {
                wifiData.text = wifiData.text.toString() +" ${it.ssid} ${it.rssiLevel}  ${it.capability} ${it.BSSID} \n";
            }

        }
    }
}
