__author__ = 'Netšajev'


# COLUMN , ROW  indexes starting from 0
kaart = [(2, 0), (1, 1), (2, 2), (1, 3)]
sizes = (4, 5)


def treasure_map(size, bombs):
    # map[row][column]
    map = [[0 for _ in range(size[0])] for _ in range(size[1])]
    # bomb -> [column][row]
    for bomb in bombs:
        map[bomb[1]][bomb[0]] = 'X'
        right = left = down = up = False

        # Means there is space on the 4 sides of the cell (right, left, down, up)
        if bomb[0] < size[0]:
            if map[bomb[1]][bomb[0]+1] != 'X':
                 map[bomb[1]][bomb[0]+1] += 1
            right = True
        if bomb[0] > 0:
            if map[bomb[1]][bomb[0]-1] != 'X':
                map[bomb[1]][bomb[0]-1] += 1
            left = True
        if bomb[1] < size[1]:
            if map[bomb[1]+1][bomb[0]] != 'X':
                map[bomb[1]+1][bomb[0]] += 1
            down = True
        if bomb[1] > 0:
            if map[bomb[1]-1][bomb[0]] != 'X':
                map[bomb[1]-1][bomb[0]] += 1
            up = not up
        # Check diagonals
        if right and up and map[bomb[1]-1][bomb[0]+1] != 'X':
            map[bomb[1]-1][bomb[0]+1] += 1
        if right and down and map[bomb[1]+1][bomb[0]+1] != 'X':
            map[bomb[1]+1][bomb[0]+1] += 1
        if left and up and map[bomb[1]-1][bomb[0]-1] != 'X':
            map[bomb[1]-1][bomb[0]-1] += 1
        if left and down and map[bomb[1]+1][bomb[0]-1] != 'X':
            map[bomb[1]+1][bomb[0]-1] += 1
    return map


def print_treasure(map):
    for row in map:
        print(''.join(str(x) for x in row))

print_treasure(treasure_map(sizes, kaart))