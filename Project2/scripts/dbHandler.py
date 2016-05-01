import sqlite3

class DBHandler():
    def __init__(self, dbFile):
        self.dbFile = dbFile
        self.conn = sqlite3.connect(self.dbFile)

    def createTables(self):
        self.conn.execute('''CREATE TABLE IF NOT EXISTS issue(id_grp VARCHAR(128), id INTEGER, name VARCHAR(128), milestone_identifier INTEGER, group_id VARCHAR(128),
        CONSTRAINT pk_issue PRIMARY KEY (id_grp) ON CONFLICT ABORT)''')

        print "Issues table created"

        self.conn.execute('''CREATE TABLE IF NOT EXISTS milestone(id INTEGER, name VARCHAR(128), description VARCHAR(1024),
        created_at DATETIME, due_at DATETIME, closed_at DATETIME, identifier INTEGER, group_id VARCHAR(128),
        CONSTRAINT pk_milestone PRIMARY KEY(id) ON CONFLICT ABORT)''')

        print "Milestones table created"

        self.conn.execute('''CREATE TABLE IF NOT EXISTS event(id_grp VARCHAR(128) NOT NULL, time DATETIME NOT NULL, action VARCHAR(128),
        label VARCHAR(128), user VARCHAR(128), identifier INTEGER,
        CONSTRAINT pk_event PRIMARY KEY (identifier) ON CONFLICT IGNORE,
        CONSTRAINT fk_event_issue FOREIGN KEY (id_grp) REFERENCES issue(id_grp) ON UPDATE CASCADE ON DELETE CASCADE)''')

        print "Events table created"

        self.conn.execute('''CREATE TABLE IF NOT EXISTS comment(id_grp VARCHAR(128), user VARCHAR(128), created_at DATETIME NOT NULL,
        updated_at DATETIME, text VARCHAR(1024), identifier INTEGER,
        CONSTRAINT pk_comment PRIMARY KEY (identifier) ON CONFLICT IGNORE,
        CONSTRAINT fk_comment_issue FOREIGN KEY (id_grp) REFERENCES issue(id_grp) ON UPDATE CASCADE ON DELETE CASCADE)''')

        print "Comments table created"

        self.conn.execute('''CREATE TABLE IF NOT EXISTS commits(id INTEGER NOT NULL, time DATETIME NOT NULL, sha VARCHAR(128),
        user VARCHAR(128), message VARCHAR(128),
        CONSTRAINT pk_commits PRIMARY KEY (id) ON CONFLICT ABORT)''')

        print "Commits table created"

        self.conn.execute('''CREATE TABLE IF NOT EXISTS user_group(user VARCHAR(128) NOT NULL, group_id VARCHAR(128),
        CONSTRAINT pk_commits PRIMARY KEY (user) ON CONFLICT ABORT)''')

        print "User Group table created"

    def populateIssues(self, issueTuples):
        try:
            if len(issueTuples) > 0:
                self.conn.executemany('INSERT INTO issue VALUES (?,?,?,?,?)', issueTuples)
                self.conn.commit()
                print "Issues table populated"
        except sqlite3.Error as er:
            print(er)

    def populateEvents(self, eventTuples):
        try:
            if len(eventTuples) > 0:
                self.conn.executemany('INSERT INTO event VALUES (?, ?, ?, ?, ?, ?)', eventTuples)
                self.conn.commit()
                print "Events table populated"
        except sqlite3.Error as er:
            print(er)

    def populateMilestones(self, milestoneTuples):
        try:
            if len(milestoneTuples) > 0:
                self.conn.executemany('INSERT INTO milestone VALUES (?, ?, ?, ?, ?, ?, ?, ?)', milestoneTuples)
                self.conn.commit()
                print "Milestones table populated"
        except sqlite3.Error as er:
            print(er)

    def populateCommits(self, commitTuples):
        try:
            if len(commitTuples) > 0:
                self.conn.executemany('INSERT INTO commits  VALUES (?, ?, ?, ?, ?)', commitTuples)
                self.conn.commit()
                print "Commits table populated"
        except sqlite3.Error as er:
            print(er)

    def populateComments(self, commentTuples):
        try:
            if len(commentTuples) > 0:
                self.conn.executemany('INSERT INTO comment VALUES (?, ?, ?, ?, ?, ?)', commentTuples)
                self.conn.commit()
                print "Comments table populated"
        except sqlite3.Error as er:
            print(er)

    def populateUserGroups(self, userGroupDict):
        try:
            for user, group in userGroupDict.items():
                self.conn.execute("INSERT INTO user_group VALUES (?, ?)", (user, group))
                self.conn.commit()
            print "UserGroups table populated"
        except sqlite3.Error as er:
            print(er)
        pass

    def closeConnection(self):
        self.conn.close()
