__author__ = 'Netšajev'

"""
This files creates a map of Group -> Hashes so that it is known for which classes
to look up when you pick any single group, without comparing it to every single class

Hash Map:
    'GROUP1' : [ClassHash1, ClassHash2, ClassHash3...],
    'GROUP2' : [ClassHash7, ClassHash1, ClassHash5...],
    'GROUP3' : [ClassHash2, ClassHash5, ClassHash9...],
    ...
    WHERE ClassHash - String object
"""

import json
import os

print("Started GroupMapCreator.py ..")

file_list = os.listdir("groups")

#GET NAMES OF ALL THE GROUPS
#IF NEEDED CAN BE REPLACED BY GETTING GROUPS FROM links.json
groups = []
for filename in file_list:
    group = filename.replace('.json', '')
    if len(group) == 6:
        groups.append(group)

#Get all the classes from ClassData.json
json_data = open("ClassData.json")
Classes = json.load(json_data)
json_data.close()


def sort_by_time(inputhash):
    return 60*(24*Classes[inputhash]['day'] + int(Classes[inputhash]['start_time'][0, 2]))\
            + int(Classes[inputhash]['start_time'][3, 5])



hash_map = {}
for group in groups:
    hash_list = []
    for Class in Classes:
        if group in Classes[Class]['groups']:
            hash_list.append(Class)
    hash_list.sort(key=sort_by_time)
    hash_map[group] = hash_list


path = "GroupsMap.json"
with open(path, 'w') as f:
    json.dump(hash_map, f, ensure_ascii=False, indent=4, sort_keys=True)

print("Groups Map was created")
print(len(hash_map), "groups mapped\nThe groups' files are being deleted")

os.chdir('groups')
groups_file_list = [f for f in os.listdir(".") if f.endswith(".json")]
for f in groups_file_list:
    os.remove(f)


