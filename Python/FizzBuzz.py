__author__ = 'Netšajev'

import random

for i in range(100):
    num = random.randint(1, 1000)
    print("\n{:3}".format(num), end='')

    divided_by3 = not num % 3
    divided_by5 = not num % 5

    if divided_by3 or divided_by5:
        print(" - ", end = '')
    if divided_by3:
        print("Fizz", end='')
    if divided_by5:
        print("Buzz", end='')
