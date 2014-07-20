__author__ = 'Netšajev'


def binary_search(name_list, desired_element):

    middle_pos = 0
    lower_bound = 0
    upper_bound = len(name_list)-1
    found = False
    while lower_bound < upper_bound and found is False:
        middle_pos = int((lower_bound+upper_bound) / 2)
        print(name_list[middle_pos])
        if name_list[middle_pos] < desired_element:
            lower_bound = middle_pos+1
        elif name_list[middle_pos] > desired_element:
            upper_bound = middle_pos
        else:
            found = True

    if found:
        print("Name found on line ", middle_pos+1)
    else:
        print("Name was not found in the file")


def main():
    path = input("Where are we looking for a person: ")
    file = open(path)
    names_list = []

    for line in file:
        line = line.strip()
        names_list.append(line)

    file.close()
    names_list.sort()

    needed_element = input("Who are you looking for: ")
    needed_element = needed_element.lower()

    binary_search(names_list, needed_element)

if __name__ == '__main__':
    main()