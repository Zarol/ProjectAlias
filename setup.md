# Setup (9/12/2016)
[Return to README](README.md)  
Setup instructions for Windows 7 x64.

## Eclipse
Download [Eclipse](https://eclipse.org/downloads/). 
Follow the instructions presented to install.

## Android ADT for Eclipse
Start Eclipse and select `Help -> Install New Software`. Click `Add` on the top right corner.
Within the Add Repository dialog enter "ADT Plugin" for name and the following URL for Location: 
`https://dl-ssl.google.com/android/eclipse/`. Click `OK`. Select "Developer Tools" and
click `Next` until `Finish`. When the installation completes, restart Eclipse.

## Android SDKs
Go to `Window -> Android SDK Manager`. Download the suggested packages, however be sure to have
the SDK Platform and Google APIs selected for Android 4.4.2 (API 19).

## Android Emulator
Go to `Window -> Android Virtual Device Manager`. Create a new AVD with the following profile:
Target Android 4.4.2, CPU/ABI Intel Atom (x86). Everything else is optional, I personally use the Nexus 5 as my
device, with no skin, 768MB of RAM, and using host GPU for emulation.

## Import Project
Within Eclipse, go to `File -> Import -> Gradle -> Gradle Project`. Click browse and navigate to the
root folder of this project and click `Finish`. Gradle will download the required dependencies which
might take a couple of minutes.

## Running the Project
### Desktop
Right-click the desktop project folder, `Run As -> Java Application`. Select DesktopLauncher.java.

### Android
Run your android device. Right-click the android project folder, `Run As -> Android Application`.