package tracemanager;

import jmutation.model.TestCase;
import testio.TestIOFramework;
import testio.model.TestIO;
import jmutation.mutation.commands.MutationCommand;
import microbat.model.trace.TraceNode;
import tracediff.TraceDiff;
import jmutation.MutationFramework;
import jmutation.model.MutationResult;
import jmutation.model.Project;
import tracediff.model.PairList;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        mutationFramework.setProjectPath("../java-mutation-framework/sample/math_70");
        mutationFramework.setDropInsDir("../java-mutation-framework/lib");
        mutationFramework.setMicrobatConfigPath("../java-mutation-framework/sampleMicrobatConfig.json");
        List<TestCase> testCases = mutationFramework.getTestCases();
        for (int i = 0; i < testCases.size(); i++) {
            int seed = 1;
            int numOfMutations = 1;
            TestCase test = testCases.get(i);
            mutationFramework.setSeed(seed);
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
        }
    }
}
