package com.trespies.musicalumbrella

public open class SecurityException(message: String) : Exception(message)
public class SecurityDebuggableException: SecurityException("You cannot run this app when is debuggable")
public class SecurityDebuggerAttachedException: SecurityException("You cannot run this app when debugger is attached")
public class SecurityPackageNameException(appName: String): SecurityException("You cannot run this app, the name is changed: $appName")
public class SecurityInvalidSignatureException(signature: String): SecurityException("You cannot run this app, signature is not valid: $signature")
public class SecurityInvalidInstallerException(installer: String): SecurityException("You cannot run this app, installer it not valid: $installer")
public class SecurityEmulatorException(emulator: String): SecurityException("You cannot run this app in an emulator $emulator")
public class SecurityRootedDeviceException: SecurityException("You cannot run this app on a rooted device")