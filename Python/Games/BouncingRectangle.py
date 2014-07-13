__author__ = 'Netšajev'

import pygame

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

pygame.init()

# Set the width and height of the screen [width, height]
size = (700, 500)
screen = pygame.display.set_mode(size)

pygame.display.set_caption("My Game")

# Loop until the user clicks the close button.
done = False

# Used to manage how fast the screen updates
clock = pygame.time.Clock()

rect_x = 50
rect_y = 50

rect_change_x = 5
rect_change_y = 5

# -------- Main Program Loop -----------
while not done:
    # --- Main event loop
    for event in pygame.event.get(): # User did something
        if event.type == pygame.QUIT: # If user clicked close
            done = True  # Flag that we are done so we exit this loop

    # --- Game logic should go here

    # --- Drawing code should go here

    # First, clear the screen to white. Don't put other drawing commands
    # above this, or they will be erased with this command.
    screen.fill(BLACK)

    pygame.draw.rect(screen, BLACK, [rect_x, rect_y, 50, 50])
    pygame.draw.ellipse(screen, RED, [rect_x+10, rect_y+10, 30, 30])

    rect_x += rect_change_x
    rect_y += rect_change_y

    if 0 > rect_y or rect_y > 450:
        rect_change_y *= -1
    if 0 > rect_x or rect_x > 650:
        rect_change_x *= -1

    # --- Go ahead and update the screen with what we've drawn.
    pygame.display.flip()

    # --- Limit to 60 frames per second
    clock.tick(60)

# Close the window and quit.
# If you forget this line, the program will 'hang'
# on exit if running from IDLE.
pygame.quit()
