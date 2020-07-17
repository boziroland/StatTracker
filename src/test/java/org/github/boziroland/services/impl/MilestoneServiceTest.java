package org.github.boziroland.services.impl;

import org.github.boziroland.DAL.impl.MilestoneInMemory;
import org.github.boziroland.entities.Milestone;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

class MilestoneServiceTest {

    @Test
    void testMilestoneNotDone(){
        MilestoneService service = new MilestoneService(new MilestoneInMemory());
        Milestone milestone = Mockito.mock(Milestone.class);

        when(milestone.getRequirement()).thenReturn(100);
        when(milestone.isDoneAlready()).thenReturn(false);

        assertFalse(service.checkAchievement(50, milestone));
    }

    @Test
    void testMilestoneDone(){
        MilestoneService service = new MilestoneService(new MilestoneInMemory());
        Milestone milestone = Mockito.mock(Milestone.class);

        when(milestone.getRequirement()).thenReturn(100);
        when(milestone.isDoneAlready()).thenReturn(false);

        assertTrue(service.checkAchievement(100, milestone));
    }

    @Test
    void testMilestoneDoneButWasAlreadyDone(){
        MilestoneService service = new MilestoneService(new MilestoneInMemory());
        Milestone milestone = Mockito.mock(Milestone.class);

        when(milestone.getRequirement()).thenReturn(100);
        when(milestone.isDoneAlready()).thenReturn(true);

        assertFalse(service.checkAchievement(100, milestone));
    }

    @Test
    void testMilestoneNotDoneAlthoughItWasAlreadyDone(){
        MilestoneService service = new MilestoneService(new MilestoneInMemory());
        Milestone milestone = Mockito.mock(Milestone.class);

        when(milestone.getRequirement()).thenReturn(100);
        when(milestone.isDoneAlready()).thenReturn(true);

        assertFalse(service.checkAchievement(10, milestone));
    }

}