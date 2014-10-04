__author__ = 'Netšajev'

from simulator import *


class Robot(Agent):
    def __init__(self, world, x, y, direction):
        super().__init__(world, x, y, direction)
        self.x = x
        self.y = y
        self.direction = direction
        self.world = world
        self.order = -1
        SkyNet.connect(self)

    def report_situation(self):
        for i in range(8):
            info = self.detect(self.world, i)
            if info is None:
                SkyNet.update_map(self.x + SkyNet.directions[i][0] * 2, self.y + SkyNet.directions[i][1] * 2)
                SkyNet.field_map[self.y + SkyNet.directions[i][1]][self.x + SkyNet.directions[i][0]] = -1
                SkyNet.field_map[self.y + SkyNet.directions[i][1] * 2][self.x + SkyNet.directions[i][0] * 2] = -1
                continue

            direction = [k * info[0] for k in SkyNet.directions[i]]
            SkyNet.update_map(self.x + direction[0], self.y + direction[1])
            if info[0] > 1:
                SkyNet.update_map(self.x + SkyNet.directions[i][0], self.y + SkyNet.directions[i][1])

            if info[1] == -1:  # Wall detected
                if i == 2:
                    SkyNet.width = self.x + info[0]
                    SkyNet.discover_map_width = False
                    self.order = -1
                    SkyNet.width_searcher = None
                    print("Field width is " + str(SkyNet.width))
                elif i == 4:
                    SkyNet.height = self.y + info[0]
                    SkyNet.discover_map_height = False
                    self.order = -1
                    SkyNet.height_searcher = None
                    print("Field height is " + str(SkyNet.height))

            elif info[1] == -2:  # Obstacle detected
                # print("Obstacle! Robot " + str(self.name) + " Direction " + str(i))
                SkyNet.field_map[self.y + direction[1]][self.x + direction[0]] = info[1]
            elif info[1] == -3:  # Treasure detected
                # print("Treasure! Robot " + str(self.name) + " Direction " + str(i))
                SkyNet.discover_treasure = False
                for robot in SkyNet.treasure_searchers:
                    robot.order = -1
                SkyNet.treasure_searchers = []
                SkyNet.field_map[self.y + direction[1]][self.x + direction[0]] = info[1]

    def execute(self, order):
        self.order = order
        turn = SkyNet.ensure_safety(self, self.continue_task())

        self.turn(self.world, turn)
        self.direction += turn
        self.direction %= 8

    def continue_task(self):
        turn = 0
        if self.order == 3:
            if 2 < self.direction < 6:
                turn = -1
            elif self.direction == 2:
                turn = 0
            else:
                turn = 1

        elif self.order == 2:
            if 4 < self.direction < 8:
                turn = -1
            elif self.direction == 4:
                turn = 0
            else:
                turn = 1

        # print(turn)

        return turn


class SkyNet:
    target = 'World Domination'
    world = None
    robots = 0
    field_map = [[]]
    network = []

    width = -1
    height = -1
    treasure = None

    # Targets
    discover_map_width = True
    discover_map_height = True
    discover_treasure = True

    # Groups
    width_searcher = None
    height_searcher = None
    treasure_searchers = []

    directions = [(0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1), (-1, 0), (-1, -1)]

    @classmethod
    def give_orders(cls):
        for robot in cls.network:
            # print("Robots: report!")
            robot.report_situation()

        if cls.discover_map_width and cls.width_searcher is None:
            cls.width_searcher = cls.find_robot(3)
            if cls.width_searcher is not None:
                cls.width_searcher.order = 3
        if cls.discover_map_height and cls.height_searcher is None:
            cls.height_searcher = cls.find_robot(2)
            if cls.height_searcher is not None:
                cls.height_searcher.order = 2

        for robot in cls.network:
            print("Robot {} at {} {} direction {} with order {}"
                  .format(robot.name, robot.x, robot.y, robot.direction, robot.order))
            if robot.order == -1:
                if cls.discover_treasure:
                    cls.treasure_searchers.append(robot)
                    robot.execute(1)
                else:  # Go take treasure
                    robot.execute(0)
            else:
                robot.execute(robot.order)

            if 2 < robot.direction < 6:
                robot.y += 1
            elif robot.direction == 0 or robot.direction % 2 == 1:
                robot.y -= 1
            if 0 < robot.direction < 4:
                robot.x += 1
            elif 4 < robot.direction < 8:
                robot.x -= 1

    @classmethod
    def connect(cls, new_robot):
        if cls.world is None:
            cls.world = new_robot.world
        elif cls.world != new_robot.world:
            raise ConnectionError
        cls.robots += 1
        new_robot.name = cls.robots
        cls.network.append(new_robot)
        cls.update_map(new_robot.x, new_robot.y)
        cls.change_width_searcher(new_robot)
        cls.change_height_searcher(new_robot)

    @classmethod
    def change_width_searcher(cls, new_robot):
        if cls.discover_map_width and cls.height_searcher != new_robot:
            if cls.width_searcher is not None:
                if new_robot.x <= cls.width_searcher.x:
                    return
                else:
                    cls.width_searcher.order = -1

            cls.width_searcher = new_robot
            cls.width_searcher.order = 3

    @classmethod
    def change_height_searcher(cls, new_robot):
        if cls.discover_map_height and cls.width_searcher != new_robot:
            if cls.height_searcher is not None:
                if new_robot.x <= cls.height_searcher.x:
                    return
                else:
                    cls.height_searcher.order = -1

            cls.height_searcher = new_robot
            cls.height_searcher.order = 2

    @classmethod
    def count_robots(cls):
        print("Robots in system: " + str(cls.robots))

    @classmethod
    def update_map(cls, x, y):
        x_diff = x + 1 - cls.get_width()

        if x_diff > 0:
            for row in cls.field_map:
                row += [0] * x_diff

        y_diff = y + 1 - cls.get_height()
        if y_diff > 0:
            for _ in range(y_diff):
                cls.field_map += [[0] * cls.get_width()]

        cls.field_map[y][x] = -1

    @classmethod
    def get_width(cls):
        if cls.width < 0:
            return len(cls.field_map[0])
        else:
            return cls.width

    @classmethod
    def get_height(cls):
        if cls.height < 0:
            return len(cls.field_map)
        else:
            return cls.height

    @classmethod
    def ensure_safety(cls, robot, intention):
        variants = [-1, 0, 1]
        print("Robot at {} {} direction {} asks to turn {}".format(robot.x, robot.y, robot.direction, intention))

        variants.remove(intention)

        direction = (robot.direction + intention) % 8
        results_one = robot.detect(robot.world, direction)
        if results_one is None or (results_one[1] == -3 and results_one[0] == 2):
            print("His intentions are okay")
            return intention

        print("His intentions are bad")
        variant_two = variants.pop(random.randint(0, 1))
        direction = (robot.direction + variant_two) % 8
        results_two = robot.detect(robot.world, direction)
        print("Random side {} and there is ".format(variant_two) + str(results_two))
        if results_two is None or (results_two[1] == -3 and results_two[0] == 2):
            return variant_two

        direction = (robot.direction + variants[0]) % 8
        results_last = robot.detect(robot.world, direction)
        if results_last is None or (results_last[1] == -3 and results_last[0] == 2):
            print("Turn because other side has " + str(results_last))
            return variants[0]

        if results_one[1] in (-3):
            return intention
        if results_two[1] in (-3):
            return variant_two
        if results_last[1] in (-3):
            return variants[0]

        if results_one[0] == 2:
            return intention
        if results_two[0] == 2:
            return variant_two
        if results_last[0] == 2:
            return variants[0]

        return random.randint(-1, 1)

    @classmethod
    def print_knowledge(cls):
        grid = [[x for x in row] for row in cls.field_map]
        icons = [chr(0x2191), chr(0x2197), chr(0x2192), chr(0x2198),
                 chr(0x2193), chr(0x2199), chr(0x2190), chr(0x2196)]

        for robot in cls.network:
            grid[robot.y][robot.x] = icons[robot.direction % 8]

        print("-" * (cls.get_width() + 2))
        for i in range(cls.get_height()):
            print("|", end="")
            for j in range(cls.get_width()):
                data_point = grid[i][j]
                if data_point == -1:
                    print('.', end="")
                elif data_point == -2:
                    print('*', end="")
                elif data_point == -3:
                    print('X', end="")
                else:
                    print(data_point, end="")
            print("|")
        print("-" * (cls.get_width() + 2))

    @classmethod
    def find_robot(cls, purpose):
        if purpose == 3:
            max_x = 0
            searcher = None
            for robot in cls.network:
                if robot.x > max_x and robot != cls.height_searcher:
                    searcher = robot
            return searcher
        elif purpose == 2:
            max_y = 0
            searcher = None
            for robot in cls.network:
                if robot.y > max_y and robot != cls.width_searcher:
                    searcher = robot
            return searcher

    @classmethod
    def get_target(cls):
        """
        Generator to use for generating target cells for robots.
        """
        q = -1
        while True:
            q = (q+1) % 4

            width = cls.get_width()
            height = cls.get_height()

            if q == 0:
                for i in range(height/2 + 1):
                    for j in range(width/2 + 1):
                        if cls.field_map[i][j] == 0:
                            cls.field_map[i][j] = -4  # TODO swap to -1 if no problems
                            yield j, i  # x, y
            elif q == 1:
                for i in range(height/2 + 1):
                    for j in range(width/2, width):
                        if cls.field_map[i][j] == 0:
                            cls.field_map[i][j] = -4  # TODO swap to -1 if no problems
                            yield j, i  # x, y
            elif q == 2:
                for i in range(height/2, height):
                    for j in range(width/2 + 1):
                        if cls.field_map[i][j] == 0:
                            cls.field_map[i][j] = -4  # TODO swap to -1 if no problems
                            yield j, i  # x, y
            elif q == 4:
                for i in range(height/2, height):
                    for j in range(width/2, width):
                        if cls.field_map[i][j] == 0:
                            cls.field_map[i][j] = -4  # TODO swap to -1 if no problems
                            yield j, i  # x, y


def main():
    world = World(width=30, height=10, sleep_time=1, treasure=(6, 1),
                  obstacles=[(5, 5), (8, 3)], reliability=0.9)
    robots = [Robot(world, 4, 1, 5), Robot(world, 2, 3, 2)]
    world.print_state()
    SkyNet.print_knowledge()
    while True:
        SkyNet.give_orders()
        world.tick()
        #world.print_state()
        SkyNet.print_knowledge()
        # exit()


if __name__ == "__main__":
    main()