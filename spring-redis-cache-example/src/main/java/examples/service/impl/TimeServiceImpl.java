package examples.service.impl;

import examples.service.api.TimeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Calendar;

@Service
public class TimeServiceImpl implements TimeService {
    @Override
    @Cacheable
    public long getCurrentInMilli(){
        return Calendar.getInstance().getTimeInMillis();
    }
}
