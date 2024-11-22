#!/bin/bash

export GRADLE_OPTS="-Xmx64m -Dorg.gradle.jvmargs='-Xmx1g'"
export DEFAULT_JVM_OPTS="-Xmx256m"

cd /DiplomaAnahit

./gradlew clean

./gradlew build

java -jar ./build/libs/DiplomaAnahit-0.0.1-SNAPSHOT.jar
