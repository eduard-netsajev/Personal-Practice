__author__ = 'Netšajev'

import RandomNames


def main():
    path = input("File do you want to clear up ->")
    original_names = RandomNames.read_names(path)
    print("Starting clearing the list..\n")

    unique_names = [original_names[0]]
    size = len(original_names)
    count = 0

    for i in range(1, size):
        if original_names[i] != unique_names[-1]:
            unique_names.append(original_names[i])
        else:
            count += 1
        print("Done: {:.2%}".format((i+1)/size))

    RandomNames.write_names(unique_names, path)
    print("Deleted {} duplicates in the file {}".format(count, path))

if __name__ == '__main__':
    main()
    input()