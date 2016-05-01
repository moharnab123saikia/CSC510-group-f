#!/bin/bash

sqlite3 -csv test.db "select count(id) as Num_Issues, milestone_identifier, group_id from issue group by milestone_identifier order by group_id" > num_milestone.csv
