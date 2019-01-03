package ru.skogmark.valhall.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Nonnull;
import javax.annotation.PostConstruct;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.requireNonNull;

@Service
public class SubjectWorkerScheduler {
    private static final Logger log = LoggerFactory.getLogger(SubjectWorkerScheduler.class);

    private final ScheduledExecutorService subjectWorkerExecutor;

    public SubjectWorkerScheduler(@Nonnull ScheduledExecutorService subjectWorkerExecutor) {
        this.subjectWorkerExecutor = requireNonNull(subjectWorkerExecutor, "subjectWorkerExecutor");
    }

    @PostConstruct
    public void start() {
        log.info("Starting scheduler");
        subjectWorkerExecutor.scheduleWithFixedDelay(this::checkTimetableAndExecuteWorker, 0, 1, TimeUnit.SECONDS);
    }

    private void checkTimetableAndExecuteWorker() {
        
    }
}
