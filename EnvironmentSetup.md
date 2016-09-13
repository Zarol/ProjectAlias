# Environment Setup (9/12/2016)
[Return to README](README.md)

Setup instructions for Windows 7 x64.

## Android SDK
Download [Android Studio](https://developer.android.com/studio/index.html#downloads).
Follow the instructions presented to install. Note the path of your Android SDK, it should be something 
like `C:\Users\[USERNAME]\AppData\Local\Android\sdk`.

## Eclipse
Download [Eclipse](https://eclipse.org/downloads/). 
Follow the instructions presented to install.

## Import Project
Within Eclipse, go to `File -> Import -> Gradle -> Gradle Project`. Click browse and navigate to the
root folder of this project and click `Finish`. Gradle will download the required dependencies which
might take a couple of minutes.

## Running the Project
### Desktop
Right-click the desktop project folder, `Run As -> Java Application`. Select DesktopLauncher.java.

### Android
Make sure an android device is connected and it shows up in DDMS (From Android Studio: 
`Tools -> Android -> Android Device Monitor`). Right-click the android project folder, `Run As ->
Android Application`.