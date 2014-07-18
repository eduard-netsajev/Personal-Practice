"""
Show the proper way to organize a game using the a game class.
 
Sample Python/Pygame Programs
Simpson College Computer Science
http://programarcadegames.com/
http://simpson.edu/computer-science/
 
Explanation video: http://youtu.be/O4Y5KrNgP_c
"""
 
import pygame
import random

#--- Global constants ---
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
 
SCREEN_WIDTH = 700
SCREEN_HEIGHT = 500

# --- Classes ---
 
class Block(pygame.sprite.Sprite):
    """ This class represents a simple block the player collects. """

    size = 1

    def __init__(self):
        """ Constructor, create the image of the block. """
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.Surface([random.randrange(2, 10), random.randrange(2, 10)])
        self.image.fill(WHITE)
        self.rect = self.image.get_rect()
 
    def reset_pos(self):
        """ Called when the block is 'collected' or falls off
            the screen. """
        self.rect.y = random.randrange(-300, -20)
        self.rect.x = random.randrange(SCREEN_WIDTH)
 
    def update(self):
        """ Automatically called when we need to move the block. """

        self.rect.y += 1
 
        if self.rect.y > SCREEN_HEIGHT + self.rect.height:
            self.reset_pos()


class BigBlock(Block):

    direction = 0
    size = 2

    def __init__(self):
        """ Constructor, create the image of the block. """
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.Surface([random.randrange(15, 40), random.randrange(15, 40)])
        self.image.fill(BLUE)
        self.rect = self.image.get_rect()

    def reset_pos(self):
        """ Called when the block is 'collected' or falls off
            the screen. """

        self.direction = random.randint(-2, 2)
        self.rect.y = random.randrange(-300, -20)
        self.rect.x = random.randrange(-SCREEN_WIDTH, 2*SCREEN_WIDTH)

    def update(self):
        """ Automatically called when we need to move the block. """
        self.rect.y += 2
        self.rect.x += self.direction
        if self.rect.y > SCREEN_HEIGHT + self.rect.height:
            self.reset_pos()


class Player(pygame.sprite.Sprite):
    """ This class represents the player. """
    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.Surface([10, 10])
        self.image.fill(RED)
        self.rect = self.image.get_rect()
 
    def update(self):
        """ Update the player location. """
        pos = pygame.mouse.get_pos()
        self.rect.x = pos[0]
        self.rect.y = pos[1]


class Game(object):
    """ This class represents an instance of the game. If we need to
        reset the game we'd just need to create a new instance of this
        class. """
 
    # --- Class attributes.
    # In this case, all the data we need
    # to run our game.
 
    # Sprite lists
    block_list = None
    all_sprites_list = None
    player = None
 
    # Other data
    game_over = False
    lifes = 0
    score = 0
    level = 0

    #Music files
    crash_sound = None
    levelup_sound = None
    background_sound = None

    time = 0
    frames = 0

    # --- Class methods
    # Set up the game
    def __init__(self):

        self.lifes = 3
        self.level = 1
        self.game_over = False
        self.frames = 0


        self.crash_sound = pygame.mixer.Sound("explosion.ogg")
        self.levelup_sound = pygame.mixer.Sound("levelup.ogg")
        self.background_sound = pygame.mixer.Sound("background.ogg")

        self.background_sound.play()

        # Create sprite lists
        self.block_list = pygame.sprite.Group()
        self.all_sprites_list = pygame.sprite.Group()
 
        # Create the block sprites
        for i in range(30):
            block = Block()
 
            block.rect.x = random.randrange(SCREEN_WIDTH)
            block.rect.y = random.randrange(-300, SCREEN_HEIGHT)
 
            self.block_list.add(block)
            self.all_sprites_list.add(block)
 
        # Create the player
        self.player = Player()
        self.all_sprites_list.add(self.player)
 
    def process_events(self):
        """ Process all of the events. Return a "True" if we need
            to close the window. """
 
        for event in pygame.event.get():
            if event.type == pygame.QUIT:
                return True
            if event.type == pygame.MOUSEBUTTONDOWN:
                if self.game_over:
                    self.background_sound.stop()
                    self.__init__()
 
        return False
 
    def run_logic(self):

        self.frames += 1
        self.time = self.frames//6/10
        """
        This method is run each time through the frame. It
        updates positions and checks for collisions.
        """
        if not self.game_over:

            #Check for levelling up
            self.check_level()

            # Move all the sprites
            for i in range(self.level):
                self.all_sprites_list.update()
 
            # See if the player block has collided with anything.
            blocks_hit_list = pygame.sprite.spritecollide(self.player, self.block_list, False)

            for block in blocks_hit_list:

                self.lifes -= block.size

                self.crash_sound.play()
                block.reset_pos()
                self.block_list.add(block)

            if self.lifes < 0:
                self.score = self.time
                self.game_over = True

    def display_frame(self, screen):
        """ Display everything to the screen for the game. """
        screen.fill(BLACK)
 
        if self.game_over:

            screen.fill(BLACK)
            font = pygame.font.SysFont("serif", 25)
            text = font.render("Game Over, click to restart", True, WHITE)
            center_x = (SCREEN_WIDTH // 2) - (text.get_width() // 2)
            center_y = (SCREEN_HEIGHT // 2) - (text.get_height() // 2)
            screen.blit(text, [center_x, center_y])

            string = "Your score: {}".format(self.score)
            text = font.render(string, True, WHITE)
            center_x = (SCREEN_WIDTH // 2) - (text.get_width() // 2)
            center_y = (SCREEN_HEIGHT // 2) - (text.get_height() // 2)
            screen.blit(text, [center_x, center_y-50])

            pygame.display.flip()
 
        if not self.game_over:
            self.all_sprites_list.draw(screen)

            font = pygame.font.SysFont("serif", 25)
            string = "Score: {}".format(self.time)
            text = font.render(string, True, GREEN)
            screen.blit(text, [15, 15])

            self.lifes *= 10
            self.lifes //= 1
            self.lifes /= 10

            life = self.lifes / 1
            life = int(self.lifes)

            string = "Armor: {}".format(life)
            text = font.render(string, True, GREEN)
            screen.blit(text, [15, 45])

            pygame.display.flip()

    def check_level(self):

        new_level = int(1+self.time//10)
        if new_level > self.level:
            self.lifes += 1
            self.levelup_sound.play()
            self.level = new_level
            if self.level % 2 == 0:
                    block = BigBlock()
                    block.rect.x = random.randrange(SCREEN_WIDTH)
                    block.rect.y = random.randrange(-300, SCREEN_HEIGHT)
                    self.block_list.add(block)
                    self.all_sprites_list.add(block)


def main():
    """ Main program function. """
    # Initialize Pygame and set up the window
    pygame.init()

    size = [SCREEN_WIDTH, SCREEN_HEIGHT]
    screen = pygame.display.set_mode(size)
 
    pygame.display.set_caption("My Game")
    pygame.mouse.set_visible(False)
 
    # Create our objects and set the data
    done = False
    clock = pygame.time.Clock()
    # Create an instance of the Game class
    game = Game()

    # Main game loop
    while not done:

        # Process events (keystrokes, mouse clicks, etc)
        done = game.process_events()
 
        # Update object positions, check for collisions
        game.run_logic()
 
        # Draw the current frame
        game.display_frame(screen)

        # Pause for the next frame
        clock.tick(60)
 
    # Close window and exit
    pygame.quit()
 
# Call the main function, start up the game
if __name__ == "__main__":
    main()