## Venue Hire System

## Aims

* Practice how to apply a systematic object-oriented design process
* Gain experience in implementing an object-oriented program with multiple interacting classes
* Learn more about the Java class libraries

## Preamble

In this assignment, you will design and implement a prototype system that could serve as the "back-end" of a venue hire system (such as for holding conferences). Pay careful attention to the requirements and make sure you have a complete and sound understanding of them before developing an **object-oriented** design and implementing it. To do this you will need to read the requirements multiple times and make your own notes. **The sample input and output files do not form a complete specification**.

## Requirements

**You must read this assignment specification thoroughly. Failure to incorporate any elements may result in a very significant loss of marks (such as due to autotesting).**

In this venue hire system, customers can make, change and cancel reservations. Each venue has a number of rooms of various sizes: rooms are either small, medium or large. A reservation request has a named identifier and is for one or more rooms of a certain size (e.g. two small rooms and one large room) for a period of time given by a start date and an end date. A reservation request is either granted in full or is completely rejected by the system (a request cannot be partially fulfilled).

Assessment will be based on the design of your program in addition to correctness. You should submit at least a UML class diagram used for the design of your program, i.e. not generated from code afterwards.

The implementation should input and output data in the JSON format. It will read from STDIN (`System.in` in Java) and output to STDOUT (`System.out` in Java). The input will be a series of JSON objects, each containing a single command (on its own line). After reading in a JSON object, the implementation will immediately process that command; i.e. it cannot wait for all commands to be input before acting on them. You can assume `room` commands will precede all other commands, but beyond that there are no guarantees of the order or quantity of each command. The commands are as follows (the text in italics is what will vary):

* Specify that venue *venue* has a room with name *room* of size *size*.

    > { "command": "room", "venue": *venue*, "room": *room*, "size": *size* }

* Request *id* is from *startdate* to *endate* for *small* number of small rooms, *medium* number of medium rooms, and *large* number of large rooms.

    > { "command": "request", "id": *id*, "start": *startdate*, "end": *enddate*, "small": *small*, "medium": *medium*, "large": *large* }

* Change existing reservation *id* to be from *startdate* to *endate* for *small* number of small rooms, *medium* number of medium rooms, and *large* number of large rooms.

    > { "command": "change", "id": *id*, "start": *startdate*, "end": *enddate*, "small": *small*, "medium": *medium*, "large": *large* }

* Cancel reservation *id* and free up rooms
    > { "command": "cancel", "id": *id* }

* List the occupancy of each room in the venue *venue*
    > { "command": "list", "venue": *venue* }

To remove any ambiguity, **all** reservation requests and changes are fulfilled as follows: each venue is checked (in order of definition in the input) to determine whether it can satisfy **all** requested rooms, and if so, the first available rooms (again in order of definition in the input) are assigned to the reservation. The output should list the rooms assigned to the reservation (once again in order of definition in the input; see first example below). Do not try to fulfil requests by allocating larger rooms when a small room is requested, or by reassigning rooms to different reservations to create space, etc. For the `list` command, output an array containing the occupancy of each room at the specified venue in order of room declarations. The reservations for a given room should be output in order of date (see below).

In this system, dates of reservations are inclusive (i.e. a reservation starting/ending with a day excludes other reservations on this date).

No invalid input will be provided by the tests - so you are not required to handle any invalid input such as having end dates before start dates or malformed input.

For passing tests - you must pass the entirety of the test to receive marks on a correctness test - there will be no partial marks.

## Additional Requirements

You must ensure that your entire Gitlab repository uses less than 5MB of space. This includes the most recent 10 commits before a submission.

## Implementation

Starter code has been provided in your repository for this assignment, available here (replace z5555555 with your own zID):

https://gitlab.cse.unsw.edu.au/z5555555/20T2-cs2511-ass1

Create all your Java source files in the `unsw.venues` package. You may create subpackages if you wish, but this is not required. The main Java file is `VenueHireSystem.java`. Do not rename this class. The starter code includes the [JSON-java](https://stleary.github.io/JSON-java/) library and shows how to use it to read and write JSON formatted data. Other than this, you are **not allowed to use any third party libraries**.

For machine marking, the output will be directly compared to the expected output, so do not print out any extra debugging information or include extra fields in your JSON objects. You can assume that reservation identifiers are unique (e.g. there won't be two reservations under the name `Annual Meeting`). Similarly, dates will be in the ISO-8601 format `uuuu-MM-dd` (e.g. `2019-05-27`). This is the format output by `LocalDate.toString()`. All characters in venues, rooms and reservation identifiers will be alpha-numeric except for spaces. The input to this system is trusted, so you can assume it will not be malformed, fields will be of the right type, dates will be valid, end dates do not come before start dates, etc.

## Example

This is a concrete example input demonstrating the commands supported (comments are for explanation and will **not** appear in the actual input):

```JSON
# Venue Zoo has the Penguin room which is small
{ "command": "room", "venue": "Zoo", "room": "Penguin", "size": "small" }

# Venue Zoo has the Hippo room which is large
{ "command": "room", "venue": "Zoo", "room": "Hippo", "size": "large" }

# Venue Zoo has the Elephant room which is large
{ "command": "room", "venue": "Zoo", "room": "Elephant", "size": "large" }

# Venue Gardens has the Figtree room which is large
{ "command": "room", "venue": "Gardens", "room": "Figtree", "size": "large" }

# Request 'Annual Meeting' is for 1 large and 1 small room from 2019-03-25 to 2019-03-26
# Assign Penguin and Hippo rooms of Zoo
{ "command": "request", "id": "Annual Meeting", "start": "2019-03-25", "end": "2019-03-26", "small": 1, "medium": 0, "large": 1 }

# Request 'Mattress Convention' is for 1 large room from 2019-03-24 to 2019-03-27
# Assign Elephant room of Zoo since Hippo room is occupied
{ "command": "request", "id": "Mattress Convention", "start": "2019-03-24", "end": "2019-03-27", "small": 0, "medium": 0, "large": 1 }

# Request 'Dance Party' is for 1 large room from 2019-03-26 to 2019-03-26
# Assign Figtree room of Gardens
{ "command": "request", "id": "Dance Party", "start": "2019-03-26", "end": "2019-03-26", "small": 0, "medium": 0, "large": 1 }

# Change reservation 'Annual Meeting' to 1 small room from 2019-03-27 to 2019-03-29
# Deassign Penguin and Hippo rooms of Zoo and assign Penguin room of Zoo
{ "command": "change", "id": "Annual Meeting", "start": "2019-03-27", "end": "2019-03-29", "small": 1, "medium": 0, "large": 0 }

# Request 'CSE Autumn Ball' is for 1 small room from 2019-03-25 to 2019-03-26
# Assign Penguin room of Zoo
{ "command": "request", "id": "CSE Autumn Ball", "start": "2019-03-25", "end": "2019-03-26" , "small": 1, "medium": 0, "large": 0 }

# Cancel reservation 'Dance Party'
# Deassign Figtree room of Gardens
{ "command": "cancel", "id": "Dance Party" }

# Request "Vivid" is for 1 small room from 2019-03-26 to 2019-03-26
# Request cannot be fulfilled
{ "command": "request", "id": "Vivid", "start": "2019-03-26", "end": "2019-03-26", "small": 1, "medium": 0, "large": 0 }

# Output a list of the occupancy for all rooms at Zoo, in order of room declarations, then date
{ "command": "list", "venue": "Zoo" }
```

Of these commands, `request`, `change`, and `list` will produce output. The other commands do not. Inputting the above should yield the following (ordering of fields and indentation may differ):

```JSON
{ "status": "success", "venue": "Zoo", "rooms": ["Penguin", "Hippo"] }
{ "status": "success", "venue": "Zoo" ,"rooms": ["Elephant"] }
{ "status": "success", "venue": "Gardens", "rooms": ["Figtree"] }
{ "status": "success", "venue": "Zoo", "rooms": ["Penguin"] }
{ "status": "success", "venue": "Zoo", "rooms": ["Penguin"] }
{ "status": "rejected" }
[ { "room": "Penguin", "reservations": [
    { "id": "CSE Autumn Ball", "start": "2019-03-25", "end": "2019-03-26" },
    { "id": "Annual Meeting", "start": "2019-03-27", "end": "2019-03-29" }
    ] },
  { "room": "Hippo", "reservations": [] },
  { "room": "Elephant", "reservations": [
    {"id": "Mattress Convention", "start": "2019-03-24", "end": "2019-03-27"}
    ] }
]
```

Note that all commands produce a JSON object as output, except for `list` that produces a JSON array.

For commands that do not always succeed, the `status` field indicates whether the result was successful. If a reservation request or change cannot be fulfilled, the status should be `rejected`. In the case of such a rejection, no reservations should be added, changed or deleted. You can assume `change` and `cancel` have identifiers for existing reservations, `list` has a valid venue, and `room` is not used to add a room that has already been added.


## Assessment Criteria

* Correctness: Automatically assessed on a series of input tests, and interactive assessment during week-05 lab
* Design: Adherence to object-oriented design principles, clarity of UML diagrams and conformance of UML diagrams to code, JSON test files, and proper justifications during interactive assessment in week-05 lab
* Programming style: Adherence to standard Java programming style, understandable class and variable names, adequate Javadoc and comments


