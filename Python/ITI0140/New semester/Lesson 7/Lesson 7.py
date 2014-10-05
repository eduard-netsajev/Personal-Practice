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
        self.target = None
        self.last_seven = []
        self.next_turn = 0
        self.next_x = x
        self.next_y = y

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
                    # print("Field width is " + str(SkyNet.width))
                elif i == 4:
                    SkyNet.height = self.y + info[0]
                    SkyNet.discover_map_height = False
                    self.order = -1
                    SkyNet.height_searcher = None
                    # print("Field height is " + str(SkyNet.height))

            elif info[1] == -2:  # Obstacle detected
                # print("Obstacle! Robot " + str(self.name) + " Direction " + str(i))
                SkyNet.field_map[self.y + direction[1]][self.x + direction[0]] = info[1]
            elif info[1] == -3:  # Treasure detected
                # print("Treasure! Robot " + str(self.name) + " Direction " + str(i))
                SkyNet.discover_treasure = False
                self.order = 0

                if SkyNet.width_searcher is not None:
                    SkyNet.width_searcher.order = 0
                    SkyNet.discover_map_width = False
                    SkyNet.width_searcher = None
                if SkyNet.height_searcher is not None:
                    SkyNet.height_searcher.order = 0
                    SkyNet.discover_map_width = False
                    SkyNet.width_searcher = None

                SkyNet.treasure = self.x + direction[0], self.y + direction[1]
                for robot in SkyNet.treasure_searchers:
                    robot.order = 0
                    robot.target = SkyNet.treasure

                SkyNet.treasure_searchers = []
                SkyNet.field_map[SkyNet.treasure[1]][SkyNet.treasure[0]] = info[1]

    def calculate_turn(self, order):

        if order < 5:
            self.order = order
            turn = self.continue_task()
            # self.next_turn = SkyNet.ensure_safety(self, turn)
        else:
            turn = random.randint(-1, 1)
            # self.next_turn = turn
            #print("Robot {} Random turn: ".format(self.name) + str(turn))

        self.next_turn = SkyNet.ensure_safety(self, turn)
        next_direction = (self.direction + self.next_turn) % 8

        self.next_x = self.x + SkyNet.directions[next_direction][0]
        self.next_y = self.y + SkyNet.directions[next_direction][1]

    def execute_turn(self):
        self.last_seven.append(self.next_turn)
        if len(self.last_seven) > 7:
            self.last_seven.pop(0)

        self.turn(self.world, self.next_turn)
        self.direction += self.next_turn
        # print("Robot {} turned {} with order {}".format(self.name, self.next_turn, self.order))
        self.direction %= 8

    def continue_task(self):
        turn = -1
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
        else:
            if self.order == 0:
                target = SkyNet.treasure
            else:
                if self.target is None or SkyNet.field_map[self.target[1]][self.target[0]] != -7:
                    self.target = SkyNet.get_target().__next__()
                    # print("New target given: " + str(self.target))
                target = self.target
            target = SkyNet.get_path(self, target)
            turn = SkyNet.get_aim(self.direction, target)

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

    dump = ""

    @classmethod
    def give_orders(cls):
        for robot in cls.network:
            robot.report_situation()

        cls.print_knowledge()

        if cls.discover_map_width and cls.width_searcher is None:
            cls.width_searcher = cls.find_robot(3)
            if cls.width_searcher is not None:
                cls.width_searcher.order = 3
        if cls.discover_map_height and cls.height_searcher is None:
            cls.height_searcher = cls.find_robot(2)
            if cls.height_searcher is not None:
                cls.height_searcher.order = 2

        for robot in cls.network:
            # print("Robot {} at {} {} direction {} with order {} and target {}"
            # .format(robot.name, robot.x, robot.y, robot.direction, robot.order, robot.target))
            if robot.order == -1:
                if cls.discover_treasure:
                    cls.treasure_searchers.append(robot)
                    robot.calculate_turn(1)
                else:  # Go take treasure
                    robot.calculate_turn(0)
            else:
                robot.calculate_turn(robot.order)

        condition = True
        cls.dump = ""
        counter = 0
        while condition:  # TODO Fix collisions
            counter += 1
            if counter > 10:
                # print(cls.dump)
                break
            colliding_robots = []
            positions = []
            for i in range(len(cls.network)):

                real_pos = (cls.network[i].x, cls.network[i].y)
                pos = (cls.network[i].next_x, cls.network[i].next_y)

                try:
                    positions.index(pos)
                    colliding_robots.append(pos)
                except ValueError:
                    positions.append(pos)

                try:
                    positions.index(real_pos)
                    colliding_robots.append(real_pos)
                except ValueError:
                    positions.append(real_pos)

                for robot in cls.network:
                    if (robot.x, robot.y) in colliding_robots:
                        # print("Robot " + str(robot.name) + " Recalculated from " + str(robot.next_turn))
                        # print("Was pos " + str((robot.x, robot.y)))
                        robot.calculate_turn(5)
                        # print("To > " + str(robot.next_turn))
                        # print("Now pos " + str((robot.x, robot.y)))

                    if (robot.next_x, robot.next_y) in colliding_robots:
                        # print("Robot " + str(robot.name) + " Recalculated from " + str(robot.next_turn))
                        # print("Was pos " + str((robot.next_x, robot.next_y)))
                        robot.calculate_turn(5)
                        # print("To > " + str(robot.next_turn))
                        # print("Now pos " + str((robot.next_x, robot.next_y)))
            cls.dump += "Collide at: " + str(colliding_robots) + "\n" + str(positions) + "\n"

            condition = len(colliding_robots) != 0
            cls.dump += "\n NEW ROUND WITH LEN " + str(len(colliding_robots)) + "\n"

        for robot in cls.network:
            robot.execute_turn()

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

        variants.remove(intention)

        direction = (robot.direction + intention) % 8
        results_one = robot.detect(robot.world, direction)
        if results_one is None or results_one[1] == -3 or (results_one[1] >= 0 and results_one[0] > 1):
            return intention

        variant_two = variants.pop(random.randint(0, 1))
        direction = (robot.direction + variant_two) % 8
        results_two = robot.detect(robot.world, direction)
        if results_two is None or results_two[1] == -3 or (results_two[1] >= 0 and results_two[0] > 1):
            return variant_two

        direction = (robot.direction + variants[0]) % 8
        results_last = robot.detect(robot.world, direction)
        if results_last is None or results_last[1] == -3 or (results_last[1] >= 0 and results_last[0] > 1):
            return variants[0]

        if results_one[0] == results_two[0] == results_last[0]:
            if robot.direction in (1, 5):
                return 1
            elif robot.direction in (3, 7):
                return -1
        else:
            if results_one[0] > 1:
                return intention
            if results_two[0] > 1:
                return variant_two
            if results_last[0] > 1:
                return variants[0]

        if sum(robot.last_seven) > 5:
            return -1
        if sum(robot.last_seven) < -5:
            return 1

        if robot.direction == 4:
            if robot.x < cls.get_width() / 2:
                return -1
            else:
                return 1

        if robot.direction == 0:
            if robot.x < cls.get_width() / 2:
                return 1
            else:
                return -1

        if robot.direction == 2:
            if robot.y < cls.get_height() / 2:
                return 1
            else:
                return -1

        if robot.direction == 6:
            if robot.y < cls.get_height() / 2:
                return -1
            else:
                return 1

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
                elif data_point == -7:
                    print('?', end="")
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

    quoter = -1

    @classmethod
    def get_target(cls):
        """
        Generator to use for generating target cells for robots.
        """
        while True:
            cls.quoter = (cls.quoter + 1) % 4
            width = cls.get_width()
            height = cls.get_height()

            if cls.quoter == 0:
                for i in range(int(height / 2) + 1):
                    for j in range(int(width / 2) + 1):
                        if cls.field_map[i][j] == 0:
                            cls.field_map[i][j] = -7
                            yield j, i  # x, y
            elif cls.quoter == 1:
                for i in range(height - 1, int(height / 2), -1):
                    for j in range(width - 1, int(width / 2), -1):
                        if cls.field_map[i][j] == 0:
                            cls.field_map[i][j] = -7
                            yield j, i  # x, y
            elif cls.quoter == 2:
                for i in range(int(height / 2) + 1):
                    for j in range(width - 1, int(width / 2), -1):
                        if cls.field_map[i][j] == 0:
                            cls.field_map[i][j] = -7
                            yield j, i  # x, y
            elif cls.quoter == 3:
                for i in range(height - 1, int(height / 2), -1):
                    for j in range(int(width / 2) + 1):
                        if cls.field_map[i][j] == 0:
                            cls.field_map[i][j] = -7
                            yield j, i  # x, y

    @classmethod
    def get_path(cls, robot, target):

        horizontal = (target[0] - robot.x)
        vertical = (target[1] - robot.y)

        if horizontal == 0 and vertical < 0:
            if vertical == -1:
                if robot.direction == 6:
                    return 5
                elif robot.direction == 2:
                    return 3
            return 0
        if horizontal == 0 and vertical > 0:
            if vertical == 1:
                if robot.direction == 6:
                    return 7
                elif robot.direction == 2:
                    return 1
            return 4
        if horizontal < 0 and vertical == 0:
            if horizontal == -1:
                if robot.direction == 0:
                    return 1
                elif robot.direction == 4:
                    return 3
            return 6
        if horizontal > 0 and vertical == 0:
            if horizontal == 1:
                if robot.direction == 0:
                    return 7
                elif robot.direction == 4:
                    return 5
            return 2
        if horizontal < 0 and vertical < 0:
            return 7
        if horizontal > 0 and vertical > 0:
            return 3
        if horizontal < 0 < vertical:
            return 5
        if horizontal > 0 > vertical:
            return 1

        print("Midagi läinud valesti.")
        print(horizontal)
        print(vertical)

    @classmethod
    def get_aim(cls, direction, target):
        direction_deg = cls.direction_to_degrees(direction)
        target_deg = cls.direction_to_degrees(target)
        diff = direction_deg - target_deg
        if diff == 0:
            return 0
        elif diff > 180 or -180 < diff < 0:
            return -1
        else:
            return 1

    @staticmethod
    def direction_to_degrees(direction):
        result = ((8 - direction) % 8 * 45)
        if result > 180:
            result -= 360
        return result


def main():
    world = World(width=30, height=10, sleep_time=1, treasure=None,
                  obstacles=[(5, 5), (8, 3), (6, 2), (3, 4), (3, 6), (3, 5), (4, 7), (5, 5)], reliability=1)
    robots = [
        # Robot(world, 4, 1, 5), Robot(world, 2, 3, 2), Robot(world, 7, 4, 6),
        Robot(world, 10, 4, 2), Robot(world, 5, 4, 1)]
    world.print_state()
    SkyNet.print_knowledge()

    while True:
        SkyNet.give_orders()
        try:
            world.tick()
        except RobotFoundTreasureException:
            print("Robots found the treasure!")
            break
        except RobotCollisionException:
            print("Robots collided. They had no choice")
            # print(SkyNet.dump)
            break
        except RobotDonutsAreBoringException:
            print("Some robot made a full circle. Gert thinks it must be useless move.")
            break
        except RobotObjectCrashException:
            print("Some robot crashed into an obstacle. He had no choice")
            break
        except RobotWallCrashException:
            print("Some robot crashed into a wall. He had no choice")
            break
    SkyNet.print_knowledge()


if __name__ == "__main__":
    main()