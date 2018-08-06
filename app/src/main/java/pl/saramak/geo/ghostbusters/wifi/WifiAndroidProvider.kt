package pl.saramak.geo.ghostbusters.wifi

import android.annotation.SuppressLint
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.wifi.WifiManager
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import pl.saramak.geo.ghostbusters.wifi.data.WifiData
import java.util.concurrent.TimeUnit
import kotlin.coroutines.experimental.suspendCoroutine


class WifiAndroidProvider(val context: Context) : WifiProvider {

    @SuppressLint("MissingPermission")
    override suspend fun getWifiList(): Set<WifiData> = suspendCoroutine<Set<WifiData>> { it ->
        val wManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
        if (!wManager.isWifiEnabled){
            it.resumeWithException(IllegalStateException("Wifi is not enabled"))
        }
        wManager.startScan()
        val list = mutableSetOf<WifiData>()

        val wifiResultResiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context?, intent: Intent?) {
                val results = wManager.getScanResults()
                for (result in results) {
                    list.add(WifiData(result.SSID, result.level, result.frequency, result.BSSID, result.capabilities))
                }
                Thread.sleep(TimeUnit.SECONDS.toMillis(10))
                for (result in results) {
                    list.add(WifiData(result.SSID, result.level, result.frequency, result.BSSID, result.capabilities))
                }
                it.resume(list);
                context!!.unregisterReceiver(this);
            }
        }
        context.registerReceiver(wifiResultResiver, IntentFilter(WifiManager.SUPPLICANT_STATE_CHANGED_ACTION))

    }

}
