#!/bin/sh
java -Xmx800M -cp $HSQLDB_HOME/lib/hsqldb.jar org.hsqldb.server.Server --port 9001 --database.0 file:helloworld --dbname.0 HELLOWORLD
