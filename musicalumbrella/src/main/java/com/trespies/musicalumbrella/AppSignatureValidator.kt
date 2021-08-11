package com.trespies.musicalumbrella

import android.content.Context
import android.content.pm.PackageManager
import android.content.pm.Signature
import android.os.Build
import android.util.Base64
import java.lang.Exception
import java.security.MessageDigest

public object AppSignatureValidator {

    public fun validate(context: Context, configuration: SecurityConfiguration): Result {
        getAppSignature(context)?.let { currentSignature ->
            return if (currentSignature.equals(configuration.expectedSignature, ignoreCase = true)) {
                Result.VALID
            } else { Result.INVALID }
        }
        return Result.UNKNOWN
    }

    private fun Context.getAppSignature(): Signature? = if (Build.VERSION.SDK_INT < 28) {
        packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNATURES).signatures.firstOrNull()
    } else {
        packageManager.getPackageInfo(packageName, PackageManager.GET_SIGNING_CERTIFICATES).signingInfo.apkContentsSigners.firstOrNull()
    }

    private fun Signature.string(): String? = try {
        val signatureBytes = toByteArray()
        val digest = MessageDigest.getInstance("SHA")
        val hash = digest.digest(signatureBytes)
        Base64.encodeToString(hash, Base64.NO_WRAP)
    } catch (exception: Exception) {
        null
    }

    /**
     * Get my app signature.
     */
    public fun getAppSignature(context: Context): String? {
        return context.getAppSignature()?.string()
    }

}