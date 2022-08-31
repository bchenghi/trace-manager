cd test-io
git submodule update --init --recursive
cd trace-model
mvn install --file pom.xml
ECHO trace-model install done
cd ..
cd trace-diff
mvn install --file pom.xml
ECHO trace-diff install done
cd ../..

cd java-mutation-framework
mvn install --file pom.xml
ECHO java-mutation-framework install done
cd ..
