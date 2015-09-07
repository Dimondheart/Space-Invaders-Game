# Member Ordering Scheme #
By Bryan Bettis

This file explains this repositories standard for sorting the members of objects in java class files.

#### General rules: ####
- Public comes before protected comes before private
- At the finest level of sorting, members are sorted loosely based on how commonly used they are or with the newest members last, and sometimes the order they are normally used in (e.g. setup() then cleanup()) or importance.


## Variables ##

### static final (constants) ###

### static ###

### final ###

### Everything Else ###


## Enums ##


## Constructors ##

### No-Argument Constructor ###

### All Others Sorted by Number of Arguments. ###


## Methods ##

### If using a thread, run() goes first ###

### Abstracts ###

### Getters ###
This includes boolean returns like "isKeyPressed".

### Setters ###

### Everything Else ###