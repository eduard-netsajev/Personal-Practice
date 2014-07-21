import pygame
import random
import json

#--- Global constants ---
BLACK = (0, 0, 0)
WHITE = (255, 255, 255)
GREEN = (0, 255, 0)
RED = (255, 0, 0)
BLUE = (0, 0, 255)
 
SCREEN_WIDTH = 700
SCREEN_HEIGHT = 500

RECORDS_PATH = 'records.json'

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
    move = True

    def __init__(self):
        """ Constructor, create the image of the block. """
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.Surface([random.randrange(15, 40), random.randrange(15, 40)])
        self.image.fill(BLUE)
        self.rect = self.image.get_rect()

    def reset_pos(self):
        """ Called when the block is 'collected' or falls off
            the screen. """

        self.direction = random.randint(-1, 1)
        self.rect.y = random.randrange(-300, -20)
        self.rect.x = random.randrange(-SCREEN_WIDTH, 2*SCREEN_WIDTH)

    def update(self):
        """ Automatically called when we need to move the block. """
        if self.move == 4:
            self.rect.y += 2
            self.rect.x += self.direction
            self.move = 1
        else:
            self.move += 1
        if self.rect.y > SCREEN_HEIGHT + self.rect.height:
            self.reset_pos()


class Player(pygame.sprite.Sprite):
    """ This class represents the player. """
    armor = 3

    def __init__(self):
        pygame.sprite.Sprite.__init__(self)
        self.image = pygame.Surface([self.armor*3, self.armor*3])
        self.image.fill(RED)
        self.rect = self.image.get_rect()

    def update(self):
        """ Update the player location. """
        pos = pygame.mouse.get_pos()
        self.rect.x = pos[0]
        self.rect.y = pos[1]

    def change_armor(self, amount):
        self.armor += amount
        if self.armor > 0:
            self.image = pygame.Surface([self.armor*3+2, self.armor*3+2])
        else:
            self.image = pygame.Surface([2, 2])
        self.image.fill(RED)
        self.rect = self.image.get_rect()


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
    score = 0
    level = 0
    biggest_armor = 0

    #Music files
    crash_sound = None
    levelup_sound = None
    background_sound = None

    time = 0
    frames = 0
    shift = False

    # --- Class methods
    # Set up the game
    def __init__(self):

        self.level = 1
        self.game_over = False
        self.frames = 0
        self.player_name = ''
        self.record = None
        self.ask_name = False

        self.crash_sound = pygame.mixer.Sound("explosion.ogg")
        self.levelup_sound = pygame.mixer.Sound("levelup.ogg")
        self.background_sound = pygame.mixer.Sound("background.ogg")
        self.background_image = pygame.image.load("asteroids.png").convert()

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
            # User pressed down on a key
            if event.type == pygame.KEYDOWN and self.ask_name:

                if event.key == pygame.K_BACKSPACE:
                    self.player_name = self.player_name[:-1]
                if event.key == pygame.K_RSHIFT or event.key == pygame.K_LSHIFT:
                    self.shift = True

                if event.key == pygame.K_q:
                    if self.shift:
                        self.player_name += 'Q'
                    else:
                        self.player_name += 'q'
                if event.key == pygame.K_w:
                    if self.shift:
                        self.player_name += 'W'
                    else:
                        self.player_name += 'w'
                if event.key == pygame.K_e:
                    if self.shift:
                        self.player_name += 'E'
                    else:
                        self.player_name += 'e'
                if event.key == pygame.K_r:
                    if self.shift:
                        self.player_name += 'R'
                    else:
                        self.player_name += 'r'
                if event.key == pygame.K_t:
                    if self.shift:
                        self.player_name += 'T'
                    else:
                        self.player_name += 't'
                if event.key == pygame.K_y:
                    if self.shift:
                        self.player_name += 'Y'
                    else:
                        self.player_name += 'y'
                if event.key == pygame.K_u:
                    if self.shift:
                        self.player_name += 'U'
                    else:
                        self.player_name += 'u'
                if event.key == pygame.K_i:
                    if self.shift:
                        self.player_name += 'I'
                    else:
                        self.player_name += 'i'
                if event.key == pygame.K_o:
                    if self.shift:
                        self.player_name += 'O'
                    else:
                        self.player_name += 'o'
                if event.key == pygame.K_p:
                    if self.shift:
                        self.player_name += 'P'
                    else:
                        self.player_name += 'p'
                if event.key == pygame.K_a:
                    if self.shift:
                        self.player_name += 'A'
                    else:
                        self.player_name += 'a'
                if event.key == pygame.K_s:
                    if self.shift:
                        self.player_name += 'S'
                    else:
                        self.player_name += 's'
                if event.key == pygame.K_d:
                    if self.shift:
                        self.player_name += 'D'
                    else:
                        self.player_name += 'd'
                if event.key == pygame.K_f:
                    if self.shift:
                        self.player_name += 'F'
                    else:
                        self.player_name += 'f'
                if event.key == pygame.K_g:
                    if self.shift:
                        self.player_name += 'G'
                    else:
                        self.player_name += 'g'
                if event.key == pygame.K_h:
                    if self.shift:
                        self.player_name += 'H'
                    else:
                        self.player_name += 'h'
                if event.key == pygame.K_j:
                    if self.shift:
                        self.player_name += 'J'
                    else:
                        self.player_name += 'j'
                if event.key == pygame.K_k:
                    if self.shift:
                        self.player_name += 'K'
                    else:
                        self.player_name += 'k'
                if event.key == pygame.K_l:
                    if self.shift:
                        self.player_name += 'L'
                    else:
                        self.player_name += 'l'
                if event.key == pygame.K_z:
                    if self.shift:
                        self.player_name += 'Z'
                    else:
                        self.player_name += 'z'
                if event.key == pygame.K_x:
                    if self.shift:
                        self.player_name += 'X'
                    else:
                        self.player_name += 'x'
                if event.key == pygame.K_c:
                    if self.shift:
                        self.player_name += 'C'
                    else:
                        self.player_name += 'c'
                if event.key == pygame.K_v:
                    if self.shift:
                        self.player_name += 'V'
                    else:
                        self.player_name += 'v'
                if event.key == pygame.K_b:
                    if self.shift:
                        self.player_name += 'B'
                    else:
                        self.player_name += 'b'
                if event.key == pygame.K_n:
                    if self.shift:
                        self.player_name += 'N'
                    else:
                        self.player_name += 'n'
                if event.key == pygame.K_m:
                    if self.shift:
                        self.player_name += 'M'
                    else:
                        self.player_name += 'm'
                if event.key == pygame.K_SPACE:
                        self.player_name += ' '
                if event.key == pygame.K_RETURN:

                    score_list = [self.player_name, self.score]
                    armor_list = [self.player_name, self.biggest_armor]

                    try:
                        json_data = open(RECORDS_PATH)
                        self.record = json.load(json_data)
                        json_data.close()

                        record_score = self.record['score'][1]
                        record_armor = self.record['armor'][1]
                    except FileNotFoundError:
                        record_score = 0
                        record_armor = 0
                    finally:
                        if record_score >= self.score:
                            score_list = [self.record['score'][0], self.record['score'][1]]
                        if record_armor >= self.biggest_armor:
                            armor_list = [self.record['armor'][0], self.record['armor'][1]]

                    record_text = {'score': score_list,
                                   'armor': armor_list}
                    with open(RECORDS_PATH, 'w') as f:
                        json.dump(record_text, f, ensure_ascii=False)
                    self.ask_name = False

            if event.type == pygame.KEYUP:
                if event.key == pygame.K_RSHIFT or event.key == pygame.K_LSHIFT:
                    self.shift = False

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

                if self.time > 3:
                    self.player.change_armor(-block.size)

                self.crash_sound.play()
                block.reset_pos()
                self.block_list.add(block)

            if self.player.armor < 0:

                self.score = self.time
                self.game_over = True
                self.ask_name = True

    def display_frame(self, screen):
        """ Display everything to the screen for the game. """
        screen.fill(BLACK)

        screen.blit(self.background_image, [0, 0])

        if self.game_over:
            font = pygame.font.SysFont("serif", 25)

            if self.ask_name:
                screen.fill(BLACK)
                string = "Enter your name: {}".format(self.player_name)
                text = font.render(string, True, WHITE)
                center_x = (SCREEN_WIDTH // 2) - (text.get_width() // 2)
                center_y = (SCREEN_HEIGHT // 2) - (text.get_height() // 2)
                screen.blit(text, [center_x, center_y])

            else:
                screen.fill(BLACK)
                text = font.render("Game Over, click to restart", True, WHITE)
                center_x = (SCREEN_WIDTH // 2) - (text.get_width() // 2)
                center_y = (SCREEN_HEIGHT // 2) - (text.get_height() // 2)
                screen.blit(text, [center_x, center_y-50])

                string = "{}, your score: {} and your biggest armor: {}".format(self.player_name,
                                                                                self.score, self.biggest_armor)
                text = font.render(string, True, WHITE)
                center_x = (SCREEN_WIDTH // 2) - (text.get_width() // 2)
                center_y = (SCREEN_HEIGHT // 2) - (text.get_height() // 2)
                screen.blit(text, [center_x, center_y])

                if self.record is not None:
                    string = "Best score: {} by {}".format(self.record['score'][1], self.record['score'][0])
                else:
                    string = "Your name will be here next time"

                text = font.render(string, True, WHITE)
                center_x = (SCREEN_WIDTH // 2) - (text.get_width() // 2)
                center_y = (SCREEN_HEIGHT // 2) - (text.get_height() // 2)
                screen.blit(text, [center_x, center_y+50])

                if self.record is not None:
                    string = "Biggest armor: {} by {}".format(self.record['armor'][1], self.record['armor'][0])
                    text = font.render(string, True, WHITE)
                    center_x = (SCREEN_WIDTH // 2) - (text.get_width() // 2)
                    center_y = (SCREEN_HEIGHT // 2) - (text.get_height() // 2)
                    screen.blit(text, [center_x, center_y+100])

            pygame.display.flip()
 
        if not self.game_over:
            self.all_sprites_list.draw(screen)

            font = pygame.font.SysFont("serif", 25)
            string = "Score: {}".format(self.time)
            text = font.render(string, True, GREEN)
            screen.blit(text, [15, 15])

            string = "Armor: {}".format(self.player.armor)
            text = font.render(string, True, GREEN)
            screen.blit(text, [15, 45])

            pygame.display.flip()

    def check_level(self):

        if self.player.armor > self.biggest_armor:
            self.biggest_armor = self.player.armor

        new_level = int(1+self.time//10)
        if new_level > self.level:
            self.player.change_armor(1)
            self.levelup_sound.play()
            self.level = new_level
            if self.level % 2 == 0:
                    block = BigBlock()
                    block.rect.x = random.randrange(SCREEN_WIDTH)
                    block.rect.y = random.randrange(-300, -20)
                    self.block_list.add(block)
                    self.all_sprites_list.add(block)


def main():
    """ Main program function. """
    # Initialize Pygame and set up the window
    pygame.init()

    size = [SCREEN_WIDTH, SCREEN_HEIGHT]
    screen = pygame.display.set_mode(size)
 
    pygame.display.set_caption("Asteroid Field by Eduard Netsajev")
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
