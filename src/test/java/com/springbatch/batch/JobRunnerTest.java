package com.springbatch.batch;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class JobRunnerTest {

    @Mock
    private JobLauncher jobLauncher;

    @Mock(name = "finalAnalysisJob")
    private Job finalAnalysisJob;

    @Mock(name = "purgeJob")
    private Job purgeJob;

    @InjectMocks
    private JobRunner jobRunner;

    @Test
    public void testRun() throws Exception {
        JobExecution finalAnalysisJobExecution = new JobExecution(1L);
        finalAnalysisJobExecution.setStatus(BatchStatus.COMPLETED);
        when(jobLauncher.run(eq(finalAnalysisJob), any(JobParameters.class))).thenReturn(finalAnalysisJobExecution);

        JobExecution purgeJobExecution = new JobExecution(2L);
        purgeJobExecution.setStatus(BatchStatus.COMPLETED);
        when(jobLauncher.run(eq(purgeJob), any(JobParameters.class))).thenReturn(purgeJobExecution);

        jobRunner.run();

        verify(jobLauncher).run(eq(finalAnalysisJob), any(JobParameters.class));
        verify(jobLauncher).run(eq(purgeJob), any(JobParameters.class));
    }

    @Test
    public void testRunWithException() throws Exception {
        JobExecution finalAnalysisJobExecution = new JobExecution(1L);
        finalAnalysisJobExecution.setStatus(BatchStatus.FAILED);
        when(jobLauncher.run(eq(finalAnalysisJob), any(JobParameters.class))).thenReturn(finalAnalysisJobExecution);

        JobExecution purgeJobExecution = new JobExecution(2L);
        purgeJobExecution.setStatus(BatchStatus.COMPLETED);
        when(jobLauncher.run(eq(purgeJob), any(JobParameters.class))).thenReturn(purgeJobExecution);

        jobRunner.run();

        // Verify that finalAnalysisJob is executed
        verify(jobLauncher).run(eq(finalAnalysisJob), any(JobParameters.class));

        // Verify that purgeJob is executed
        verify(jobLauncher).run(eq(purgeJob), any(JobParameters.class));
    }

}
