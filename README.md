# MobileSecurity-Android
Simple android library, collecting logs and data and storing them securely in DB

To integrate this module, just add below dependencies:

*1. build.gradle*
```
    // dependencies for mobile security
    implementation "android.arch.persistence.room:runtime:1.1.1"
    implementation project(':mobilesecurity')
```

*2. Android.manifest*
```
    <uses-permission android:name="android.permission.ACCESS_FINE_LOCATION"/>
    <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
    <uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
```

*3. MainActivity.kt*
 > create object
```
lateinit var mobileSecurity:MobileSecurity
```

 > In PostCreate, initialize the framework and register the main activity
 ```
        mobileSecurity = MobileSecurity()
        mobileSecurity.register(this.applicationContext, this)
```
 > Then override the request permissions, and pass those permissions to the module
 ```
 override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        mobileSecurity.requestPermissionsResult(requestCode, permissions, grantResults)
    }
```

So the complete MainActivity will be like below:

```
class MainActivity : AppCompatActivity() {

    lateinit var mobileSecurity:MobileSecurity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mobileSecurity = MobileSecurity()
        mobileSecurity.register(this.applicationContext, this)

    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        mobileSecurity.requestPermissionsResult(requestCode, permissions, grantResults)
    }
}
```
