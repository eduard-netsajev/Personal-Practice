__author__ = 'Netšajev'

import pygame
import random

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

def main():
    """ Main function for the game. """
    pygame.init()

    # Set the width and height of the screen [width,height]
    size = [255, 255]
    screen = pygame.display.set_mode(size)

    pygame.display.set_caption("MineSweeper")

    #Loop until the user clicks the close button.
    done = False

    # Used to manage how fast the screen updates
    clock = pygame.time.Clock()

    # -------- Main Program Loop -----------

    click_sound = pygame.mixer.Sound("laser.ogg")

    width = 20
    height = 20
    margin = 5

    grid = []
    for row in range(10):
        grid.append([])
        for column in range(10):
            grid[row].append(0)

    mines_count = random.randint(15, 20)

    while not done:
        # ALL EVENT PROCESSING SHOULD GO BELOW THIS COMMENT
        for event in pygame.event.get():  # User did something
            if event.type == pygame.QUIT:  # If user clicked close
                done = True  # Flag that we are done so we exit this loop
            elif event.type == pygame.MOUSEBUTTONDOWN:
                click_sound.play()
                mouse_position = pygame.mouse.get_pos()
                column = mouse_position[0] // (width+margin)
                row = mouse_position[1] // (height+margin)
                print("Row:", row, "Column: ", column)
                grid[row][column] = 1
        # ALL EVENT PROCESSING SHOULD GO ABOVE THIS COMMENT

        # ALL GAME LOGIC SHOULD GO BELOW THIS COMMENT

        # ALL GAME LOGIC SHOULD GO ABOVE THIS COMMENT

        # ALL CODE TO DRAW SHOULD GO BELOW THIS COMMENT

        # First, clear the screen to white. Don't put other drawing commands
        # above this, or they will be erased with this command.
        screen.fill(BLACK)

        # Draw the grid
        for row in range(10):
            for column in range(10):
                color = WHITE
                if grid[row][column] == 1:
                    color = GREEN
                pygame.draw.rect(screen,
                                 color,
                                 [(margin+width)*column+margin,
                                  (margin+height)*row+margin,
                                  width,
                                  height])




        # ALL CODE TO DRAW SHOULD GO ABOVE THIS COMMENT

        # Go ahead and update the screen with what we've drawn.
        pygame.display.flip()

        # Limit to 20 frames per second
        clock.tick(20)

    # Close the window and quit.
    # If you forget this line, the program will 'hang'
    # on exit if running from IDLE.
    pygame.quit()

if __name__ == "__main__":
    main()