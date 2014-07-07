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


pi = 3.141592653

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
    time += 0.1
    # --- Drawing code should go here

    # First, clear the screen to white. Don't put other drawing commands
    # above this, or they will be erased with this command.
    screen.fill(BLACK)
    #Drawing

    pygame.draw.rect(screen, WHITE, [20, 20, 250, 100], 3)
    pygame.draw.ellipse(screen, RED, [20, 20, 250, 100], 3)
    pygame.draw.arc(screen, GREEN, [20, 20, 250, 100],  pi/3,     4*pi/3, 5)

    pygame.draw.arc(screen, GREEN, [100,200,250,100],  pi/2,     pi, 2)
    pygame.draw.arc(screen, WHITE, [100,200,250,100],     0,   pi/2, 2)
    pygame.draw.arc(screen, RED,   [100,200,250,100],3*pi/2,   2*pi, 2)
    pygame.draw.arc(screen, BLUE,  [100,200,250,100],    pi, 3*pi/2, 2)

    # This draws a triangle using the polygon command
    pygame.draw.polygon(screen, WHITE, [[300,300], [200,400], [400,400]], 5)

    pygame.draw.polygon(screen, (160, 100, 190), [[310, 10], [330, 120], [390, 180], [440, 150], [350, 50]], 5)

    # Select the font to use, size, bold, italics
    font = pygame.font.SysFont('Calibri', 25, True, False)

    # Render the text. "True" means anti-aliased text.
    # Green is the color. The variable GREEN was defined above
    # Note: This line creates an image of the letters,
    # but does not put it on the screen yet.
    output_string = "Time: {:.6}".format(time)
    text = font.render(output_string, True, GREEN)

    # Put the image of the text on the screen at 250x250
    screen.blit(text, [250, 250])


    # --- Go ahead and update the screen with what we've drawn.
    pygame.display.flip()

    # --- Limit to 60 frames per second
    clock.tick(60)

# Close the window and quit.
# If you forget this line, the program will 'hang'
# on exit if running from IDLE.
pygame.quit()