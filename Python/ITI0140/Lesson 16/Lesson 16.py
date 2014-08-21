__author__ = 'Netšajev'

import Tund16gen as data_gen


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
    upper_bound = len(lst)-1
    found = False
    while lower_bound < upper_bound and found is False:
        middle_pos = int((lower_bound+upper_bound) / 2)
        if lst[middle_pos] < num:
            lower_bound = middle_pos+1
        elif lst[middle_pos] > num:
            upper_bound = middle_pos
        else:
            return True
    return False


def get_input(size):
    my_input = data_gen.gimme_my_input(size, "131654")
    return my_input


def find_with_binary(inpt):
    size = len(inpt[0])
    print("\nUsing binary search\n")
    for i in range(size):
        needed_num = inpt[1].__next__()
        if binary_search(inpt[0], needed_num):
            print("Number {} was found".format(needed_num))
        else:
            print("Number {} was not found in the list!".format(needed_num))


def find_with_linear(inpt):
    size = len(inpt[0])
    print("\nUsing linear search\n")
    for i in range(size):
        needed_num = inpt[1].__next__()
        if linear_search(inpt[0], needed_num):
            print("Number {} was found".format(needed_num))
        else:
            print("Number {} was not found in the list!".format(needed_num))


def main():
    my_input = get_input(10)

    print(my_input[0])
    find_with_linear(my_input)
    find_with_binary(my_input)


if __name__ == "__main__":
    main()
