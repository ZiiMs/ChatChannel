## Functionality:
- Either save UUID to mysql or file
- On player join, auto connect to channels
- Channel list for the server, add a player to list on join/remove on leave to limit query's.
    - ``` Map<Channel, List<Player>> channelList = new HashMap<>(); ```
- Channel object, basic getters and setters
    - Name
    - ID
    - Prefix
    - Color

## Tasks:
- [ ] Create command
    - [ ] Channel creates and saves to database
- [ ] Join channel command
    -[ ] Adds the player to database, with channel ID if channel exists
    -[ ] Receives messages sent in channel
- [ ] Auto join channel
    - [ ] On join, joins channel automatically
- [ ] Prepare for deploy
    - [ ] Debug/Stress test
    - [ ] Polish/Finalize
