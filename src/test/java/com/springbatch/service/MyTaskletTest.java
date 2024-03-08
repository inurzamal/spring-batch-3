package com.springbatch.service;

import com.springbatch.repository.EmployeeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
public class MyTaskletTest {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private MyTasklet myTasklet;

    @Test
    void testExecute() throws Exception {
        // Execute the tasklet
        RepeatStatus repeatStatus = myTasklet.execute(mock(StepContribution.class), mock(ChunkContext.class));

        // Verify that the deleteEmployeesByCityNewYork method was called
        verify(employeeRepository, times(1)).deleteEmployeesByCityNewYork();

        assertEquals(RepeatStatus.FINISHED, repeatStatus);
    }
}
