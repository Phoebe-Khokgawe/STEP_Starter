// Copyright 2019 Google LLC
//
// Licensed under the Apache License, Version 2.0 (the "License");
// you may not use this file except in compliance with the License.
// You may obtain a copy of the License at
//
//     https://www.apache.org/licenses/LICENSE-2.0
//
// Unless required by applicable law or agreed to in writing, software
// distributed under the License is distributed on an "AS IS" BASIS,
// WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
// See the License for the specific language governing permissions and
// limitations under the License.

package com.google.sps;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public final class FindMeetingQuery {
    private ArrayList<TimeRange> freeTimeRange = new ArrayList<>();
    ArrayList<Event> allEvents = new ArrayList<>();
    private int index = 0;
    private int validStartTime = TimeRange.START_OF_DAY;
    private int freeTimeDuration = 0;

    public Collection<TimeRange> query(Collection<Event> events, MeetingRequest request) {

        //don't consider the event that the attendees are not joining
        for(Event event : events){
            if(attendeesIsInThisMeeting(event,request)){
                this.allEvents.add(event);
            }
        }

        if (request.getDuration() > TimeRange.WHOLE_DAY.duration()) return new ArrayList<TimeRange>();
        else if (request.getAttendees().isEmpty() || allEvents.isEmpty()) return Arrays.asList(TimeRange.WHOLE_DAY);
        else {
            //checking through all the events and find the free time during the day
            for (Event event : allEvents) {
                if(!attendeesIsInThisMeeting(event,request)){continue;}
                validStartTime = checkReturnValidStartTime(event);
                System.out.println("Valid start time before: " + validStartTime);
                if (validStartTime > event.getWhen().start()) {
                    checkLastEvent(allEvents.size());
                    continue;
                }
                freeTimeRange.add(TimeRange.fromStartEnd(validStartTime, event.getWhen().start(), false));
                validStartTime = event.getWhen().end();
                System.out.println("Valid start time after: " + validStartTime);
                checkLastEvent(allEvents.size());
                System.out.println(freeTimeRange.toString());
                index++;
            }

            //check that the free time that's given is enough for the person to have a meeting
            for (TimeRange range : freeTimeRange) {
                freeTimeDuration += range.duration();
                if (freeTimeDuration >= request.getDuration()) return freeTimeRange;
            }
            //not enough free time
            freeTimeRange.clear();

        }
        return freeTimeRange;
    }

    /**
     * The valid start time is where there's no conflict within the given event.
     * @param event -- current event
     * @return if there's no conflict -- original validStartTime
     *         if there's conflict -- ending time of the given event
     */
    private int checkReturnValidStartTime(Event event) {
        return event.getWhen().contains(validStartTime) ? event.getWhen().end() : validStartTime;
    }

    /**
     * The last event need to be added specifically if the END_OF_DAY time.
     * @param size -- total valid events
     */
    private void checkLastEvent(int size) {
        if (index == size - 1) { //last event
            freeTimeRange.add(TimeRange.fromStartEnd(validStartTime, TimeRange.END_OF_DAY, true));
        }
    }

    /**
     * Check if the attendees in the current event is in the meeting.
     * @param event -- current event
     * @param request -- current meeting request
     * @return true -- attendee is in the meeting
     *         false -- attendee is not in the meeting
     */
    private boolean attendeesIsInThisMeeting(Event event, MeetingRequest request) {
        for (String attendee : event.getAttendees()) {
            if (!request.getAttendees().contains(attendee)) {
                return false;
            }
        }
        return true;
    }

}

