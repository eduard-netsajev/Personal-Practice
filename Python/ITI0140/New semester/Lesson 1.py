__author__ = 'Netšajev'

# First who completed the task in whole class. Got maximum points.


def task(current_bottles):
    first = True
    empty_bottles = 0
    drinked_total = 0

    while current_bottles > 0 or empty_bottles > 2:
        new_bottles = current_bottles + empty_bottles // 3

        if first:
            print("{}: {}".format(current_bottles, new_bottles), end=' ')
        else:
            print("+ {}".format(new_bottles), end=' ')

        empty_bottles += current_bottles
        drinked_total += current_bottles

        current_bottles = empty_bottles // 3
        empty_bottles -= current_bottles * 3
        first = False

    print("= {}".format(drinked_total))
    return drinked_total


def main():
    total = 0
    for i in range(1, 101):
        total += task(i)

    print("\n Total drinked: {}".format(total))


if __name__ == "__main__":
    main()