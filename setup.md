# Setup (9/12/2016)
[Return to README](README.md)  
Setup instructions for Windows 7 x64.

## Table of Contents
+ [Eclipse](#eclipse)
+ [Android ADT for Eclipse](#android-adt-for-eclipse)
+ [Android SDKs](#android-sdks)
+ [Android Emulator](#android-emulator)
+ [Import Project](#import-project)
+ [Running the Project](#running-the-project)

## Eclipse
Download [Eclipse](https://eclipse.org/downloads/). 
Follow the instructions presented to install.

## Android ADT for Eclipse
Start Eclipse and select `Help -> Install New Software`. Click `Add` on the top right corner.
Within the Add Repository dialog enter "ADT Plugin" for name and the following URL for Location: 
`https://dl-ssl.google.com/android/eclipse/`. Click `OK`. Select "Developer Tools" and
click `Next` until `Finish`. When the installation completes, restart Eclipse.

## Android SDKs
If you do not have an SDK:  
A welcome screen will appear when Eclipse has restarted, select "Install new SDK" and "Install the latest...." 
Click `Next` and follow the on screen instructions to finish the installation. Continue to "If you already
have a previous SDK installed."

If you already have a previous SDK installed:  
Go to `Window -> Android SDK Manager`. Download the suggested packages, however be sure to have
the SDK Platform and Google APIs selected for Android 4.4.2 (API 19) and Android SDK Build tools version 23.0.3.

## Android Emulator
Go to `Window -> Android Virtual Device Manager`. Create a new AVD with the following profile:
Target Android 4.4.2, CPU/ABI Intel Atom (x86). Everything else is optional, I personally use the Nexus 5 as my
device, with no skin, 768MB of RAM, and using host GPU for emulation.

If you are having problems with "HAXM" in running your emulator, go to the Android SDK Manager and under Extras
install the `Intel x86 Emulator Accelerator (HAXM installer)`. Next go to the directory of your Android SDK,
under `extras\intel\Hardware_Accelerated_Execution_Manager` run `intelhaxm-android.exe` and follow the instructions.
Your emulator should now work.

## Import Project
Clone the project and create a file within the root directory called `local.properties`. Within the file
write `sdk.dir=%PATH_TO_ANDROID_SDK%` and save the file. Within Eclipse, go to 
`File -> Import -> Gradle -> Gradle Project`. Click browse and navigate to the root folder of this project 
and click `Finish`. Gradle will download the required dependencies which might take a couple of minutes.

## Running the Project
### Desktop
Right-click the desktop project folder, `Run As -> Java Application`. Select DesktopLauncher.java.

First Time Running:
The project will fail the first time running, after failing, go to `Run -> Run Configurations -> DesktopLauncher`.
Navigate to the `Arguments` tab and under `Working directory` select `Other` and set the path to
`%PATH_TO_PROJECT%\ProjectAlias\android\assets`. Click `Apply` and run the project again.

### Android
Run your android device. Right-click the android project folder, `Run As -> Android Application`.

First Time Running:
Right-click ProjectAlias-android from the Package Explorer and click `Properties`. Click `Java Build Path` and then
the `Order and Export` tab. Ensure that `Project and External Dependencies`, `Android Private Libraries`, and 
`Android Dependencies` are checked. Click `Apply` and run the project again.
