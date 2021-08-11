package com.trespies.musicalumbrella

import android.content.Context
import android.os.Build

public object AppInstallerValidator {

    enum class Installer(val id: String) {
        GOOGLE_PLAY_STORE(id = "com.android.vending"),
        AMAZON_APP_STORE(id = "com.amazon.venezia")
    }

    public fun validate(context: Context): Result {
        return if (context.verifyInstaller(Installer.GOOGLE_PLAY_STORE) || context.verifyInstaller(Installer.AMAZON_APP_STORE)) {
            Result.VALID
        } else {
            Result.INVALID
        }
    }

    private fun Context.verifyInstaller(installer: Installer): Boolean {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            packageManager.getInstallSourceInfo(packageName).originatingPackageName?.startsWith(installer.id) == true
        } else {
            packageManager.getInstallerPackageName(packageName)?.startsWith(installer.id) == true
        }
    }

    public fun getInstaller(context: Context): String? {
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            context.packageManager.getInstallSourceInfo(context.packageName).originatingPackageName
        } else {
            context.packageManager.getInstallerPackageName(context.packageName)
        }
    }
}