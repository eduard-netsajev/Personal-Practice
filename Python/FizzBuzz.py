__author__ = 'Netšajev'

import random

for i in range(100):
    num = random.randint(1, 1000)

    print("\n{:3} - ".format(num), end='')
    if num % 3 == 0:
        print("Fizz", end='')
    if num % 5 == 0:
        print("Buzz", end='')