#!/bin/bash


sqlite3 -csv test.db "select A.group_id, A.id, (cl.time - op.time) as openTime from issue A, event cl, (select id_grp, min(time) as time from event group by id_grp) op where cl.action == 'closed' AND cl.id_grp == op.id_grp and A.id_grp = cl.id_grp order by A.group_id" > issue_close_time_spent.csv
