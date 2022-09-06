git pull origin main
git submodule update
cd test-io
call mvn install --file pom.xml
cd ../java-mutation-framework
call mvn install --file pom.xml
cd ..
call mvn compile