package com.trespies.musicalumbrella

import android.app.Application
import android.content.pm.ApplicationInfo
import android.os.Build
import android.os.Debug
import com.scottyab.rootbeer.RootBeer
import java.io.File

public class Security constructor(private val app: Application, private val configuration: SecurityConfiguration) {

    public fun checkSecurity() {
        isDebuggable()
        isDebuggerAttached()
        validateAppName()
        validateAppSignature()
        validateAppInstaller()
        isRunningInEmulator()
        checkEmulatorFiles()
        validateRootedDevice()
    }

    private fun isDebuggable() {
        val isDebuggable = app.applicationInfo.flags and ApplicationInfo.FLAG_DEBUGGABLE != 0

        if (isDebuggable) {
            throw SecurityDebuggableException()
        }
    }

    private fun isDebuggerAttached() {
        val isDebuggerAttached = Debug.isDebuggerConnected() || Debug.waitingForDebugger()

        if (isDebuggerAttached) {
            throw SecurityDebuggerAttachedException()
        }
    }

    private fun validateAppName() {
        val isSamePackageName = app.packageName == configuration.packageName

        if (isSamePackageName) {
            throw SecurityPackageNameException()
        }

    }

    private fun validateAppSignature() {
        val appSignatureValidate = AppSignatureValidator.validate(app, configuration)

        if (appSignatureValidate != Result.VALID) {
            throw SecurityInvalidSignatureException()
        }
    }

    private fun validateAppInstaller() {
        val appInstallerValidate = AppInstallerValidator.validate(app)

        if (appInstallerValidate != Result.VALID) {
            throw SecurityInvalidInstallerException()
        }
    }

    private fun isRunningInEmulator() {
        var isEmulator = (Build.MANUFACTURER.contains("Genymotion")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.toLowerCase().contains("droid4x")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.HARDWARE == "goldfish"
                || Build.HARDWARE == "vbox86"
                || Build.HARDWARE.toLowerCase().contains("nox")
                || Build.FINGERPRINT.startsWith("generic")
                || Build.PRODUCT == "sdk"
                || Build.PRODUCT == "google_sdk"
                || Build.PRODUCT == "sdk_x86"
                || Build.PRODUCT == "vbox86p"
                || Build.PRODUCT.toLowerCase().contains("nox")
                || Build.BOARD.toLowerCase().contains("nox")
                || (Build.BRAND.startsWith("generic") &&    Build.DEVICE.startsWith("generic")))
        if (isEmulator) {
            throw SecurityEmulatorException()
        }
    }

    private val PIPES = arrayOf(
        "/dev/socket/qemud",
        "/dev/qemu_pipe"
    )
    private val X86_FILES = arrayOf(
        "ueventd.android_x86.rc",
        "x86.prop",
        "ueventd.ttVM_x86.rc",
        "init.ttVM_x86.rc",
        "fstab.ttVM_x86",
        "fstab.vbox86",
        "init.vbox86.rc",
        "ueventd.vbox86.rc"
    )
    private val ANDY_FILES = arrayOf(
        "fstab.andy",
        "ueventd.andy.rc"
    )
    private val NOX_FILES = arrayOf(
        "fstab.nox",
        "init.nox.rc",
        "ueventd.nox.rc"
    )
    private fun checkFiles(targets: Array<String>): Boolean {
        for (pipe in targets) {
            val file = File(pipe)
            if (file.exists()) {
                return true
            }
        }
        return false
    }

    private fun checkEmulatorFiles() {
        val isEmulatorFiles = (checkFiles(ANDY_FILES)
                //|| checkFiles(ANDY_FILES)
                || checkFiles(NOX_FILES)
                || checkFiles(X86_FILES)
                || checkFiles(PIPES))

        if (isEmulatorFiles) {
            throw SecurityEmulatorException()
        }
    }

    private fun validateRootedDevice() {
        val rootBeer = RootBeer(app)
        val isRooted = rootBeer.isRooted &&
                rootBeer.isRootedWithBusyBoxCheck
        if (isRooted) {
            throw SecurityRootedDeviceException()
        }
    }

}