package ge.tbc.testautomation.util;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryAnalyzer implements IRetryAnalyzer {
    private int cnt;
    private int maxRetries;


    @Override
    public boolean retry(ITestResult iTestResult) {
//        Throwable err = iTestResult.getThrowable();
        RetryCount annotation = iTestResult.getMethod().getConstructorOrMethod().getMethod()
                .getAnnotation(RetryCount.class);

        if (annotation != null){
            maxRetries = annotation.maxRetries();
        }
        if(cnt < maxRetries){
            ++cnt;
            return true;

        }
        return false;
    }
}