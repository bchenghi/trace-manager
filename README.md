# trace-manager
Example usages of java-mutation-framework, trace-diff and test-io, for debugging research

## Setup
- Have maven, java 8 and java 11 (or above) installed.
- Set up maven and java 11 (or above) in environment variables.
- Run setup.bat
- Modify java-mutation-framework/sampleMicrobatConfig.json's java_home to point to java 8.
- Run Main class in trace-manager

## Execution
In tracemanager.TraceManager class, it contains a for loop that runs all test cases found in the specified project. Specific test cases can be specified by choosing a specific one from the list of test cases obtained using List.get(int).

## How it works
In tracemanager.TraceManager, the mutation framework is set up (microbat config file, project directory, etc) and the test cases in the project is obtained. It then runs the mutation framework, trace-diff, and test-io on each test case.<br/>
The methods to obtain various results, such as root cause of bugs, input and output of test cases, and PairList can be seen in the code.
