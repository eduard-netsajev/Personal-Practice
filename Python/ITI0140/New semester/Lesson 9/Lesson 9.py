__author__ = 'Netšajev'

import random
import timeit as t

from math import *

trials = (2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096)


def pi_1(n):
    count = 0
    for _ in range(n):
        if (2*random.random() - 1) ** 2 + (2*random.random() - 1) ** 2 <= 1:
            count += 1
    return 4.0 * count / n


def pi_2(n):
    randints = [random.randrange(1000) for _ in range(n)]

    total = 0
    count = 0
    for i in randints:
        for j in randints:
            if j > i:
                if gcd_1(i, j) == 1:
                    count += 1
                total += 1
    return sqrt(6.0 / count * total)


def gcd_1(n, m):
    x = n % m
    return m if x == 0 else gcd_1(m, x)


def main():
    t.timeit()

    print("{:^8s} {:^8s} {:^8s} {:^8s}".format("n", "meetod", "delta", "aeg"))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("64", "first", str(abs(pi-pi_1(64))),
                                               str(t.timeit(takes1_64, number=10))))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("64", "second", str(abs(pi-pi_2(64))),
                                               str(t.timeit(takes2_64, number=10))))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("256", "first", str(abs(pi-pi_1(256))),
                                               str(t.timeit(takes1_256, number=10))))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("256", "second", str(abs(pi-pi_2(256))),
                                               str(t.timeit(takes2_256, number=10))))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("1024", "first", str(abs(pi-pi_1(1024))),
                                               str(t.timeit(takes1_1024, number=10))))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("1024", "second", str(abs(pi-pi_2(1024))),
                                               str(t.timeit(takes2_1024, number=10))))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("2048", "first", str(abs(pi-pi_1(2048))),
                                               str(t.timeit(takes1_2048, number=10))))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("2048", "second", str(abs(pi-pi_2(2048))),
                                               str(t.timeit(takes2_2048, number=10))))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("4096", "first", str(abs(pi-pi_1(4096))),
                                               str(t.timeit(takes1_4096, number=1) * 10)))

    print("{:^8s} {:^8s} {:^8.5s} {:^8s}".format("4096", "second", str(abs(pi-pi_2(4096))),
                                               str(t.timeit(takes2_4096, number=1) * 10)))






def takes1_64():
    pi_1(64)

def takes2_64():
    pi_2(64)

def takes1_256():
    pi_1(256)

def takes2_256():
    pi_2(256)

def takes1_1024():
    pi_1(1024)

def takes2_1024():
    pi_2(1024)

def takes1_2048():
    pi_1(2048)

def takes2_2048():
    pi_2(2048)

def takes1_4096():
    pi_1(4096)

def takes2_4096():
    pi_2(4096)


if __name__ == "__main__":
    main()
