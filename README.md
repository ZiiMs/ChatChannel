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
- [ ] Commands:
    - [x] ~Create command~
        - [X] ~Channel creates and saves to database~
    - [X] ~Join channel command~
        - [X] ~Adds the player to database, with channel name if channel exists~
        - [X] ~Receives messages sent in channel~
    - [X] ~List current channels~
    - [X] ~Edit channel~
        - [X] ~edit color, changes color of channel~
        - [X] ~edit channel name, changes name of channel~
        - [X] ~edit channel prefix, changes the symbol used to type in chat.~
- [X] ~Auto join channel~
    - [X] ~On join, joins channel automatically~
- [ ] Prepare for deploy
    - [ ] Debug/Stress test
    - [ ] Polish/Finalize
