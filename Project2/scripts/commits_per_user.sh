#!/bin/bash

sqlite3 -csv test.db "select a.group_id, b.commit_count, b.user from user_group a,  (select user, count(id) as commit_count from commits group by user) b where a.user = b.user"  > commit_per_user.csv
