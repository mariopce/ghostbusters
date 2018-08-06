package pl.saramak.geo.ghostbusters.wifi
import kotlinx.coroutines.experimental.Deferred
import pl.saramak.geo.ghostbusters.wifi.data.WifiData

interface WifiProvider {
    suspend fun getWifiList() : Set<WifiData>
}