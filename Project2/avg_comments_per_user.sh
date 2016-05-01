#!/bin/bash

sqlite3 -csv test.db "select A.id, A.group_id, B.user, B.count from issue A ,(select user, id_grp, count(user) as count from comment group by id_grp) B where A.id_grp = B.id_grp order by A.group_id" > avg_comments_per_user.csv
