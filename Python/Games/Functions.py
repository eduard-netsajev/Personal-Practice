__author__ = 'Netšajev'

import random


def min(a, b, c):
    if a < b:
        if c < a:
            return c
        else:
            return a
    else:
        if b < c:
            return b
        else:
            return c


def box(a, b):
    for i in range(a):
        for j in range(b):
            print("*", end='')
        print()


def find(numbers, key):
    for i in range(len(numbers)):
        if numbers[i] == key:
            print("Found {} at position {}".format(key, i))


def create_list(size):
    list = []
    for i in range(size):
        list.append(random.randint(1, 6))
    return list


def count_list(numbers, number):
    count = 0
    for num in numbers:
        if num == number:
            count += 1
    return count


def average_list(numbers):
    total = 0
    for num in numbers:
        total += num
    return total/len(numbers)


def test():
    print(min(4, 7, 5))
    print(min(4, 5, 5))
    print(min(4, 4, 4))
    print(min(-2, -6, -100))
    print(min("Z", "B", "A"))

    box(7, 5)  # Print a box 7 high, 5 across
    print()   # Blank line
    box(3, 2)  # Print a box 3 high, 2 across
    print()   # Blank line
    box(3, 10) # Print a box 3 high, 10 across

    lst=[36, 36, 79, 96, 36, 91, 77, 33, 19, 3, 34, 70, 12, 12, 54, 98, 86, 11, 17, 17]

    find(lst, 12)
    find(lst, 91)
    find(lst, 80)

    my_list = create_list(5)
    print(my_list)

    count = count_list([1,2,3,3,3,4,2,1],3)
    print(count)

    avg = average_list([1,2,3])
    print(avg)


def main():
    my_list = create_list(10000)

    for i in range(1, 7):
        print("Number {} was found {} times".format(i, count_list(my_list, i)))

    print("The average of the list is {}".format(average_list(my_list)))

if __name__ == "__main__":
    main()
