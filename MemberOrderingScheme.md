# Member Ordering Scheme #
By Bryan Bettis

This file explains this repositories standard for sorting the members of objects in java class files.

#### General rules: ####
- Public comes before protected comes before private
- (Static final) then (static) then (final) then everything else
- At the finest level of sorting, members are sorted loosely based on how commonly used they are or with the newest members last, and sometimes the order they are normally used in (e.g. setup() then cleanup()) or importance.


## Class Variables ##


## Enums ##


## Constructors ##

### No-Argument Constructor (If Used) ###

### All Others Sorted by Number of Arguments. ###


## Methods ##

### If using a thread, start() and then @Override run() are always first two ###

### Abstracts ###

### Getters ###
This includes boolean returns like "isKeyPressed".

### Setters ###
This includes functions that add something to a list like "addPlayerEntity"

### Everything Else ###