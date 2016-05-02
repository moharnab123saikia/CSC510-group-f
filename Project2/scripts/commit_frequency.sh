#!/bin/bash

sqlite3 -csv test.db "select G.user, G.group_id, C.sha, C.time from commits C, user_group G where C.user = G.user order by G.group_id" > user_commits.csv
