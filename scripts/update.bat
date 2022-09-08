git pull origin main
cd test-io/trace-model
git pull origin main
call mvn install --file pom.xml
cd ../trace-diff
git pull origin main
call mvn install --file pom.xml
cd ..
git pull origin main
call mvn install --file pom.xml
cd ../java-mutation-framework
git pull origin main
call mvn install --file pom.xml
cd ..
call mvn compile