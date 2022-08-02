package tracemanager;

import tracediff.TraceDiff;
import jmutation.MutationFramework;
import jmutation.model.MutationResult;
import jmutation.model.Project;
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
        mutationFramework.setProjectPath("../java-mutation-framework/sample/math_70");
        mutationFramework.setDropInsDir("../java-mutation-framework/lib");
        List<MutationResult> mutationResults = mutationFramework.startMutationFramework();
        for (MutationResult mutationResult : mutationResults) {
            Project mutatedProject = mutationResult.getMutatedProject();
            Project originalProject = mutationResult.getOriginalProject();
            PairList pairList = traceDiff.getTraceAlignment("src/main/java", "src/main/test",
                    mutatedProject.getRoot().getAbsolutePath(), originalProject.getRoot().getAbsolutePath(),
                    mutationResult.getMutatedTrace(), mutationResult.getOriginalTrace());
            System.out.println("Pair list obtained");
        }
    }
}
