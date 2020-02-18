/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.advantech.quartzJob;

import com.advantech.model.BabSettingHistory;
import com.advantech.model.Floor;
import com.advantech.model.Line;
import com.advantech.model.LineUserReference;
import com.advantech.model.PrepareSchedule;
import com.advantech.model.User;
import com.advantech.service.BabSettingHistoryService;
import com.advantech.service.FloorService;
import com.advantech.service.LineService;
import com.advantech.service.LineUserReferenceService;
import com.advantech.service.PrepareScheduleService;
import static com.google.common.collect.Lists.newArrayList;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import static java.util.Comparator.comparing;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import static java.util.stream.Collectors.toList;
import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.Minutes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author Wei.Cheng
 *
 */
@Component
@Transactional
public class ArrangePrepareSchedule1Impl {

    private static final Logger logger = LoggerFactory.getLogger(ArrangePrepareSchedule1Impl.class);

    @Autowired
    private FloorService floorService;

    @Autowired
    private PrepareScheduleService psService;

    @Autowired
    private LineUserReferenceService lineUserRefService;

    @Autowired
    private LineService lineService;

    @Autowired
    private BabSettingHistoryService settingHistoryService;

    private List<Interval> restTimes;
    private DateTime scheduleStartTime, scheduleEndTime;

    public void execute() throws Exception {
        this.execute(new DateTime());
    }

    public void execute(DateTime d) throws Exception {
        d = d.withTime(0, 0, 0, 0);

        List<Floor> floors = floorService.findAll();
        floors = floors.stream()
                .filter(f -> f.getId() == 1 || f.getId() == 2)
                .collect(toList());

        for (Floor f : floors) {
            List<PrepareSchedule> ps = this.findPrepareSchedule(f, d);
            ps.forEach((p) -> {
                psService.update(p);
            });
        }

        logger.info("Update prepareSchedule finish");
    }

    private void updateDateParamater(DateTime d) {
        scheduleStartTime = new DateTime(d).withTime(8, 30, 0, 0);
//        scheduleEndTime = new DateTime(d).withTime(21, 0, 0, 0);
        scheduleEndTime = new DateTime(d).withTime(23, 59, 59, 0);
        restTimes = newArrayList(
                new Interval(new DateTime(d).withTime(12, 0, 0, 0), new DateTime(d).withTime(12, 50, 0, 0)),
                new Interval(new DateTime(d).withTime(15, 30, 0, 0), new DateTime(d).withTime(15, 40, 0, 0)),
                new Interval(new DateTime(d).withTime(17, 30, 0, 0), new DateTime(d).withTime(18, 0, 0, 0))
        );
    }

    public List<PrepareSchedule> findPrepareSchedule(Floor f, DateTime d) {

        updateDateParamater(d);
        d = d.withTime(0, 0, 0, 0);

        List<PrepareSchedule> l = psService.findByFloorAndDate(f, d);

        List<Line> lines = lineService.findBySitefloorAndLineType(f.getName(), 1, 2);

        List<LineUserReference> users = lineUserRefService.findByLinesAndDate(lines, d);

        List<PrepareSchedule> result = new ArrayList();

        lines.forEach((line) -> {
            List<LineUserReference> lineUsers = users.stream()
                    .filter(lr -> Objects.equals(line, lr.getLine()))
                    .collect(toList());

            List<PrepareSchedule> lineSchedule = l.stream()
                    .filter(p -> Objects.equals(line, p.getLine()))
                    .collect(toList());

            if (line != null && !lineUsers.isEmpty() && !l.isEmpty()) {
                lineSchedule = addUser(lineUsers, lineSchedule);
                lineSchedule = scheduleTime(lineUsers, lineSchedule);
                lineSchedule = addProducedFlag(lineSchedule);
                result.addAll(lineSchedule);
            }
        });

        return result;
    }

    private List<PrepareSchedule> addUser(List<LineUserReference> users, List<PrepareSchedule> l) {

        int maxPeopleCnt = users.size();
        List<User> settingUsers = users.stream().map(u -> u.getUser()).collect(toList());

        int peopleFlag = 0;
        int settingUsersCnt = settingUsers.size();

        for (PrepareSchedule w : l) {
            List<User> u = new ArrayList();
            if (settingUsersCnt <= maxPeopleCnt) {
                u.addAll(settingUsers);
            } else {
                for (int i = peopleFlag, j = peopleFlag + maxPeopleCnt; i < j; i++) {
                    u.add(settingUsers.get(i % settingUsersCnt));
                }
                peopleFlag += maxPeopleCnt;
            }
            w.setUsers(u);
        }

//        l = fillUserGap(line, l);
        return l;
    }

    private List<PrepareSchedule> fillUserGap(Line line, List<PrepareSchedule> l) {

        List<Line> cellLine = lineService.findBySitefloorAndLineType(line.getFloor().getName(), 2);
        List<LineUserReference> cellUsers = lineUserRefService.findByLines(cellLine);
        List<User> settingUsers = cellUsers.stream().map(u -> u.getUser()).collect(toList());

        int peopleFlag = 0;

        for (PrepareSchedule w : l) {
            int gapCnt = line.getPeople() - w.getUsers().size();
            List<User> scheduleUsers = w.getUsers();
            if (cellUsers.size() <= gapCnt) {
                scheduleUsers.addAll(settingUsers);
            } else {
                for (int i = 0; i < gapCnt; i++) {
                    scheduleUsers.add(settingUsers.get(peopleFlag % l.size()));
                }
                peopleFlag += gapCnt;
            }
        }

        return l;

    }

    private List<PrepareSchedule> addProducedFlag(List<PrepareSchedule> l) {
        if (l.isEmpty()) {
            return l;
        }
        List<String> modelNames = l.stream()
                .map(p -> p.getModelName())
                .distinct()
                .collect(toList());

        List<BabSettingHistory> settings = settingHistoryService.findByBabModelNames(modelNames);

        l.forEach(p -> {
            Map infoMap = new HashMap();
            List<User> scheduleUsers = p.getUsers();
            int[] ordinal = {0};
            scheduleUsers.forEach((u) -> {
//                System.out.println(ordinal[0]);
//                BabSettingHistory h = settings.stream().filter(s
//                        -> s.getBab().getModelName().equals(p.getModelName())
//                        && s.getStation() == ordinal[0]
//                        && s.getJobnumber().equals(u.getJobnumber()))
//                        .findFirst()
//                        .orElse(null);

                BabSettingHistory h = settings.stream().filter(s
                        -> s.getBab().getModelName().equals(p.getModelName())
                        && s.getJobnumber().equals(u.getJobnumber()))
                        .findFirst()
                        .orElse(null);

                infoMap.put(ordinal[0], h != null);
                ordinal[0]++;
            });
            p.setOtherInfo(infoMap);
        });

        return l;
    }

    private List<PrepareSchedule> scheduleTime(List<LineUserReference> users, List<PrepareSchedule> l) {

        l = l.stream().sorted(comparing(PrepareSchedule::getPriority)).collect(toList());

        int maxPeopleCnt = users.get(0).getLine().getPeople();

        PrepareSchedule prev = null;
        int cnt = 0;

        for (PrepareSchedule w : l) {
            DateTime start = this.scheduleStartTime;
            if (cnt != 0 && prev != null) {
                start = new DateTime(prev.getEndDate());
            }
            BigDecimal addTime = w.getTimeCost().setScale(0, RoundingMode.UP);
            DateTime end = start.plusMinutes(addTime.divide(new BigDecimal(users.size() > maxPeopleCnt ? maxPeopleCnt : users.size()), 0, BigDecimal.ROUND_UP).intValue());

            Interval timeAdjust = byPassRestTime(new Interval(start, end));

            w.setStartDate(timeAdjust.getStart().toDate());
            w.setEndDate(timeAdjust.getEnd().toDate());

            prev = w;
            cnt++;
        }
        return l;
    }

    private Interval byPassRestTime(Interval i) {
        for (Interval restTime : restTimes) {
            int iMin = Minutes.minutesBetween(i.getStart(), i.getEnd()).getMinutes();
            int restMin = Minutes.minutesBetween(restTime.getStart(), restTime.getEnd()).getMinutes();
            if (hasOverlap(i, restTime)) {
                if (isInRestTime(restTime, i.getStart()) && isInRestTime(restTime, i.getEnd())) {
                    /*
                        i   |----|
                        r |--------|   
                    */
                    return new Interval(restTime.getEnd(), restTime.getEnd().plusMinutes(iMin));
                } else if (isInRestTime(restTime, i.getStart()) && !isInRestTime(restTime, i.getEnd())) {
                    /*
                        i    |--------|
                        r |----|   
                    */
                    int overlap = Minutes.minutesBetween(i.getStart(), restTime.getEnd()).getMinutes();
                    return new Interval(restTime.getEnd(), i.getEnd().plusMinutes(overlap));
                } else if (!isInRestTime(restTime, i.getStart()) && isInRestTime(restTime, i.getEnd())) {
                    /*
                        i |----|
                        r   |--------|   
                    */
                    int overlap = Minutes.minutesBetween(restTime.getStart(), i.getEnd()).getMinutes();
                    return new Interval(i.getStart(), restTime.getEnd().plusMinutes(overlap));
                } else {
                    /*
                        i |--------|
                        r   |----|   
                    */
                    return new Interval(i.getStart(), i.getEnd().plusMinutes(restMin));
                }
            }
        }
        return i;
    }

    private boolean hasOverlap(Interval t1, Interval t2) {
        return !t1.getEnd().isBefore(t2.getStart()) && !t1.getStart().isAfter(t2.getEnd());
    }

    private boolean isInRestTime(Interval rest, DateTime d) {
        return rest.getStart().compareTo(d) * d.compareTo(rest.getEnd()) >= 0;
    }

}
