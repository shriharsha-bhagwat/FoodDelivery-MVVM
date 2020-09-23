package com.kopa.me.driver.core.service


import android.Manifest
import android.app.*
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.graphics.Color
import android.location.Location
import android.os.Build
import android.os.IBinder
import android.os.Looper
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.android.gms.location.*
import com.google.android.gms.tasks.Task
import com.ironz.binaryprefs.Preferences
import com.kopa.me.driver.R
import com.kopa.me.driver.core.utils.AppConstants
import com.kopa.me.driver.domain.driver.DriverDutyStatusRequestModel
import com.kopa.me.driver.domain.driver.IDriverProfileRepository
import com.kopa.me.driver.presentation.ui.MainActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject


class LocationUpdateService() : Service() {

    private val repository: IDriverProfileRepository by inject(IDriverProfileRepository::class.java)
    private val preferences : Preferences by inject(Preferences::class.java)

    /**
     * The desired interval for location updates. Inexact. Updates may be more or less frequent.
     */
    private val UPDATE_INTERVAL_IN_MILLISECONDS: Long = 30000

    /**
     * The fastest rate for active location updates. Exact. Updates will never be more frequent than this value.
     */
    private val FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS = UPDATE_INTERVAL_IN_MILLISECONDS / 2

    /**
     * Provides access to the Fused Location Provider API.
     */
    lateinit var mFusedLocationClient: FusedLocationProviderClient

    /**
     * Stores parameters for requests to the FusedLocationProviderApi.
     */
    lateinit var mLocationRequest: LocationRequest

    /**
     * Callback for Location events.
     */
    lateinit var mLocationCallback: LocationCallback

    /**
     * Represents a geographical location.
     */
    private var mCurrentLocation: Location? = null

    /**
     * Tracks the status of the location updates request. Value changes when the user presses the Start Updates and Stop Updates buttons.
     */
    private var mRequestingLocationUpdates: Boolean = false

    override fun onCreate() {
        super.onCreate()
        try {
            mRequestingLocationUpdates = false
            mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

            createLocationCallback()
            createLocationRequest()

        } catch (e: Exception) {
        }
    }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {

        val action = intent.action
        if (action != null) when (action) {
            AppConstants.BROADCAST_MESSAGE_START_LOCATION_UPDATE -> {
                startForegroundService()
            }
            AppConstants.BROADCAST_MESSAGE_STOP_LOCATION_UPDATE -> {
                stopForegroundService()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    override fun onBind(intent: Intent): IBinder? {
        return null
    }

    /**
     * Sets up the location request. Android has two location request settings: `ACCESS_COARSE_LOCATION` and `ACCESS_FINE_LOCATION`. These
     * settings control the accuracy of the current location. This sample uses ACCESS_FINE_LOCATION, as defined in the AndroidManifest.xml.
     *
     *
     * When the ACCESS_FINE_LOCATION setting is specified, combined with a fast update interval (5 seconds), the Fused Location Provider API returns
     * location updates that are accurate to within a few feet.
     *
     *
     * These settings are appropriate for mapping applications that show real-time location updates.
     */
    private fun createLocationRequest() {
        mLocationRequest = LocationRequest()
        // Sets the desired interval for active location updates. This interval is
        // inexact. You may not receive updates at all if no location sources are available, or
        // you may receive them slower than requested. You may also receive updates faster than
        // requested if other applications are requesting location at a faster interval.
        mLocationRequest.interval = UPDATE_INTERVAL_IN_MILLISECONDS
        // Sets the fastest rate for active location updates. This interval is exact, and your
        // application will never receive updates faster than this value.
        mLocationRequest.fastestInterval = FASTEST_UPDATE_INTERVAL_IN_MILLISECONDS
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
    }

    /**
     * Creates a callback for receiving location events.
     */
    private fun createLocationCallback() {
        mLocationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                Log.e(AppConstants.TAG, "onLocationResult" + locationResult.lastLocation)
                mCurrentLocation = locationResult.lastLocation

                GlobalScope.launch {
                    val driverDutyStatusRequestModel = DriverDutyStatusRequestModel(
                        preferences.getString(
                            AppConstants.PREF_KEY_APP_TOKEN, ""
                        ).orEmpty(), preferences.getLong(
                            AppConstants.PREF_KEY_DRIVER_ID, -1L
                        ), "en", 0.0, 0.0
                    )
                    driverDutyStatusRequestModel.latitude = mCurrentLocation?.latitude ?: 0.0
                    driverDutyStatusRequestModel.longitude = mCurrentLocation?.longitude ?: 0.0
                    driverDutyStatusRequestModel.type = AppConstants.DRIVER_STATUS_UPDATE

                    repository.updateDriverDutyStatus(driverDutyStatusRequestModel)
                }
            }
        }
    }

    /**
     * Requests location updates from the FusedLocationApi. Note: we don't call this unless location runtime permission has been granted.
     */
    private fun startLocationUpdates() {
        if (ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        Log.e(AppConstants.TAG, " startLocationUpdates")
        mRequestingLocationUpdates = true
        mFusedLocationClient.requestLocationUpdates(
            mLocationRequest,
            mLocationCallback, Looper.myLooper()
        )
    }

    /**
     * Removes location updates from the FusedLocationApi.
     */
    private fun stopLocationUpdates() {

        if (!mRequestingLocationUpdates) {
            Log.e(AppConstants.TAG, "stopLocationUpdates: updates never requested, no-op.")
            return
        }
        Log.e(AppConstants.TAG, "stopLocationUpdates")
        mFusedLocationClient.removeLocationUpdates(mLocationCallback)
            .addOnCompleteListener { task: Task<Void?>? -> mRequestingLocationUpdates = false }
    }

    private fun startForegroundService() {
        Log.d(AppConstants.TAG, "Start foreground service.")

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createNotificationChannel("my_service", "Location Service")
        } else {

            // Create notification default intent.
            val intent = Intent()
            val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

            // Create notification builder.
            val builder = NotificationCompat.Builder(this)

            // Make notification show big text.
            val bigTextStyle = NotificationCompat.BigTextStyle()
            bigTextStyle.setBigContentTitle("Location Updates")
            bigTextStyle.bigText("Location Updates Are In Progress")
            // Set big text style.
            builder.setStyle(bigTextStyle)
            builder.setWhen(System.currentTimeMillis())
            builder.setSmallIcon(R.mipmap.ic_launcher)
            val largeIconBitmap =
                BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher)
            builder.setLargeIcon(largeIconBitmap)
            // Make the notification max priority.
            builder.priority = Notification.PRIORITY_HIGH
            // Make head-up notification.
            builder.setFullScreenIntent(pendingIntent, true)

            // Build the notification.
            val notification: Notification = builder.build()

            // Start foreground service.
            startForeground(1, notification)
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(channelId: String, channelName: String) {
        val resultIntent = Intent(this, MainActivity::class.java)

        // Create the TaskStackBuilder and add the intent, which inflates the back stack
        val stackBuilder: TaskStackBuilder = TaskStackBuilder.create(this)
        stackBuilder.addNextIntentWithParentStack(resultIntent)
        val resultPendingIntent: PendingIntent =
            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT)
        val chan =
            NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT)
        chan.lightColor = Color.BLUE
        chan.lockscreenVisibility = Notification.VISIBILITY_PRIVATE

        val manager = (getSystemService(NOTIFICATION_SERVICE) as NotificationManager)
        manager.createNotificationChannel(chan)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
        val notification: Notification = notificationBuilder.setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setContentTitle("App is running in background")
            .setPriority(NotificationManager.IMPORTANCE_MIN)
            .setCategory(Notification.CATEGORY_SERVICE)
            .setContentIntent(resultPendingIntent) //intent
            .build()

        val notificationManager = NotificationManagerCompat.from(this)
        notificationManager.notify(1, notificationBuilder.build())
        startForeground(1, notification)

        startLocationUpdates()
    }


    private fun stopForegroundService() {
        stopLocationUpdates()

        Log.d(AppConstants.TAG, "Stop foreground service.")

        // Stop foreground service and remove the notification.
        stopForeground(true)

        // Stop the foreground service.
        stopSelf()
    }
}