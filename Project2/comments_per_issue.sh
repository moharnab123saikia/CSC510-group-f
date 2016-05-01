#!/bin/bash

sqlite3 -csv test.db "select A.id, A.group_id, B.count from issue A left outer join (select id_grp, count(*) as count from comment group by id_grp) B on A.id_grp = B.id_grp order by A.group_id" > comments_per_issue.csv
