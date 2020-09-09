
### ***Work Item Management***
----
The project task is to design and implement a Work Item Management (WIM) Console Application.


### ***Functional Requirements***

Application should support multiple teams. Each team has ***name***, ***members*** and ***boards***.

- ***Member*** has name, list of work items and activity history.

   - Name should be unique in the application

    - Name is a string between 5 and 15 symbols.

- ***Board*** has name, list of work items and activity history.

  - Name should be unique in the team

  - Name is a string between 5 and 10 symbols.

There are 3 types of work items: ***bug***, ***story*** and ***feedback***.

### **Bug**

Bug has ID, title, description, steps to reproduce, priority, severity, status, assignee, comments and history.

- Title is a string between 10 and 50 symbols.
- Description is a string between 10 and 500 symbols.
- Steps to reproduce is a list of strings.
- Priority is one of the following: High, Medium, Low
- Severity is one of the following: Critical, Major, Minor 
-  Status is one of the following: Active, Fixed 
-  Assignee is a member from the team.
- Comments is a list of comments (string messages with author).
- History is a list of all changes (string messages) that were done to the bug.

### **Story**

Story has ID, title, description, priority, size, status, assignee, comments and history.
- Title is a string between 10 and 50 symbols.
- Description is a string between 10 and 500 symbols.
- Priority is one of the following: High, Medium, Low
- Size is one of the following: Large, Medium, Small 
-  Status is one of the following: NotDone, InProgress, Done 
-  Assignee is a member from the team.
- Comments is a list of comments (string messages with author).
- History is a list of all changes (string messages) that were done to the story.

### **Feedback**

Feedback has ID, title, description, rating, status, comments and history.

- Title is a string between 10 and 50 symbols.
- Description is a string between 10 and 500 symbols.
- Rating is an integer.
- Status is one of the following: New, Unscheduled, Scheduled, Done 
- Comments is a list of comments (string messages with author).
- History is a list of all changes (string messages) that were done to the feedback.

### **Operations**

Application should support the following operations:

- Create a new person 
- Show all people 
- Show person's activity 
- Create a new team 
- Show all teams
- Show team's activity
- Add person to team
- Show all team members
- Create a new board in a team
- Show all team boards
- Show board's activity
- Create a new Bug/Story/Feedback in a board
- Change Priority/Severity/Status of a bug
- Change Priority/Size/Status of a story
- Change Rating/Status of a feedback
- Assign/Unassign work item to a person
- Add comment to a work item
- List work items with options:
  - List all
  - Filter bugs/stories/feedback only
  - Filter by status and/or asignee
  - Sort by title/priority/severity/size/rating

## ***Commands***
**Create**
- CreateMember `[Name]` - creates a new Member with name _[Name]_ .
- CreateTeam `[TeamName]` - creates a new Team with name _[TeamName]_ .
- CreateBoard `[BoardName]` `[TeamName]`- creates a new Board with name _[BoardName]_ in team _[TeamName]_ .
- CreateStory `[storyTitle]` `[description]` `[storySize]` `[teamName]` `[boardName]` `[priority]`  - creates a new Story by specific person in team _[teamName]_  inside board _[boardName]_. Description input should be surrounded by brackets _[Lorem Ipsum...]_
- CreateBug `[bugTitle]` `[Description]` `[priority]` `[severity]` `[teamName]` `[boardName]` `[stepsToReproduce]` - creates a new Bug in given team's board with _[Description]_ and _[StepsToReproduce]_ . Description input should be surrounded by brackets _[Lorem Ipsum...]_ Steps to reproduce must be surrounded by curly brackets `{}` and `|` _{stepOne | stepTwo| stepThree}_
- CreateFeedback `[feedbackTitle]` `[description]` `[rating]` `[teamName]` `[boardName]` - creates a new Feedback in board _[boardName]_ in team _[teamName]_

**Show**
- ShowAllMembers - shows all existing members. No matter if they are part of a team or not.
- ShowAllTeams - shows all existing teams.
- ShowAllTeamMembers `[teamName]` - shows all members in the specified team.
- ShowTeamAllBoards `[teamName]`- shows all boards in the specified team.
- ShowMemberActivity `[memberName]` - shows a given member's activity.
- ShowBoardActivity `[boardName]` - shows a given board's activity.
- ShowTeamActivity `[teamName]` - shows  a given team's activity .

**Add**
- AddMemberToTeam `[memberName]` `[teamName]` - adds an existing member to an existing team.
- AddCommentToWorkItem `[memberName]` `[boardName]` `[TeamName]` `[itemName]` `[message]` - lets a person who is part of a team's board to add comment to a work item.

**Change**
- ChangeBugSeverity `[memberName]` `[boardName]` `[teamName]` `[bugName]` `[severity]` - lets a person who is part of a team's board change the severity of a bug.
- ChangeBugStatus `[memberName]` `[boardName]` `[teamName]` `[bugName]` `[status]` -lets a person who is part of a team's board change the status of a bug.
- ChangePriority `[memberName]` `[boardName]` `[teamName]` `[workItem]` `[priority]` - lets a person who is part of a team's board change the priority of a work item.
- ChangeRatingFeedback  `[memberName]` `[boardName]` `[teamName]` `[feedbackName]` `[rating]` - lets a person who is part of a team's board change the rating of a feedback.
- ChangeSizeStory `[memberName]` `[boardName]` `[teamName]` `[storyName]` `[size]` - lets a person who is part of a team's board change the size of a story.
- ChangeStatusFeedback `[memberName]` `[boardName]` `[teamName]` `[feedbackName]` `[status]` - lets a person who is part of a team's board change the status of a feedback.
- ChangeStatusStory `[memberName]` `[boardName]` `[teamName]` `[storyName]` `[status]` - lets a person who is part of a team's board change the status of a story.

**Assign**
- AssignPersonToWorkItem `[personName]` `[boardName]` `[teamName]` `[itemName]` - member is assigned to work on that specific item. There could be more than one members assigned on one item.
- UnassignPersonFromWorkItem  `[personName]` `[boardName]` `[teamName]` `[itemName]` - provided person is unassigned from the specified work item. 

**Filter**
- FilterItems `[workItem type]` - shows all existing types of items. You can choose between _[bug]_ _[story]_ _[feedback]_
- FilterByStatus `[status]` - shows all work items with the specified status type.
- FilterByAssignee `[assigneeName]` - shows all work items that are assigned to the specified member name.

**Sort**
- Sort `[filter parameter]` - shows all work items sorted by the specified parameter. You can choose between _[Title]_ _[Priority]_ _[Severity]_ _[Size]_ _[Rating]_

**DeleteCommands**
- DeleteBoard `[boardName]` `[teamName]` - delete the specified board from the team and move it to the archive.
- DeleteMember `[memberName]` - delete members that are not present in any team and move them to the archive.
- DeleteWorkItem `[itemName]` `[boardName]` `[teamName]` - delete the specified work item.
- RemoveMemberFromTeam `[memberName]` `[teamName]` - remove the specified member from the team.


Part of:
--- 
![alt text][logo]

[logo]: http://eskills.tto-bait.bg/wp-content/uploads/2017/03/Primary@2x.png "TelerikAcademy"