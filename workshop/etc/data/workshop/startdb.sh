#!/bin/sh
java -Xmx800M -cp $HSQLDB_HOME/lib/hsqldb.jar org.hsqldb.server.Server --port 9002 --database.0 file:workshop --dbname.0 WORKSHOP
