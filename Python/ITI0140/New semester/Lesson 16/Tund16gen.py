"""
Module to generate lists and random numbers.
"""

import random

def __gimme_a_generator(max_number, seed):
    """
    Generator to use for generating random numbers.
    """
    r = random.Random(seed)
    while True:
        yield r.randrange(1, max_number)

def __gimme_a_list(generator, size):
    """
    Returns a generated list to use for searching.
    """
    return [next(generator) for _ in range(size)]

def gimme_my_input(size, seed):
    """
    Returns a tuple consisting of the generated list and the generator to use.
    """
    gen = __gimme_a_generator(2 * size, seed)
    lst = __gimme_a_list(gen, size)
    return (lst, gen)


def __main():
    inp = gimme_my_input(10, "123456")
    print(inp[0])
    print(next(inp[1]))
    print(next(inp[1]))

if __name__ == "__main__":
    __main()