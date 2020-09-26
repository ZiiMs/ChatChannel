## Functionality:

- Either save UUID to mysql or file
- On player join, auto connect to channels
- Channel list for server, add player to list on join/remove on leave to limit querys.
    - ``` Map<Channel, List<Player>> channelList = new HashMap<>(); ```
- Channel object, basic getters and setters
    - Name
    - ID
    - Prefix
    - Color

## Tasks:

- [ ] saving/loading mysql
    - [ ] adding players to table
- [ ] Onjoin add to Map
    - [ ] Detect chat message, send to recipients
- [ ] Debug/Polish
