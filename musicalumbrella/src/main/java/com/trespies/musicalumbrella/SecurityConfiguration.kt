package com.trespies.musicalumbrella

public interface SecurityConfiguration {
    val packageName: String
    val expectedSignature: String
    val checkIsDebuggable: Boolean
        get() = true
    val ckeckIsDebuggerAttached: Boolean
        get() = true
    val checkAppName: Boolean
        get() = true
    val checkAppSignature: Boolean
        get() = true
    val checkAppInstaller: Boolean
        get() =  true
    val checkRunInEmulator: Boolean
        get() = true
    val checkRootedDevice: Boolean
        get() = true

}