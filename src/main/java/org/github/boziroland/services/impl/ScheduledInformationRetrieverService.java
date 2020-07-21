package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.ILeagueDAO;
import org.github.boziroland.entities.User;
import org.github.boziroland.services.IAPIService;
import org.github.boziroland.services.ILeagueService;
import org.github.boziroland.services.impl.LeagueService;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ScheduledInformationRetrieverService {
    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);

    private LocalTime remindTime;

    public void retrieve(User user, IAPIService service){
        Runnable sender = new Runnable(){
            public void run(){
                service.requestInformation(user.getLeagueID(), user.getLeagueData());
            }
        };
        long delay = ChronoUnit.SECONDS.between(LocalTime.now(), remindTime);
        scheduler.schedule(sender, delay, TimeUnit.SECONDS);
    }

    public void setRemindTime(LocalTime remindTime) {
        this.remindTime = remindTime;
    }

    public LocalTime getRemindTime() {
        return remindTime;
    }
}
