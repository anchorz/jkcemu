#!/bin/sh
ME=$(dirname $(readlink -f $0))
java -classpath $ME/../src jkcemu.Main $*

