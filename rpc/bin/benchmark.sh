#!/usr/bin/env bash

usage() {
    echo "Usage: ${PROGRAM_NAME} options dir"
    echo "options: [h|p|o|a]"
    echo "         -h ip address"
    echo "         -p port"
    echo "         -o output file path"
    echo "         -a other args"
    echo "dir: test module name"
}

build() {
    mvn --projects base-benchmark,base-server,base-client,${PROJECT_DIR} clean package
}

java_options() {
    JAVA_OPTIONS="-server -Xmx1g -Xms1g -XX:MaxDirectMemorySize=1g -XX:+UseG1GC"
    if [ "x${MODE}" = "xprofiling" ]; then
        JAVA_OPTIONS="${JAVA_OPTIONS} \
            -XX:+UnlockCommercialFeatures \
            -XX:+FlightRecorder \
            -XX:StartFlightRecording=duration=30s,filename=${PROJECT_DIR}.jfr \
            -XX:FlightRecorderOptions=stackdepth=256"
    fi
}

run() {
    if [ -d "${PROJECT_DIR}/target" ]; then
        JAR=`find ${PROJECT_DIR}/target/*.jar | head -n 1`
        CMD="java ${JAVA_OPTIONS} -Dserver.host=${SERVER} -Dserver.port=${PORT} -Djmh.output=${OUTPUT} ${OTHERARGS} -jar ${JAR}"
        echo
        echo "Run at ${PROJECT_DIR}, command is: ${CMD}"
        echo
        ${CMD}
    fi
}

PROGRAM_NAME=$0
SERVER="127.0.0.1"
PORT="9003"
OUTPUT=""
OPTIND=1
OTHERARGS=""

while getopts "h:p:o:a:" opt; do
    case "$opt" in
        h)
            SERVER=${OPTARG}
            ;;
        p)
            PORT=${OPTARG}
            ;;
        o)
            OUTPUT=${OPTARG}
            ;;
        a)
            OTHERARGS=${OPTARG}
            ;;
        ?)
            usage
            exit 0
            ;;
    esac
done

shift $((OPTIND-1))
PROJECT_DIR=$1

if [ ! -d "${PROJECT_DIR}" ]; then
    usage
    exit 0
fi

build
java_options
run
