#!/usr/bin/env bash

SELF_JAR=`echo $JORAM_HOME/target/joramexperiment-*-shaded.jar`

CMD="java -cp $SELF_JAR $LOCAL_RUNTIME_TMP $LOGGING_CONFIG edu.snu.mist.examples.$*"

case $1 in
  PahoDemo) ;;
  *)
    echo "Invalid input."
    exit 1;;
esac

echo $CMD
$CMD
