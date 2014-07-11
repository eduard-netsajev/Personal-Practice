__author__ = 'Netšajev'

# http://programarcadegames.com/labs/adventure/castle_map_02.png

room_list = []

# room = [THIS, N, E, S, W,]

#from 0 to 6

room = ['You are in a Bedroom 2.\nThere are rooms to the north and east.', 3, 1, None, None]
room_list.append(room)

room = ['You are in a South Hall\nThere are rooms to the north, east and west.', 4, 2, None, 0]
room_list.append(room)

room = ['You are in a dining room\nThere are rooms to the north and west.', 5, None, None, 1]
room_list.append(room)

room = ['You are in a Bedroom 1.\nThere are rooms to the east and south.', None, 4, 0, None]
room_list.append(room)

room = ['You are in a North Hall\nThere are rooms in every direction.', 6, 5, 1, 3]
room_list.append(room)

room = ['You are in a kitchen\nThere are rooms to the south and west.', None, None, 2, 4]
room_list.append(room)

room = ['You are in a balcony\nThere is a passage to the south', None, None, 4, None]
room_list.append(room)


def print_room(this_room):
    if this_room[1] is None:
        print("   ____________")
    else:
        print("   ___||  ||___")

    print("   |           |")

    if this_room[4] is None:
        print("   |     ", end="")
    else:
        print("''''     ", end="")

    if this_room[2] is None:
        print("      |")
    else:
        print("      ''''")

    if this_room[4] is None:
        print("   |     ", end="")
    else:
        print("....     ", end="")

    if this_room[2] is None:
        print("      |")
    else:
        print("      ....")

    print("   |           |")

    if this_room[3] is None:
        print("   |___________|\n")
    else:
        print("   |___    ____|\n      ||  ||")

    pass


current_room = 0

playing = True

while playing:
    print(room_list[current_room][0])

    print_room(room_list[current_room])

    direction = input("Where you want to go? ")
    print()

    if len(direction) < 1:
        pass
    elif direction.lower() in ['exit', 'quit']:
        playing = False
    elif direction[0].lower() == 'n':
        next_room = room_list[current_room][1]
        if next_room is None:
            print("You can't go in that direction")
        else:
            current_room = next_room
    elif direction[0].lower() == 'e':
        next_room = room_list[current_room][2]
        if next_room is None:
            print("You can't go in that direction")
        else:
            current_room = next_room
    elif direction[0].lower() == 's':
        next_room = room_list[current_room][3]
        if next_room is None:
            print("You can't go in that direction")
        else:
            current_room = next_room
    elif direction[0].lower() == 'w':
        next_room = room_list[current_room][4]
        if next_room is None:
            print("You can't go in that direction")
        else:
            current_room = next_room
    else:
        print("Excuse me, where?")
