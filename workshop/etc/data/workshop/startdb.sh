#!/bin/sh

java -cp ../../../../opt/hsqldb/jre-1.8/hsqldb-2/lib/hsqldb.jar org.hsqldb.server.Server --port 9002 --database.0 file:workshop --dbname.0 WORKSHOP
