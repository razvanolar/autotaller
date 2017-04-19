@echo off
mvn compile -e & cd target\classes & java -cp \..\jars\libs com.autotaller.app.AutoTallerApplication & cd ..\..\