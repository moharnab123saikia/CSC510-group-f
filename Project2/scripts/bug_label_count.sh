#!/bin/bash

sqlite3 -csv test.db "select A.identifier, A.user, B.group_id from (select * from event where label="bug") A, user_group B where A.user=B.user" > bug_label_count.csv
