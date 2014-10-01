__author__ = 'Netšajev'
import random


def roll():
    d1 = random.randint(1, 6)
    d2 = random.randint(1, 6)

    if d1 != d2:
        if d1 == 1 or d2 == 1:
            return 0
        else:
            return d1 + d2
    else:
        if d1 == 1:
            return 25
        else:
            return 4 * d1


def play_game(bound1, bound2):
    scores = [0, 0]
    current_score = 0

    turn = True  # True if it's first player turn, False otherwise
    turns = 0
    while scores[0] < 100 and scores[1] < 100:
        rolled = roll()

        if rolled == 0:
            turn = not turn
            current_score = 0
            turns = 0
            continue
        else:
            turns += 1
            current_score += rolled

        if turn and current_score > bound1:
            scores[0] += current_score
            turn = not turn
            turns = 0
            current_score = 0
        if not turn and current_score > bound2:
            scores[1] += current_score
            turn = not turn
            current_score = 0
            turns = 0

    if scores[0] >= 100:
        return True  # First player won
    else:
        return False  # First player lost to Second player


def get_winrate(bound1=100, bound2=100, count=10, turns1=100, turns2=100):
    won = 0.0
    bound1 = int(bound1)
    bound2 = int(bound2)
    turns1 = int(turns1)
    turns2 = int(turns2)
    for i in range(count):
        if play_game(bound1, bound2):
            won += 1
    return won/count


def main():
    bound1 = 2
    bound2 = 100
    count = 10000
    print("Playing {} times.\nFirst player rolls until {}.\nSecond player rolls until {}.".format(count, bound1, bound2))
    print("Winrate of the first player: " + str(get_winrate(0, 0, bound1, bound2, count)))

if __name__ == "__main__":
    main()