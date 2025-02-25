[![EO principles respected here](https://www.elegantobjects.org/badge.svg)](https://www.elegantobjects.org)

[![Build](https://github.com/ArtemGet/prbot/actions/workflows/maven.yaml/badge.svg)](https://github.com/ArtemGet/prbot/actions/workflows/maven.yaml)

# Message template:

line 1: TagAssigner TagReviewer1 TagReviewer2 TagReviewerN

line 2: Issue number - Issue name

line 3: [[Link](https://link-to-pull-request)] [BranchFromName](https://link-to-branch) -> [BranchToName]([link to branch](https://link-to-branch))

line 4: AssignerApproveCheckbox Reviewer1ApproveCheckbox MergeCheckbox(If merged)

line N-1: same as line 3 (used when pull requests are linked)

line N: same as line 4 (used when pull requests are linked) Reviewer2ApproveCheckbox ReviewerNApproveCheckbox

## Checkboxes
Pending: ☐

Approve: ✅

Comments above: ❌

Merged: 👍

##Message example
line 1: @Assigner @Reviewer

line 2: ISSUE-123 [BE] - touch the grass

line 3: [[Link](https://link-to-pull-request)] [develop](https://link-to-develop-branch) -> [release](https://link-to-release-branch)

line 4: 👍 ✅ ☐

##Reactions
If pull request is merged - bot set a 👍 reaction

If pull request is closed - bot set a ❌ reaction
