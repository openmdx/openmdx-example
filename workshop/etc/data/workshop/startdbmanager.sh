#!/bin/sh
java -cp $HSQLDB_HOME/lib/hsqldb.jar org.hsqldb.util.DatabaseManagerSwing --url jdbc:hsqldb:hsql://127.0.0.1:9002/WORKSHOP --user sa --password "manager99"
