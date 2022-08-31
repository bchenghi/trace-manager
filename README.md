# trace-manager
Example usages of java-mutation-framework, trace-diff and test-io, for debugging research

## Setup
- Have maven, java 11 (or above) installed and set up in environment variables.
- Run setup.bat
- Modify java-mutation-framework/sampleMicrobatConfig.json's java_home to point to java 8.
- Run Main class in trace-manager

## Execution
In tracemanager.TraceManager class, it contains a for loop that runs all test cases found in the specified project. Specific test cases can be specified by choosing a specific one from the list of test cases obtained using List.get(int).
