__author__ = 'Netšajev'

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


    # -------- Main Program Loop -----------
time = 0

while not done:
    # --- Main event loop
    for event in pygame.event.get(): # User did something
        if event.type == pygame.QUIT: # If user clicked close
            done = True # Flag that we are done so we exit this loop

    # --- Game logic should go here
    time += 0.01
    # --- Drawing code should go here

    # First, clear the screen to white. Don't put other drawing commands
    # above this, or they will be erased with this command.
    screen.fill(BLACK)

    #Sine and cosine lines
    for i in range(3000):
        radians_x = i / 40 + time
        radians_y = i / 10

        x = int((500) * math.sin(radians_x)) + 300
        y = int(200 * math.cos(radians_y)) + 250

        pygame.draw.line(screen, GREEN, [x,y], [x+3,y], 3)

    # --- Go ahead and update the screen with what we've drawn.
    pygame.display.flip()

    # --- Limit to 60 frames per second
    clock.tick(60)

# Close the window and quit.
# If you forget this line, the program will 'hang'
# on exit if running from IDLE.
pygame.quit()