package porz;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;
import porz.JavaTest;

public class TestRunner {
    public static void main(String[] args) {
        Result result = JUnitCore.runClasses(JavaTest.class);
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
        }
        System.out.println(result.wasSuccessful());
    }
}
