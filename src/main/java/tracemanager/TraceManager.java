package tracemanager;

import jmutation.model.MicrobatConfig;
import jmutation.model.TestCase;
import testio.TestIOFramework;
import testio.model.IOModel;
import testio.model.TestIO;
import jmutation.mutation.commands.MutationCommand;
import microbat.model.trace.TraceNode;
import tracediff.TraceDiff;
import jmutation.MutationFramework;
import jmutation.model.MutationResult;
import jmutation.model.project.Project;
import tracediff.model.PairList;
import java.util.List;

public class TraceManager {
    MutationFramework mutationFramework = new MutationFramework();
    TraceDiff traceDiff = new TraceDiff();

    public void setMutationFramework(MutationFramework mutationFramework) {
        this.mutationFramework = mutationFramework;
    }

    public void setTraceDiff(TraceDiff traceDiff) {
        this.traceDiff = traceDiff;
    }

    public void run() {
        // MicroBat configuration programmatic setup
        // If MutationFramework#setMicrobatConfig is not called, it would attempt to generate the MicroBat configuration using the JSON file.
        String projectPath = "./java-mutation-framework/sample/math_70";
        MicrobatConfig microbatConfig = MicrobatConfig.defaultConfig();
        microbatConfig = microbatConfig.setJavaHome("C:\\Program Files\\Java\\jdk1.8.0_341");
        microbatConfig = microbatConfig.setStepLimit(200000);
        mutationFramework.setMicrobatConfig(microbatConfig);

        mutationFramework.setProjectPath(projectPath);
        List<TestCase> testCases = mutationFramework.getTestCases();
        mutationFramework.autoSeed(1, 1000);
        mutationFramework.toggleStrongMutations(true);
        int numOfMutations = 1;
        for (int i = 0; i < testCases.size(); i++) {
            TestCase test = testCases.get(i);
            mutationFramework.setTestCase(test);
            mutationFramework.setMaxNumberOfMutations(numOfMutations);
            MutationResult mutationResult;
            mutationResult = mutationFramework.startMutationFramework();

            List<TraceNode> rootCauses = mutationResult.getRootCauses();

            List<MutationCommand> mutationHistory = mutationResult.getMutationHistory();

            Project mutatedProject = mutationResult.getMutatedProject();
            Project originalProject = mutationResult.getOriginalProject();

            PairList pairList = traceDiff.getTraceAlignment("src\\main\\java", "src\\test\\java",
                    mutatedProject.getRoot().getAbsolutePath(), originalProject.getRoot().getAbsolutePath(),
                    mutationResult.getMutatedTrace(), mutationResult.getOriginalTrace());

            TestIOFramework testIOFramework = new TestIOFramework();
            TestIO testIO = testIOFramework.getBuggyTestIOs(mutationResult.getOriginalResult(),
                    mutationResult.getOriginalResultWithAssertions(),
                    mutationResult.getMutatedResult(),
                    mutationResult.getMutatedResultWithAssertions(), originalProject.getRoot(),
                    mutatedProject.getRoot(), pairList, mutationResult.getTestClass(),
                    mutationResult.getTestSimpleName());
            if (testIO == null) {
                System.out.println("Test IO was null");
            } else {
                List<IOModel> inputs = testIO.getInputs();
                IOModel output = testIO.getOutput();
                System.out.println("Inputs:");
                for (IOModel input: inputs) {
                    System.out.println(input);
                }
                System.out.println("Output:\n" + output);
            }
        }
    }
}
