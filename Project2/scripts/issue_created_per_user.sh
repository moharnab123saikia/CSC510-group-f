#!/bin/bash

sqlite3 -csv test.db "select a.group_id, b.user, b.issue_count from user_group a, (select user, count(*) as issue_count from event e1 where time <= (select min(time) from event where id_grp = e1.id_grp) group by user) b where a.user = b.user order by a.group_id" > issue_created_per_user.csv
