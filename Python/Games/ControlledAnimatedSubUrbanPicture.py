__author__ = 'Netšajev'

import pygame
import random

pygame.init()

# Define some colors
BLACK = (0, 0, 0)
WHITE = [255, 255, 255]
GREEN = (0, 255, 0)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
DARKGREEN = (42, 115, 28)
YELLOW = (255, 255, 0)
DARKYELLW = (255, 180, 0)
SILVER = (180, 180, 180)
GRAY = (127, 127, 127)
NEARWHITE = (240, 240, 240)
DARKRED = (170, 0, 0)
BROWN = (120, 30, 30)
STARCOLOR = (150, 150, 255)

pi = 3.141592653

scr_size = [800, 450]
screen = pygame.display.set_mode(scr_size)
pygame.display.set_caption("Animated Suburban by Eduard")

# Loop until the user clicks the close button.
done = False

# Used to manage how fast the screen updates
clock = pygame.time.Clock()


class Picture(object):
    def __init__(self, x, y, size=7):
        self.x = x
        self.y = y
        self.size = size


class Star(object):

        seen = 0

        def __init__(self, x, y):
            self.x = x
            self.y = y

        def draw(self):
            if self.seen:
                pygame.draw.circle(screen, STARCOLOR, [self.x, self.y], 2)

        def show(self):
            if random.randint(0, 2000) == 77:
                self.seen = 1

        def hide(self):
            if random.randint(0, 25) == 12:
                self.seen = 0


class Cloud(Picture):

    time = 0
    speed = 0
    MIN_SPEED = 7

    def __init__(self, x, y, size=7, speed=0):
        super().__init__(x, y, size)
        if speed == 0:
            self.speed = random.randint(1, self.MIN_SPEED)
        else:
            self.speed = speed

    def move(self):

        if self.x > 850:
            self.x = -150
            self.y = random.randrange(-20, 110)
            self.speed = random.randint(1, self.MIN_SPEED)
            self.size = random.randint(1, 10)
        else:
            if self.speed == 1:
                self.x += 1
            elif self.speed == 2:
                self.x += random.randint(1, 2)
            elif self.speed == -1:
                pass
            else:
                if self.time+1 == self.speed:
                    self.x += 1
                    self.time = 1
                else:
                    self.time += 1


class SmallCloud(Cloud):

    def draw_cloud(self):

        pygame.draw.ellipse(screen, WHITE, [self.x, self.y, self.size*10, self.size*4])
        pygame.draw.ellipse(screen, NEARWHITE, [self.x, self.y, self.size*10, self.size*4], 1)

        pygame.draw.ellipse(screen, WHITE, [self.x + self.size*3, self.y + self.size*2, self.size*10, self.size*4])
        pygame.draw.ellipse(screen, NEARWHITE, [self.x + self.size*3, self.y+self.size*2, self.size*10, self.size*4], 1)


class BigCloud(Cloud):

    def draw_cloud(self):
        pygame.draw.ellipse(screen, WHITE, [self.x + self.size*4, self.y, self.size*10, self.size*5])
        pygame.draw.ellipse(screen, WHITE, [self.x, self.y + self.size*3/2, self.size*18, self.size*6])
        pygame.draw.ellipse(screen, WHITE, [self.x + self.size*4, self.y + self.size*4, self.size*10, self.size*5])


class Sun(Picture):

    mode = 1
    direction = 1
    timer = 0

    def draw_sun(self):
        if self.mode == 1:
            pygame.draw.ellipse(screen, YELLOW, [self.x, self.y, self.size * 10, self.size * 10])
            pygame.draw.ellipse(screen, DARKYELLW, [self.x + 1.5 * self.size, self.y + 1.5 * self.size,
                                                    self.size * 7, self.size * 7])
        else:
            pygame.draw.ellipse(screen, NEARWHITE, [self.x + 1.5 * self.size, self.y + 1.5 * self.size,
                                                    self.size * 7, self.size * 7])

    def move(self):

        self.x += 1
        if self.timer == 3:
            self.y -= self.direction
            self.timer = 1
        else:
            self.timer += 1
        if self.y == 5:
            self.direction *= -1

        if self.x > 1000:
            self.x = -150
            self.y = 250
            self.mode *= -1
            self.direction *= -1


class House(Picture):

    win1 = False
    win2 = False
    win3 = True

    def __init__(self, x, y, size=24):
        super().__init__(x, y, size)

    def draw_house(self):

        if WHITE[0] == 240:
            self.win1 = random.randint(1, 3) == 2
            self.win2 = random.randint(1, 3) == 2
            self.win3 = random.randint(1, 3) == 2

        win1color = BLACK
        win2color = BLACK
        win3color = BLACK

        if self.win1:
            win1color = YELLOW
        if self.win2:
            win2color = YELLOW
        if self.win3:
            win3color = YELLOW

        doorw = self.size * 2
        doorh = self.size * 6

        pnt_a = [self.x, self.y + self.size * 4]
        pnt_b = [self.x + self.size * 10, pnt_a[1]]
        pnt_k = [self.x + self.size * 1, self.y + self.size * 14]
        pnt_n = [pnt_k[0] + doorw, pnt_k[1]]

        #DRAW ROOF
        pygame.draw.polygon(screen, DARKRED, [pnt_a, [self.x + self.size * 5, self.y - self.size / 2], pnt_b])
        #DRAW MAIN BUILDING
        pygame.draw.rect(screen, GRAY, [self.x, self.y + self.size * 4, self.size * 10, self.size * 10])
        #DRAW A DOORROOF
        pygame.draw.ellipse(screen, YELLOW, [pnt_k[0], pnt_k[1] - doorh + self.size/3, self.size*2, self.size*2])
        #DRAW A DOOR
        pygame.draw.polygon(screen, BROWN, [pnt_k, [pnt_k[0], pnt_k[1] + self.size * 3 / 2 - doorh],
                                            [pnt_n[0], pnt_n[1] + self.size * 3 / 2 - doorh], pnt_n])
        #DRAW A ROAD IN FRONT OF THE HOUS
        pygame.draw.polygon(screen, SILVER, [pnt_k, [pnt_k[0] + self.size, pnt_k[1] + self.size * 3 / 2],
                                             [pnt_n[0] + self.size, pnt_n[1] + self.size * 3 / 2], pnt_n])
        #DRAW UPPER WINDOWS
        pygame.draw.rect(screen, win1color, [self.x + self.size/2, self.y + self.size * 9/2,
                                             self.size * 4, self.size * 3])
        pygame.draw.rect(screen, win2color, [self.x + self.size*11/2, self.y + self.size * 9/2,
                                             self.size * 4, self.size * 3])
        #DRAW LOWER WINDOW
        pygame.draw.rect(screen, win3color, [self.x + self.size*11/2, self.y + self.size * 19/2,
                                             self.size * 4, self.size * 3])
        #DRAW A DOORKNOB
        pygame.draw.ellipse(screen, BLACK, [pnt_k[0]+self.size/8, pnt_k[1]-self.size*5/2, self.size/4, self.size/4])


# -------- Main Program Loop -----------


def draw_ground(sky=0.65, road=40):
    pygame.draw.rect(screen, DARKGREEN, [0, scr_size[1] * sky, scr_size[0], scr_size[1] * (1 - sky)+10])
    pygame.draw.rect(screen, SILVER, [0, scr_size[1] - road - 20, scr_size[0], road])


def calculate_sky_color(sun, skycolor):
    blu = skycolor[2]
    gre = skycolor[1]

    if sun.mode == 1 and sun.x % 5 == 0:
        if sun.direction == 1:
            if blu < 250:
                blu += 1
            if gre < 120:
                gre += 1
        else:
            if blu > 80:
                blu -= 3
            if gre > 0:
                gre -= 3
    elif sun.mode != 1:
        gre = 0
        blu = 80
    color = [0, gre, blu]

    # Side effect on clouds

    if sun.mode == 1:
        for colour in range(len(WHITE)):
            if WHITE[colour] < 255:
                WHITE[colour] += 1
    else:
        for colour in range(len(WHITE)):
            if WHITE[colour] > 208:
                WHITE[colour] -= 1

    return color


def draw_sign(x, y, what, size=5):
    pygame.draw.rect(screen, DARKGREEN, [x, y, len(what)*size*1.5, size*3])
    pygame.draw.rect(screen, BLACK, [x+size*7, y+size*3, size*3, size*16])
    pygame.draw.rect(screen, BLACK, [x, y, len(what)*size*1.5, size*3], size//2)

    font = pygame.font.SysFont('Calibri', size*3, True, False)
    text = font.render(what, True, GREEN)
    screen.blit(text, [x+size, y])


sun1 = Sun(-150, 250)

small_cloud1 = SmallCloud(200, 20, 10)
small_cloud2 = SmallCloud(20, 20, 7)
small_cloud3 = SmallCloud(770, 60, 9)
small_cloud4 = SmallCloud(-150, 40, 4)
small_cloud5 = SmallCloud(430, 80, 5)

big_cloud1 = BigCloud(220, 10)
big_cloud2 = BigCloud(-50, 80, 4)
big_cloud3 = BigCloud(450, 150, 8)
big_cloud4 = BigCloud(600, 30, 5)
big_cloud5 = BigCloud(800, 10, 3)


clouds = [big_cloud1, big_cloud2, big_cloud3, big_cloud4, big_cloud5,
          small_cloud1, small_cloud2, small_cloud3, small_cloud4, small_cloud5]

houses = []
for i in range(5):
    houses.append(House(20+i*190, 150, 16))

sky_color = [0, 120, 255]

star_list = []
for i in range(93):
    star_x = random.randrange(0, 850)
    star_y = random.randrange(0, 300)
    star_list.append(Star(star_x, star_y))

signia = 'Eduardville'

shift = False

sign_x = 5
sign_y = 348

sign_x_speed = 0
sign_y_speed = 0

while not done:
    # --- Main event loop
    for event in pygame.event.get():  # User did something
        if event.type == pygame.QUIT:  # If user clicked close
            done = True  # Flag that we are done so we exit this loop
        # User pressed down on a key
        if event.type == pygame.KEYDOWN:
            # Figure out if it was an arrow key. If so
            # adjust speed.
            if event.key == pygame.K_BACKSPACE:
                signia = signia[:-1]
            if event.key == pygame.K_RSHIFT or event.key == pygame.K_LSHIFT:
                shift = True

            if event.key == pygame.K_LEFT:
                sign_x_speed -= 3
            if event.key == pygame.K_RIGHT:
                sign_x_speed += 3
            if event.key == pygame.K_UP:
                sign_y_speed -= 3
            if event.key == pygame.K_DOWN:
                sign_y_speed += 3

            if event.key == pygame.K_q:
                if shift:
                    signia += 'Q'
                else:
                    signia += 'q'
            if event.key == pygame.K_w:
                if shift:
                    signia += 'W'
                else:
                    signia += 'w'
            if event.key == pygame.K_e:
                if shift:
                    signia += 'E'
                else:
                    signia += 'e'
            if event.key == pygame.K_r:
                if shift:
                    signia += 'R'
                else:
                    signia += 'r'
            if event.key == pygame.K_t:
                if shift:
                    signia += 'T'
                else:
                    signia += 't'
            if event.key == pygame.K_y:
                if shift:
                    signia += 'Y'
                else:
                    signia += 'y'
            if event.key == pygame.K_u:
                if shift:
                    signia += 'U'
                else:
                    signia += 'u'
            if event.key == pygame.K_i:
                if shift:
                    signia += 'I'
                else:
                    signia += 'i'
            if event.key == pygame.K_o:
                if shift:
                    signia += 'O'
                else:
                    signia += 'o'
            if event.key == pygame.K_p:
                if shift:
                    signia += 'P'
                else:
                    signia += 'p'
            if event.key == pygame.K_a:
                if shift:
                    signia += 'A'
                else:
                    signia += 'a'
            if event.key == pygame.K_s:
                if shift:
                    signia += 'S'
                else:
                    signia += 's'
            if event.key == pygame.K_d:
                if shift:
                    signia += 'D'
                else:
                    signia += 'd'
            if event.key == pygame.K_f:
                if shift:
                    signia += 'F'
                else:
                    signia += 'f'
            if event.key == pygame.K_g:
                if shift:
                    signia += 'G'
                else:
                    signia += 'g'
            if event.key == pygame.K_h:
                if shift:
                    signia += 'H'
                else:
                    signia += 'h'
            if event.key == pygame.K_j:
                if shift:
                    signia += 'J'
                else:
                    signia += 'j'
            if event.key == pygame.K_k:
                if shift:
                    signia += 'K'
                else:
                    signia += 'k'
            if event.key == pygame.K_l:
                if shift:
                    signia += 'L'
                else:
                    signia += 'l'
            if event.key == pygame.K_z:
                if shift:
                    signia += 'Z'
                else:
                    signia += 'z'
            if event.key == pygame.K_x:
                if shift:
                    signia += 'X'
                else:
                    signia += 'x'
            if event.key == pygame.K_c:
                if shift:
                    signia += 'C'
                else:
                    signia += 'c'
            if event.key == pygame.K_v:
                if shift:
                    signia += 'V'
                else:
                    signia += 'v'
            if event.key == pygame.K_b:
                if shift:
                    signia += 'B'
                else:
                    signia += 'b'
            if event.key == pygame.K_n:
                if shift:
                    signia += 'N'
                else:
                    signia += 'n'
            if event.key == pygame.K_m:
                if shift:
                    signia += 'M'
                else:
                    signia += 'm'
            if event.key == pygame.K_SPACE:
                    signia += ' '
        if event.type == pygame.KEYUP:
            if event.key == pygame.K_RSHIFT or event.key == pygame.K_LSHIFT:
                shift = False

    # --- Game logic should go here

    sign_y += sign_y_speed
    sign_x += sign_x_speed

    if sign_x < -200:
        sign_x = 900
    if sign_x > 900:
        sign_x = -150
    if sign_y > 750:
        sign_y = -150
    if sign_y < -200:
        sign_y = 700

    # --- Drawing code should go here
    sky_color = calculate_sky_color(sun1, sky_color)
    screen.fill(sky_color)

    for star in star_list:
        if sun1.mode == 1 and sun1.direction == -1 or sun1.mode == -1:
                star.show()
        else:
            star.hide()

        star.draw()

    sun1.draw_sun()
    sun1.move()

    draw_ground()

    for cloud in clouds:
        cloud.draw_cloud()
        cloud.move()

    for house in houses:
        house.draw_house()

    draw_sign(sign_x, sign_y, signia, 5)

    # --- Go ahead and update the screen with what we've drawn.
    pygame.display.flip()

    # --- Limit to 60 frames per second
    clock.tick(60)

# Close the window and quit.
# If you forget this line, the program will 'hang'
# on exit if running from IDLE.
pygame.quit()
