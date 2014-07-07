__author__ = 'Netšajev'

import random
import math

import pygame

pygame.init()

# Define some colors
BLACK    = (   0,   0,   0)
WHITE    = ( 255, 255, 255)
GREEN    = (   0, 255,   0)
RED      = ( 255,   0,   0)
BLUE     = (   0,   0, 255)


PI = 3.141592653

size = [700, 500]
screen = pygame.display.set_mode(size)
pygame.display.set_caption("Eduard's Cool Game")

# Loop until the user clicks the close button.
done = False

# Used to manage how fast the screen updates
clock = pygame.time.Clock()


def draw_random_line(scr):
    """draws a random line on given screen scr"""
    multipl = 1  # Multiplier, integer > 0, bigger multiplier - smaller line
    randthick = random.randint(1, 20)//multipl
    randcolor = (random.randint(1, 255), random.randint(1, 255), random.randint(1, 255))
    randstart = [random.randint(100, 600)//multipl, random.randint(100, 400)//multipl]
    randend = [random.randint(100, 300)//multipl, random.randint(100, 400)//multipl]

    pygame.draw.line(scr, randcolor, randstart, randend, randthick)
    return True
    # -------- Main Program Loop -----------

y_offset = 0
offset = 10

while not done:
    # --- Main event loop
    for event in pygame.event.get(): # User did something
        if event.type == pygame.QUIT: # If user clicked close
            done = True # Flag that we are done so we exit this loop

    # --- Game logic should go here

    # --- Drawing code should go here

    # First, clear the screen to white. Don't put other drawing commands
    # above this, or they will be erased with this command.
    screen.fill(BLACK)

    #Drawing
    #pygame.draw.rect(screen, RED, [550, 400, 10, 5], 0)
    #pygame.draw.line(screen, GREEN, [0, 0], [100, 100], 5)


    #RANDOM LINES
    draw_random_line(screen)
    draw_random_line(screen)
    draw_random_line(screen)

    # Draw on the screen several lines from (0, 10) to (100, 110)
    # 5 pixels wide using a while loop
    pygame.draw.line(screen, RED, [300, 200+y_offset], [500, 210-y_offset], 5)
    y_offset += offset
    if y_offset > 1500:
       offset = -offset
    if y_offset < -200:
        offset = -offset

    #Sine and cosine lines
    for i in range(3000):
        radians_x = i / 40
        radians_y = i / 6

        x = int((900+y_offset) * math.sin(radians_x)) + 300
        y = int(200 * math.cos(radians_y)) + 250

        pygame.draw.line(screen, GREEN, [x-y_offset,y], [x-y_offset+5,y], 5)

    # --- Go ahead and update the screen with what we've drawn.
    pygame.display.flip()

    # --- Limit to 60 frames per second
    clock.tick(60)

# Close the window and quit.
# If you forget this line, the program will 'hang'
# on exit if running from IDLE.
pygame.quit()