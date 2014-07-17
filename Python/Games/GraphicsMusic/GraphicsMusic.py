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

def main():
    """ Main function for the game. """
    pygame.init()

    # Set the width and height of the screen [width,height]
    size = [700, 500]
    screen = pygame.display.set_mode(size)

    pygame.display.set_caption("Graphics & Music")

    #Loop until the user clicks the close button.
    done = False

    # Used to manage how fast the screen updates
    clock = pygame.time.Clock()

    player_image = pygame.image.load("player.png").convert()
    player_image.set_colorkey(BLACK)

    background_image = pygame.image.load("background.jpg").convert()

    click_sound = pygame.mixer.Sound("laser.ogg")

    # -------- Main Program Loop -----------
    while not done:
        # ALL EVENT PROCESSING SHOULD GO BELOW THIS COMMENT
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                done = True
            elif event.type == pygame.MOUSEBUTTONDOWN:
                click_sound.play()
        # ALL EVENT PROCESSING SHOULD GO ABOVE THIS COMMENT

        # ALL GAME LOGIC SHOULD GO BELOW THIS COMMENT

        # Get the current mouse position. This returns the position
        # as a list of two numbers.
        player_position = pygame.mouse.get_pos()
        x = player_position[0]
        y = player_position[1]

        # ALL GAME LOGIC SHOULD GO ABOVE THIS COMMENT

        # ALL CODE TO DRAW SHOULD GO BELOW THIS COMMENT

        # First, clear the screen to white. Don't put other drawing commands
        # above this, or they will be erased with this command.
        screen.fill(WHITE)
        screen.blit(background_image, [0, 0])

        # Copy image to screen:
        screen.blit(player_image, [x, y])

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