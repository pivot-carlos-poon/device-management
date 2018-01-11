#!/bin/sh

cd $INPUT_RESOURCE
./gradlew build
cp build/libs/deviceManagement*.jar ../device-package/