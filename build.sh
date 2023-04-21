#!/bin/bash

unset _JAVA_OPTIONS

### OPTIONS ###
JVM_DIR="/usr/lib/jvm/default"
SDK_DIR="${HOME}/Android/Sdk"
ANTRACK_PROJECT_DIR="${HOME}/AndroidStudioProjects/AnTrack/app/src/main/"
### OPTIONS ###

MODULES_PROJECT_DIR="`pwd`/app/src/main/"
JAR="$JVM_DIR/bin/jar"
JAVAC="$JVM_DIR/bin/javac"
CLASSPATH="$SDK_DIR/platforms/android-33/android.jar"
D8="$SDK_DIR/build-tools/31.0.0/d8 --release --min-api 23 --classpath $CLASSPATH"

#PROGUARD="$SDK_DIR/tools/proguard/bin/proguard.sh\
#    -libraryjars $CLASSPATH\
#    -keep public class org.antrack.app.modules.*.Module**\
#    -keepclassmembers public class org.antrack.app.modules.*.Module** { *; }\
#    -keep public class org.antrack.app.modules.Template**\
#    -keepclassmembers public class org.antrack.app.modules.Template** { *; }"

APP_DIR="org/antrack/app"
MODULES_SRC_DIR="$APP_DIR/modules/"
DEPEND_SOURCES="$APP_DIR/modLibs/*.java $APP_DIR/modules/*.java"
DEPEND_CLASSES="$APP_DIR/modLibs/*.class $APP_DIR/modules/*.class"

###

rm -rf ${ANTRACK_PROJECT_DIR}/assets/modules/*
cd ${MODULES_PROJECT_DIR}/java

for dir in ${MODULES_SRC_DIR}/*; do
    test -f $dir && continue;

    module=`basename $dir`

    echo "> Building $module... "

    $JAVAC ${dir}/*.java $DEPEND_SOURCES --class-path $CLASSPATH 2>&1 | grep -v '^Note:'
    $D8 ${dir}/*.class $DEPEND_CLASSES --lib $CLASSPATH
    $JAR cf ${ANTRACK_PROJECT_DIR}/assets/modules/$module.jar classes.dex
done

echo -n "> Clean up... "

rm classes.dex
find . -name \*.class | xargs rm -f

echo "done"

