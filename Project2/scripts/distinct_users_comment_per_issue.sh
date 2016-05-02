#!/bin/bash

sqlite3 -csv test.db "select A.group_id, A.id, B.count from issue A left outer join (select id_grp, count(distinct user) as count from comment group by id_grp) B on A.id_grp = B.id_grp order by A.group_id" > distinct_users_comment_per_issue.csv
