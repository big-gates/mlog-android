//package com.kychan.mlog
//
//import android.content.Intent
//import android.net.Uri
//import android.os.Bundle
//import androidx.appcompat.app.AppCompatActivity
//import androidx.databinding.DataBindingUtil
//import com.google.android.material.dialog.MaterialAlertDialogBuilder
//import com.google.firebase.ktx.Firebase
//import com.google.firebase.remoteconfig.FirebaseRemoteConfig
//import com.google.firebase.remoteconfig.ktx.remoteConfig
//import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
//import com.kychan.mlog.databinding.ActivitySplashBinding
//
//class SplashActivity : AppCompatActivity() {
//
//    private lateinit var remoteConfig: FirebaseRemoteConfig
//    private lateinit var binding: ActivitySplashBinding
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = DataBindingUtil.setContentView(this, R.layout.activity_splash)
//        binding.lifecycleOwner = this
//
//        remoteConfig = Firebase.remoteConfig
//        val configSettings = remoteConfigSettings {
//            minimumFetchIntervalInSeconds = if (BuildConfig.DEBUG) 30 else ONE_DAY_SECONDS
//        }
//        remoteConfig.setConfigSettingsAsync(configSettings)
//
//        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
//
//        fetchVersion()
//    }
//
//    private fun fetchVersion() {
//        val localVersion = BuildConfig.VERSION_NAME
//        remoteConfig.fetchAndActivate()
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val remoteAppVersion = remoteConfig.getString(KEY_LATEST_VERSION)
//                    if (localVersion >= remoteAppVersion) {
//                        startMainActivity()
//                    } else {
//                        showUpdateDialog()
//                    }
//                } else {
//                    startMainActivity()
//                }
//            }
//    }
//
//    private fun showUpdateDialog() {
//        val updateDialog = MaterialAlertDialogBuilder(this)
//            .setTitle(getString(R.string.dialog_update_title))
//            .setMessage(getString(R.string.dialog_update_sub_title))
//            .setCancelable(false)
//            .setNegativeButton(getString(R.string.later)) { _, _ ->
//                startMainActivity()
//            }
//            .setPositiveButton(getString(R.string.update)) { _, _ ->
//                startMainActivity()
//                val intent = Intent(Intent.ACTION_VIEW)
//                    .setData(Uri.parse("market://details?id=" + BuildConfig.APPLICATION_ID))
//
//                startActivity(intent)
//            }
//            .create()
//
//        updateDialog.show()
//    }
//
//    private fun startMainActivity() {
//        startActivity(MainActivity.getIntent(this))
//        finish()
//    }
//
//    companion object {
//        private const val ONE_DAY_SECONDS = 60 * 60 * 24L
//
//        private const val KEY_LATEST_VERSION = "latest_version"
//    }
//}
