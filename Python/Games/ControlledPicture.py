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


def draw_stick_figure(screen, x, y):
    # Head
    pygame.draw.ellipse(screen, BLACK, [1+x,y,10,10], 0)

    # Legs
    pygame.draw.line(screen, BLACK ,[5+x,17+y], [10+x,27+y], 2)
    pygame.draw.line(screen, BLACK, [5+x,17+y], [x,27+y], 2)

    # Body
    pygame.draw.line(screen, RED, [5+x,17+y], [5+x,7+y], 2)

    # Arms
    pygame.draw.line(screen, RED, [5+x,7+y], [9+x,17+y], 2)
    pygame.draw.line(screen, RED, [5+x,7+y], [1+x,17+y], 2)


def main():
    """ Main function for the game. """
    pygame.init()

    # Set the width and height of the screen [width,height]
    size = [700, 500]
    screen = pygame.display.set_mode(size)

    pygame.display.set_caption("My Game")

    #Loop until the user clicks the close button.
    done = False

    # Used to manage how fast the screen updates
    clock = pygame.time.Clock()

    # Hide the mouse cursor
    pygame.mouse.set_visible(False)

    # Speed in pixels per frame
    x_speed = 0
    y_speed = 0

    # Current position
    x_coord = 10
    y_coord = 10

    # -------- Main Program Loop -----------
    while not done:
        # ALL EVENT PROCESSING SHOULD GO BELOW THIS COMMENT
        for event in pygame.event.get(): # User did something
            if event.type == pygame.QUIT: # If user clicked close
                done = True # Flag that we are done so we exit this loop

            # User pressed down on a key
            if event.type == pygame.KEYDOWN:
                # Figure out if it was an arrow key. If so
                # adjust speed.
                if event.key == pygame.K_LEFT:
                    x_speed -= 3
                if event.key == pygame.K_RIGHT:
                    x_speed += 3
                if event.key == pygame.K_UP:
                    y_speed -= 3
                if event.key == pygame.K_DOWN:
                    y_speed += 3

            # User let up on a key
            if event.type == pygame.KEYUP:
                if event.key == pygame.K_LEFT:
                    x_speed += 3
                if event.key == pygame.K_RIGHT:
                    x_speed -= 3
                if event.key == pygame.K_UP:
                    y_speed += 3
                if event.key == pygame.K_DOWN:
                    y_speed -= 3
        # ALL EVENT PROCESSING SHOULD GO ABOVE THIS COMMENT


        # ALL GAME LOGIC SHOULD GO BELOW THIS COMMENT

        '''
        pos = pygame.mouse.get_pos()
        x = pos[0]-15
        y = pos[1]-30
        '''
        x_coord += x_speed
        y_coord += y_speed

        # ALL GAME LOGIC SHOULD GO ABOVE THIS COMMENT



        # ALL CODE TO DRAW SHOULD GO BELOW THIS COMMENT

        # First, clear the screen to white. Don't put other drawing commands
        # above this, or they will be erased with this command.
        screen.fill(WHITE)

        # ALL CODE TO DRAW SHOULD GO ABOVE THIS COMMENT

        #draw_stick_figure(screen, x, y)

        # Draw the stick figure
        draw_stick_figure(screen, x_coord, y_coord)

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