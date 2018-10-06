package android.thakur.com.mobilesecurity.model

import android.location.Location
import android.os.Build


class UserLocation() {

    private var provider: String = ""
    private var time: Long = 0
    private var elapsedRealtimeNanos: Long = 0
    private var latitude = 0.0
    private var longitude = 0.0
    private var altitude = 0.0
    private var speed = 0.0f
    private var bearing = 0.0f
    private var horizontalAccuracyMeters = 0.0f
    private var verticalAccuracyMeters = 0.0f
    private var speedAccuracyMetersPerSecond = 0.0f
    private var bearingAccuracyDegrees = 0.0f

    constructor(currentLocation: Location) : this() {
        provider = currentLocation.provider as String
        time = currentLocation.time
        elapsedRealtimeNanos = currentLocation.elapsedRealtimeNanos
        latitude = currentLocation.latitude
        longitude = currentLocation.longitude
        altitude = currentLocation.altitude
        speed = currentLocation.speed
        bearing = currentLocation.bearing
        horizontalAccuracyMeters = currentLocation.accuracy

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            verticalAccuracyMeters = currentLocation.verticalAccuracyMeters
            speedAccuracyMetersPerSecond = currentLocation.speedAccuracyMetersPerSecond
            bearingAccuracyDegrees = currentLocation.bearingAccuracyDegrees
        }

    }

    override fun toString(): String {
        return "{\"location\":{\"provider\":$provider, \"time\":$time, \"elapsedRealtimeNanos\":$elapsedRealtimeNanos, \"latitude\":$latitude, \"longitude\":$longitude, \"altitude\":$altitude, \"speed\":$speed, \"bearing\":$bearing, \"horizontalAccuracyMeters\":$horizontalAccuracyMeters, \"verticalAccuracyMeters\":$verticalAccuracyMeters, \"speedAccuracyMetersPerSecond\":$speedAccuracyMetersPerSecond, \"bearingAccuracyDegrees\":$bearingAccuracyDegrees}"
    }


}