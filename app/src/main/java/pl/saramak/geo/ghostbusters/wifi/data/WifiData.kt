package pl.saramak.geo.ghostbusters.wifi.data

data class WifiData(val ssid:String, val rssiLevel : Int, val frequency: Int, var BSSID: String, var capability:String){
    override fun hashCode(): Int {
        return ssid.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        var otherSsid = other as? WifiData
        return otherSsid?.ssid == ssid ?: false
    }
}
