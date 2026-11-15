package com.rynekryz.graphyte.data

import android.content.Context
import org.json.JSONObject

class CameraStore(context: Context) {
    private val prefs = context.getSharedPreferences("graphyte", Context.MODE_PRIVATE)

    fun load(): LastCamera? = runCatching {
        val o = JSONObject(prefs.getString("camera", "{}") ?: "{}")
        if (o.isNull("lat") || o.isNull("lon")) null
        else LastCamera(GeoPoint(o.getDouble("lat"), o.getDouble("lon")), o.getDouble("zoom"))
    }.getOrNull()

    fun save(camera: LastCamera) {
        val o = JSONObject()
            .put("lat", camera.point.lat)
            .put("lon", camera.point.lon)
            .put("zoom", camera.zoom)
        prefs.edit().putString("camera", o.toString()).apply()
    }
}
