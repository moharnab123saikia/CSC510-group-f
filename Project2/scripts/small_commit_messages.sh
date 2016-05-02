#!/bin/bash

sqlite3 -csv test.db "select  A.group_id,B.user, B.sha, B.time, B.message from user_group A, commits B  where A.user = B.user order by A.group_id " > small_commit.csv
