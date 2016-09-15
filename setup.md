# Setup (9/12/2016)
[Return to README](README.md)

Setup instructions for Windows 7 x64.

## Android SDK
Download [Android Studio](https://developer.android.com/studio/index.html#downloads).
Follow the instructions presented to install. Note the path of your Android SDK, it should be something 
like `C:\Users\[USERNAME]\AppData\Local\Android\sdk`.

## Eclipse
Download [Eclipse](https://eclipse.org/downloads/). 
Follow the instructions presented to install.

## Android ADT for Eclipse
Start Eclipse and select `Help -> Install New Software`. Click `Add` on the top right corner.
Within the Add Repository dialog enter "ADT Plugin" for name and the following URL for Location: 
`https://dl-ssl.google.com/android/eclipse/`. Click `OK`. Select "Developer Tools" and
click `Next` until `Finish`. When the installation completes, restart Eclipse.

## Import Project
Within Eclipse, go to `File -> Import -> Gradle -> Gradle Project`. Click browse and navigate to the
root folder of this project and click `Finish`. Gradle will download the required dependencies which
might take a couple of minutes.

## Running the Project
### Desktop
Right-click the desktop project folder, `Run As -> Java Application`. Select DesktopLauncher.java.

### Android
[Might need to update these instructions with an emulator??]
Make sure an android device is connected and it shows up in DDMS (From Android Studio: 
`Tools -> Android -> Android Device Monitor`). Right-click the android project folder, `Run As ->
Android Application`.