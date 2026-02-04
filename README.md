# Tasks & Lists

An open source task app for Android forked from OpenTasks (<https://github.com/dmfs/opentasks>).

> [!important]
> Disclaimer:
>
> This repository and the application "Tasks & Lists" are a fork of OpenTasks (Copyright (c) Marten Gajda 2013-2015, licensed under Apache2). The fork was created, because development of OpenTasks has been stopped and the original app may stop working on newer Android versions.
>
> One key aspect of OpenTasks, the original app, is the support by the app DAVx5 (<https://github.com/bitfireAT/davx5-ose>). DAVx5 is able to synchronize tasks from CALDav servers to OpenTasks. For this, DAVx5 uses the OpenTasks API and finds the installed app by the application id.
>
> The intention of the Tasks & Lists app is to be a drop-in replacement for OpenTasks. It was renamed and the logo was changed to fulfill license and copyright requirements. The original application id 'org.dmfs.tasks' was kept as is, to preserve the synchronization possibility with DAVx5. The downside of this approach is, that the original app, OpenTasks, and this app, Tasks & Lists, cannot be installed in parallel on the same device. Again, this is NOT to deceive users, but to preserve the synchronization possibility with DAVx5. If in doubt, use the original app only.

There are no road maps, active development, or new features planned. Just Android SDK, Gradle, and dependency updates to allow execution on current Android devices.

## Build

This section describes how the .apk file of the app can be built.

### Prerequisites

* Java >= 21.0.9
* Gradle >= 8.14.1
* Android SDK 36
* Signing keys

### Running Gradle

* Make sure you have the following environment variables set:
  * JAVA_HOME
  * GRADLE_HOME
  * Java und Gradle "bin" folders in PATH
  * ANDROID_HOME
  * ANDROID_SDK_HOME
* For signing also set:
  * RELEASE_KEYSTORE
  * RELEASE_KEYSTORE_PASSWORD
  * RELEASE_KEY_ALIAS
  * RELEASE_KEY_PASSWORD
* Run: `gradle clean assembleRelease`
* The .apk file will be in 'tasksandlists/build/outputs/apk/release'

## Download

Pre-built .apk files can be downloaded from the repository releases.
