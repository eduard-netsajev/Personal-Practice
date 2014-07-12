__author__ = 'Netšajev'

import pygame

pygame.init()

# Define some colors
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
DARKGREEN = (42, 115, 28)
YELLOW = (255, 255, 0)
DARKYELLW = (255, 180, 0)
SILVER = (180, 180, 180)
GRAY = (127, 127, 127)

pi = 3.141592653

scr_size = [800, 450]
screen = pygame.display.set_mode(scr_size)
pygame.display.set_caption("Suburban by Eduard")

# Loop until the user clicks the close button.
done = False

# Used to manage how fast the screen updates
clock = pygame.time.Clock()


# -------- Main Program Loop -----------

def draw_cloud_small(x, y, size=7):
    cloud_size = size
    pygame.draw.ellipse(screen, WHITE, [x, y, cloud_size*10, cloud_size*4])
    pygame.draw.ellipse(screen, WHITE, [x + cloud_size*3, y + cloud_size*2, cloud_size*10, cloud_size*4])


def draw_cloud_big(x, y, size=7):
    cloud_size = size
    pygame.draw.ellipse(screen, WHITE, [x + cloud_size*4, y, cloud_size*10, cloud_size*5])
    pygame.draw.ellipse(screen, WHITE, [x, y + cloud_size*3/2, cloud_size*18, cloud_size*6])
    pygame.draw.ellipse(screen, WHITE, [x + cloud_size*4, y + cloud_size*4, cloud_size*10, cloud_size*5])


def draw_house(x, y, size=24):

    house_size = size

    doorw = house_size * 2
    doorh = house_size * 6

    pnt_a = [x, y + house_size * 4]
    pnt_b = [x + house_size * 10, pnt_a[1]]
    pnt_k = [x + house_size * 1, y + house_size * 14]
    pnt_n = [pnt_k[0] + doorw, pnt_k[1]]

    #DRAW ROOF
    pygame.draw.polygon(screen, BLACK, [pnt_a, [x + house_size * 5, y - house_size / 2], pnt_b])
    #DRAW MAIN BUILDING
    pygame.draw.rect(screen, GRAY, [x, y + house_size * 4, house_size * 10, house_size * 10])
    #DRAW A DOORROOF
    pygame.draw.ellipse(screen, YELLOW, [pnt_k[0], pnt_k[1] - doorh + house_size/3, house_size*2, house_size*2])
    #DRAW A DOOR
    pygame.draw.polygon(screen, RED, [pnt_k, [pnt_k[0], pnt_k[1] + house_size * 3 / 2 - doorh],
                                      [pnt_n[0], pnt_n[1] + house_size * 3 / 2 - doorh], pnt_n])
    #DRAW A ROAD IN FRONT OF THE HOUS
    pygame.draw.polygon(screen, SILVER, [pnt_k, [pnt_k[0] + house_size, pnt_k[1] + house_size * 3 / 2],
                                         [pnt_n[0] + house_size, pnt_n[1] + house_size * 3 / 2], pnt_n])
    #DRAW UPPER WINDOWS
    pygame.draw.rect(screen, BLACK, [x + house_size/2, y + house_size * 9/2,
                                     house_size * 4, house_size * 3])
    pygame.draw.rect(screen, BLACK, [x + house_size*11/2, y + house_size * 9/2,
                                     house_size * 4, house_size * 3])
    #DRAW LOWER WINDOW
    pygame.draw.rect(screen, YELLOW, [x + house_size*11/2, y + house_size * 19/2,
                                     house_size * 4, house_size * 3])
    #DRAW A DOORKNOB
    pygame.draw.ellipse(screen, BLACK, [pnt_k[0]+house_size/8, pnt_k[1]-house_size*5/2, house_size/4, house_size/4])


def draw_background(skyratio=0.65, roadwidth=40):
    sky = skyratio
    road = roadwidth

    pygame.draw.rect(screen, BLUE, [0, 0, scr_size[0], scr_size[1] * sky])
    pygame.draw.rect(screen, DARKGREEN, [0, scr_size[1] * sky, scr_size[0], scr_size[1] * (1 - sky)])
    pygame.draw.rect(screen, SILVER, [0, scr_size[1] - road - 20, scr_size[0], road])


def draw_sun(x, y, size=6):
    sun_size = size

    pygame.draw.ellipse(screen, YELLOW, [x, y, sun_size * 10, sun_size * 10])
    pygame.draw.ellipse(screen, DARKYELLW, [x + 1.5 * sun_size, y + 1.5 * sun_size, sun_size * 7, sun_size * 7])


def draw_sign(x, y, what, size=5):
    pygame.draw.rect(screen, DARKGREEN, [x, y, size*18, size*3])
    pygame.draw.rect(screen, BLACK, [x+size*7, y+size*3, size*3, size*21])
    pygame.draw.rect(screen, BLACK, [x, y, size*18, size*3], size//2)

    font = pygame.font.SysFont('Calibri', size*3, True, False)
    text = font.render(what, True, GREEN)
    screen.blit(text, [x+size, y])


while not done:
    # --- Main event loop
    for event in pygame.event.get():  # User did something
        if event.type == pygame.QUIT:  # If user clicked close
            done = True  # Flag that we are done so we exit this loop

    # --- Game logic should go here

    # --- Drawing code should go here
    screen.fill(BLACK)

    draw_background()

    draw_sun(700, 30)
    draw_cloud_big(420, 15)
    draw_cloud_small(200, 20, 15)
    draw_cloud_small(20, 20, 7)
    draw_cloud_small(570, 60, 9)
    draw_cloud_big(150, 80, 4)

    for i in range(5):
        draw_house(20+i*190, 150, 16)

    draw_cloud_big(350, 150, 4)

    draw_sign(5, 318, 'Eduardville', 3)

    # --- Go ahead and update the screen with what we've drawn.
    pygame.display.flip()

    # --- Limit to 60 frames per second
    clock.tick(60)

# Close the window and quit.
# If you forget this line, the program will 'hang'
# on exit if running from IDLE.
pygame.quit()
