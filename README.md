This is the standard set of modules for [AnTrack](https://github.com/zobnin/antrack). Almost every module can be invoked by name and its arguments.

## List of modules/commands

* `alarm` - to play an alarm sound for 10 seconds;
* `apps` - writes a list of installed applications to the `apps` file;
* `audio <SECONDS>` - records audio for the specified number of seconds and puts it in the file `audio/DATE.3gp`;
* `camera [front|back]` - takes a picture with the front or rear camera and records it in the file `camera/DATE.jpg`;
* `command <COMMAND>` - executes the specified console command and writes the result to the file `cmdout`;
* `contacts` - writes a list of contacts to the file `contacts`;
* `dial <NUMBER>` - makes a call to the specified number;
* `dumpsms` - writes SMS list to `sms/inbox` and sms/sent` files;
* `hide [on|off]` - shows or hides the application icon;
* `info` - writes information about the device to the file `info`;
* `locate` - writes the current location to the file `location` (records are added one by one);
* `calls` - logs incoming and outgoing calls to the file `calls` (called automatically during the call);
* `notify <TEXT>` - shows a notification with the specified text;
* `play <PATH>` - plays the specified audio file;
* `screenshot` - takes a screenshot and writes it to the file `screenshots/DATE.png` (requires root rights);
* `sms <NUMBER> <TEXT>` - sends a text to the specified number;
* `startapp <PACKAGE_NAME>` - launches the application with the specified package name;
* `status` - records the current state of the device in the `status` file;
* `wipesd` - deletes the contents of the memory card (not tested).

## Build/install

At the moment the build and installation of the scripts is done using the `build.sh` script. Change the lines in this script specifying the correct paths to the Android SDK and the AnTrack project directory.
