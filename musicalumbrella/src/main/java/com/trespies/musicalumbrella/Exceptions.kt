package com.trespies.musicalumbrella

public open class SecurityException(message: String) : Exception(message)
public class SecurityDebuggableException: SecurityException("You cannot run this app when is debuggable")
public class SecurityDebuggerAttachedException: SecurityException("You cannot run this app when debugger is attached")
public class SecurityPackageNameException: SecurityException("You cannot run this app, the name is changed")
public class SecurityInvalidSignatureException: SecurityException("You cannot run this app, signature is not valid")
public class SecurityInvalidInstallerException: SecurityException("You cannot run this app, installer it not valid")
public class SecurityEmulatorException: SecurityException("You cannot run this app in an emulator")
public class SecurityRootedDeviceException: SecurityException("You cannot run this app on a rooted device")