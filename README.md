## Functionality:

- Either save UUID to mysql or file
- On player join, auto connect to channels
<<<<<<< HEAD
- Channel list for the server, add a player to list on join/remove on leave to limit query's.
=======
- Channel list for server, add player to list on join/remove on leave to limit querys.
>>>>>>> master
    - ``` Map<Channel, List<Player>> channelList = new HashMap<>(); ```
- Channel object, basic getters and setters
    - Name
    - ID
    - Prefix
    - Color

## Tasks:

<<<<<<< HEAD
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
=======
- [ ] saving/loading mysql
    - [ ] adding players to table
- [ ] Onjoin add to Map
    - [ ] Detect chat message, send to recipients
- [ ] Debug/Polish
>>>>>>> master
