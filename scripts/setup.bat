cd test-io
git submodule update --init --recursive
cd trace-model
call mvn install --file pom.xml
ECHO trace-model install done
cd ..
cd trace-diff
call mvn install --file pom.xml
ECHO trace-diff install done
cd ..
call mvn install --file pom.xml
ECHO test-io install done
cd ..
cd java-mutation-framework
call mvn install --file pom.xml
ECHO java-mutation-framework install done
cd ..
