__author__ = 'Netšajev'

import RandomNames


def main():
    files_count = int(input("How much files to merge: "))
    path_output = input("Output file name: ")

    files = []
    for i in range(files_count):
        file_path = input("Input file nr. {} name:".format(i+1))
        files.append(file_path)

    used_files = []
    names = []
    for path in files:
        if path in used_files:
            continue
        else:
            names += RandomNames.read_names(path)
            used_files.append(path)
    names.sort()
    RandomNames.write_names(names, path_output)


if __name__ == '__main__':
    main()