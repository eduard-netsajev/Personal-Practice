__author__ = 'Netšajev'

from simulator import *


class Robot(Agent):

    def __init__(self, world, x, y, direction):
        super().__init__(world, x, y, direction)
        self.x = x
        self.y = y
        self.direction = direction
        self.world = world
        self.name = self.ID + 1
        SkyNet.connect(self)

    def report_situation(self):
        area = [[0 for _ in range(5)] for _ in range(5)]
        area[2][2] = self.name
        for i in range(8):
            info = self.detect(self.world, i)
            if info is None:
                continue
            direction = [k*info[0] for k in SkyNet.directions[i]]
            area[2 + direction[1]][2 + direction[0]] = info[1]
        for row in area:
            print(row)

    def reason(self):
        self.detect(self.world, 3)


class SkyNet:

    target = 'World Domination'
    world = None
    robots = 0
    field_map = [[]]
    network = []

    directions = [(0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1), (-1, 0), (-1, -1)]

    @classmethod
    def give_orders(cls):
        for robot in cls.network:
            robot.report_situation()
        for robot in cls.network:
            if 2 < robot.direction < 6:
                robot.y += 1
            elif robot.direction == 0 or robot.direction % 2 == 1:
                robot.y -= 1
            if 0 < robot.direction < 4:
                robot.x += 1
            elif 4 < robot.direction < 8:
                robot.x -= 1
            cls.update_map(robot)

    @classmethod
    def connect(cls, new_robot):
        if cls.world is None:
            cls.world = new_robot.world
        elif cls.world != new_robot.world:
            raise ConnectionError
        cls.network.append(new_robot)
        cls.robots += 1
        cls.update_map(new_robot)

    @classmethod
    def count_robots(cls):
        print("Robots in system: " + str(cls.robots))

    @classmethod
    def update_map(cls, new_robot):
        x_diff = new_robot.x + 1 - cls.get_width()

        if x_diff > 0:
            for row in cls.field_map:
                row += [0] * x_diff

        y_diff = new_robot.y + 1 - cls.get_height()
        if y_diff > 0:
            for _ in range(y_diff):
                cls.field_map += [[0] * cls.get_width()]

        cls.field_map[new_robot.y][new_robot.x] = "."

    @classmethod
    def get_width(cls):
        return len(cls.field_map[0])

    @classmethod
    def get_height(cls):
        return len(cls.field_map)

    @classmethod
    def print_knowledge(cls):
        grid = [[x for x in row] for row in cls.field_map]
        icons = [chr(0x2191), chr(0x2197), chr(0x2192), chr(0x2198),
                 chr(0x2193), chr(0x2199), chr(0x2190), chr(0x2196)]

        for robot in cls.network:
            grid[robot.y][robot.x] = icons[robot.direction % 8]

        print("-"*(cls.get_width() + 2))
        for i in range(cls.get_height()):
            print("|", end="")
            for j in range(cls.get_width()):
                print(grid[i][j], end="")
            print("|")
        print("-"*(cls.get_width()+2))


def main():
    world = World(width=60, height=10, sleep_time=1, treasure=None,
                  obstacles=[(5, 5)], reliability=0.9)
    robots = [Robot(world, 4, 1, 2), Robot(world, 2, 3, 2)]
    world.print_state()
    SkyNet.print_knowledge()
    #exit()
    while True:
        SkyNet.give_orders()
        world.tick()
        world.print_state()
        SkyNet.print_knowledge()
        exit()




if __name__ == "__main__":
    main()