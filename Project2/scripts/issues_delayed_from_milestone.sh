#!/bin/bash


sqlite3 -csv test.db "select ev.id_grp, G.group_id, ev.time-M.due_at as secondsAfter from (select id_grp, user, time from event ev1 where action = 'closed' and time >= (select max(time) from event where id_grp = ev1.id_grp and action = 'closed')) ev, milestone M, issue I , user_group G where I.id_grp = ev.id_grp and I.milestone_identifier = M.identifier and G.user = ev.user order by G.group_id" > delayed_milestone_issue.csv
