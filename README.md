# SuggestionsBox
Interactive Suggestions plugin that uses a GUI for players to view and vote on other player's suggestions
Changelog:
- added SQL DB for storing suggestions
- added listeners for approving submissions
- added Page navigation to GUIs
- added suggestions saving to paper as lore
- added GUI register

Current issues:
approvalListener was working but broke without reason, currently debugging

Planned changes:
VoteListener to be with new SQL values that get added to the titles of Approved suggestions
Ability to sort approved suggestions by submission date or upvotes (default submission date)
