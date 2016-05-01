#  gitabel
#  the world's smallest project management tool
#  reports relabelling times in github (time in seconds since epoch)
#  thanks to dr parnin
#  todo:
#    - ensure events sorted by time
#    - add issue id
#    - add person handle

"""
You will need to add your authorization token in the code.
Here is how you do it.
1) In terminal run the following command
curl -i -u <your_username> -d '{"scopes": ["repo", "user"], "note": "OpenSciences"}' https://api.github.com/authorizations
2) Enter ur password on prompt. You will get a JSON response. 
In that response there will be a key called "token" . 
Copy the value for that key and paste it on line marked "token" in the attached source code. 
3) Run the python file. 
     python gitable.py
"""

from __future__ import print_function
import urllib2
import json
import re,datetime
import sys
import sqlite3
import ConfigParser
import os.path
import argparse
import random
from  dbHandler import DBHandler

User_map = dict()
User_group_map = dict()
milestone_count = 0
commit_count = 0
groupCount = 0

class L():
  "Anonymous container"
  def __init__(i,**fields) : 
    i.override(fields)
  def override(i,d): i.__dict__.update(d); return i
  def __repr__(i):
    d = i.__dict__
    name = i.__class__.__name__
    return name+'{'+' '.join([':%s %s' % (k,pretty(d[k])) 
                     for k in i.show()])+ '}'
  def show(i):
    lst = [str(k)+" : "+str(v) for k,v in i.__dict__.iteritems() if v != None]
    return ',\t'.join(map(str,lst))

  
def secs(d0):
  d     = datetime.datetime(*map(int, re.split('[^\d]', d0)[:-1]))
  epoch = datetime.datetime.utcfromtimestamp(0)
  delta = d - epoch
  return delta.total_seconds()

def dumpMilestone1(u, milestones, token):
  request = urllib2.Request(u, headers={"Authorization" : "token "+token})
  v = urllib2.urlopen(request).read()
  w = json.loads(v)
  if not w or ('message' in w and w['message'] == "Not Found"): 
    print ("returning false")
    return False
  milestone = w
  identifier = milestone['id']
  milestone_id = milestone['number']
  milestone_title = milestone['title']
  milestone_description = milestone['description']
  created_at = secs(milestone['created_at'])
  due_at_string = milestone['due_on']
  due_at = secs(due_at_string) if due_at_string != None else due_at_string
  closed_at_string = milestone['closed_at']
  closed_at = secs(closed_at_string) if closed_at_string != None else closed_at_string
  user = milestone['creator']['login']
    
  milestoneObj = L(ident=identifier,
               m_id = milestone_id,
               m_title = milestone_title,
               m_description = milestone_description,
               created_at=created_at,
               due_at = due_at,
               closed_at = closed_at,
               user = user)
  milestones.append(milestoneObj)
  return True


def dumpCommit1(u,commits,token):
  request = urllib2.Request(u, headers={"Authorization" : "token "+token})
  v = urllib2.urlopen(request).read()
  w = json.loads(v)
  if not w: return False
  for commit in w:
    sha = commit['sha']
    user = commit['author']['login']
    time = secs(commit['commit']['author']['date'])
    message = commit['commit']['message']
    commitObj = L(sha = sha,
                user = user,
                time = time,
                message = message)
    commits.append(commitObj)
  return True

def dumpComments1(u, comments, token):
  request = urllib2.Request(u, headers={"Authorization" : "token "+token})
  v = urllib2.urlopen(request).read()
  w = json.loads(v)
  if not w: return False
  for comment in w:
    user = comment['user']['login']
    identifier = comment['id']
    issueid = int((comment['issue_url'].split('/'))[-1])
    comment_text = comment['body']
    created_at = secs(comment['created_at'])
    updated_at = secs(comment['updated_at'])
    commentObj = L(ident = identifier,
                issue = issueid, 
                user = user,
                text = comment_text,
                created_at = created_at,
                updated_at = updated_at)
    comments.append(commentObj)
  return True

def dump1(u,issues, token):
  request = urllib2.Request(u, headers={"Authorization" : "token "+token})
  v = urllib2.urlopen(request).read()
  w = json.loads(v)
  if not w: return False
  for event in w:
    identifier = event['id']
    issue_id = event['issue']['number']
    issue_name = event['issue']['title']
    created_at = secs(event['created_at'])
    action = event['event']
    label_name = event['label']['name'] if 'label' in event else event['assignee']['login'] if action == 'assigned' else event['milestone']['title'] if action in ['milestoned', 'demilestoned'] else action
    user = event['actor']['login']
    milestone = event['issue']['milestone']
    milestone_identifier = -1
    if milestone != None : 
      milestone_identifier = milestone['id']
      milestone = milestone['number']
  
    eventObj = L(ident=identifier,
                 when=created_at,
                 action = action,
                 what = label_name,
                 user = user,
                 milestone = milestone)
    issue_obj = issues.get(issue_id)
    if not issue_obj: issue_obj = [issue_name, milestone_identifier, []]
    all_events = issue_obj[2]
    all_events.append(eventObj)
    issues[issue_id] = issue_obj
  return True

def dumpMilestone(u,milestones,token):
  try:
    return dumpMilestone1(u, milestones,token)
  except urllib2.HTTPError as e:
    if e.code != 404:
      print(e)
      print("404 Contact TA")
    return False
  except Exception as e:
    print(u)
    rint(e)
    print("other Contact TA")
    return False

def dumpCommit(u,commits, token):
  try:
    return dumpCommit1(u,commits,token)
  except Exception as e: 
    print(u)
    print(e)
    print("Contact TA")
    return False

def dumpComments(u,comments, token):
  try:
    return dumpComments1(u,comments,token)
  except Exception as e: 
    print(u)
    print(e)
    print("Contact TA")
    return False

def dump(u,issues,token):
  try:
    return dump1(u, issues, token)
  except Exception as e: 
    print(u)
    print(e)
    print("Contact TA")
    return False


def extractRepoData(token, repo, dbHandler):

  global groupCount

  groupCount += 1

  groupId = "group_" + str(groupCount)

  page = 1
  milestones = []
  print('getting records from '+repo)
  while(True):
    url = 'https://api.github.com/repos/'+repo+'/milestones/' + str(page)
    doNext = dumpMilestone(url, milestones, token)
    print("milestone "+ str(page))
    page += 1
    if not doNext : break

  page = 1
  issues = dict()
  while(True):
    url = 'https://api.github.com/repos/'+repo+'/issues/events?page=' + str(page)
    doNext = dump(url, issues, token)
    print("issue page "+ str(page))
    page += 1
    if not doNext : break

  page = 1
  comments = []
  while(True):
    url = 'https://api.github.com/repos/'+repo+'/issues/comments?page='+str(page)
    doNext = dumpComments(url, comments, token)
    print("comments page "+ str(page))
    page += 1
    if not doNext : break

  page = 1
  commits = []
  while(True):
    url = 'https://api.github.com/repos/'+repo+'/commits?page=' + str(page)
    doNext = dumpCommit(url, commits, token)
    print("commit page "+ str(page))
    page += 1
    if not doNext : break


  issueTuples = []
  eventTuples = []
  milestoneTuples = []
  commentTuples = []
  commitTuples = []

  for milestone in milestones:
    global milestone_count
    milestone_count += 1
    milestoneTuples.append([milestone_count, milestone.m_title, milestone.m_description, milestone.created_at, milestone.due_at, 
        milestone.closed_at, milestone.ident, groupId])


  for issue, issueObj in issues.iteritems():
    events = issueObj[2]
    num_id = str(issue)
    id_grp = str(issue) + "_" + groupId
    name = issueObj[0]
    milestone_id = issueObj[1]

    issueTuples.append([id_grp, num_id, name, milestone_id, groupId])
   
    for event in events:
      if event.user in User_map:
          User_group_map[User_map[event.user]] = groupId
          eventTuples.append([id_grp, event.when, event.action, 
            User_map[event.what] if event.action == 'assigned' else event.what, User_map[event.user],  event.ident])

  for comment in comments:
    issue_grp = str(comment.issue) + "_" + groupId
    commentTuples.append([issue_grp, User_map[comment.user], comment.created_at, comment.updated_at, comment.text, comment.ident])

  for commit in commits:
    global commit_count
    commit_count += 1
    User_group_map[User_map[commit.user]] = groupId
    commitTuples.append([commit_count, commit.time, commit.sha, User_map[commit.user], commit.message])

  dbHandler.populateIssues(issueTuples)
  dbHandler.populateEvents(eventTuples)
  dbHandler.populateMilestones(milestoneTuples)
  dbHandler.populateCommits(commitTuples)
  dbHandler.populateComments(commentTuples)

  print('done!')
    
def createUserMap():
  with open("names.txt",'r') as f:
    doc=f.readlines()
    num_of_users=len(doc)
    my_randoms=[]
    my_randoms = random.sample(xrange(1, num_of_users+1), num_of_users)
    # print(my_randoms)
    for m in doc:
      User_map[m.strip()]='user_'+str(my_randoms.pop())

def main():
  parser = argparse.ArgumentParser(description='Process GitHub issue records and record to SQLite database')
  parser.add_argument('-t','--token', help = 'Token for authorization')
  parser.add_argument('-db','--database',default='', help='specify db filename')

  args = parser.parse_args()

  dbFile = args.database
  token = args.token

  dbHandler = DBHandler(dbFile)
  dbHandler.createTables()

  createUserMap()
  with open("repos.txt",'r') as f:
    repos = f.readlines()
    for repo in repos:
      extractRepoData(token, repo.strip(), dbHandler)

  dbHandler.populateUserGroups(User_group_map)
  dbHandler.closeConnection()

if __name__ == "__main__":
    main()