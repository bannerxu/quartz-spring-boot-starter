package io.github.lib.quartz.core;

import io.github.lib.quartz.model.IScheduleJob;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class DefaultScheduleJobProvider implements JobProvider {

    @Override
    public List<IScheduleJob> findAll() {
        return new ArrayList<>();
    }

    @Override
    public List<IScheduleJob> findAllById(List<String> jobIds) {
        return new ArrayList<>();
    }
}