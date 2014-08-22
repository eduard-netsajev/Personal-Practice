__author__ = 'Netšajev'

import Tund16gen as data_gen
import time


def get_input(size):
    my_input = data_gen.gimme_my_input(size, "131654")
    return my_input


def linear_search(lst, num):
    """
    Return True if found, False otherwise.
    """
    for n in lst:
        if n == num:
            return True
    return False


def binary_search(lst, num):
    """
    Return True if found, False otherwise.
    """
    lst.sort()
    lower_bound = 0
    upper_bound = len(lst) - 1
    while lower_bound < upper_bound:
        middle_pos = int((lower_bound + upper_bound) / 2)
        if lst[middle_pos] < num:
            lower_bound = middle_pos + 1
        elif lst[middle_pos] > num:
            upper_bound = middle_pos
        else:
            return True
    return False


def find_with_binary(inpt):
    size = len(inpt[0])

    start_time = time.time()

    for i in range(size):
        binary_search(inpt[0], inpt[1].__next__())

    end_time = time.time()
    return end_time - start_time


def find_with_linear(inpt):
    size = len(inpt[0])

    start_time = time.time()

    for i in range(size):
        linear_search(inpt[0], inpt[1].__next__())

    end_time = time.time()
    return end_time - start_time


def main(n):
    test_sizes = [2 ** x * 1000 for x in range(n)]

    linear_results = []
    binary_results = []

    for size in test_sizes:
        print("Testing with list size {}:".format(size))

        my_input = get_input(size)
        linear_results.append(find_with_linear(my_input))
        print("\tLinear search: {0:.3f}s".format(linear_results[-1]))

        my_input = get_input(size)
        binary_results.append(find_with_binary(my_input))
        print("\tBinary search: {0:.3f}s".format(binary_results[-1]))

    import matplotlib.pyplot as plt
    import numpy as np

    def abs_results():
        fig = plt.subplot(211)

        ind = np.arange(n)  # the x locations for the groups
        width = 0.35

        plt.barh(ind, linear_results, width, color='green')

        plt.barh(ind + width * 0.5, binary_results, width, color='red')

        fig.set_xlabel('Time (seconds)')
        fig.set_yticklabels(test_sizes, fontdict=None, minor=False)
        fig.set_yticks([x for x in range(n)], minor=False)

    def relative_results():
        ax = plt.subplot(212)

        for i in range(n):
            x = 100 / linear_results[i]
            linear_results[i] *= x
            binary_results[i] *= x

        ind = np.arange(n)  # the x locations for the groups
        width = 0.35

        linear_bar = plt.barh(ind, linear_results, width, color='green')

        binary_bar = plt.barh(ind + width * 0.5, binary_results, width, color='red')

        ax.set_ylabel('List size')
        ax.set_xlabel('Time (percents)')
        ax.set_yticklabels(test_sizes, fontdict=None, minor=False)
        ax.set_yticks([x for x in range(n)], minor=False)

        ax.legend((linear_bar, binary_bar), ('Linear', 'Binary'), loc='upper right',
                  bbox_to_anchor=(1.12, 1.29), fancybox=True, shadow=True)

    abs_results()
    relative_results()
    plt.show()


if __name__ == "__main__":
    main(4)