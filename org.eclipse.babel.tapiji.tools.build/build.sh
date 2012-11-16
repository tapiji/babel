#!/bin/bash

ECLIPSE_BASE=~/dev/tapiji/build/eclipse

java -jar $ECLIPSE_BASE/plugins/org.eclipse.equinox.launcher_1.2.0.v20110502.jar \
     -application org.eclipse.ant.core.antRunner \
     -buildfile build.xml \
     -DbaseLocation=$ECLIPSE_BASE
