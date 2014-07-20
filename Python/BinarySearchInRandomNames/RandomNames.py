__author__ = 'Netšajev'

import os
import requests
import json


def read_names(file_path):
    """Function that returns a list of names from the file"""

    if os.path.isfile(file_path) and os.access(file_path, os.R_OK):
        print("File {} exists and is readable".format(file_path))
        file = open(file_path, 'r')
        name_list = []
        for line in file:
            line = line.strip()
            name_list.append(line)
        file.close()
        return name_list
    else:
        return []


def get_names(amount=1):
    """Function returns random name from randomuser.me API"""

    l = requests.get('http://api.randomuser.me/?results={}'.format(amount))
    if l.ok:
        random_people = json.loads(l.text or l.content)
        names = []
        for random_person in random_people['results']:
            random_first_name = random_person['user']['name']['first']
            random_last_name = random_person['user']['name']['last']
            random_name = '{} {}'.format(random_first_name, random_last_name)
            names.append(random_name)
        return names
    else:
        print("API doesn't respond")
        return []


def write_names(names_list, file_path):
    """Function writes every name on separate line to given file (clearing it first)"""

    with open(file_path, 'w') as file:
        for name in names_list:
            file.write(name)
            file.write('\n')
        print("\nFile {} was created with all the information".format(file_path))


def main():

    count = int(input("How many new names do you want to get: "))
    path = input("Where do you want to save them: ")

    count_done = 0

    new_names = []
    old_names = read_names(path)

    while count_done < count:
        new_names += get_names(count-count_done)
        count_done += 20
        print("Done: {:.2%}".format(len(new_names)/count))

    all_names = old_names + new_names
    all_names.sort()
    write_names(all_names, path)


if __name__ == '__main__':
    main()