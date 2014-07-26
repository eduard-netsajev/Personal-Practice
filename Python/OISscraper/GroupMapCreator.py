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

hash_map = {}

for group in groups:
    hash_list = []
    for Class in Classes:
        if group in Classes[Class]['groups']:
            hash_list.append(Class)
    hash_map[group] = hash_list


path = "GroupsMap.json"
with open(path, 'w') as f:
    json.dump(hash_map, f, ensure_ascii=False, indent=4, sort_keys=True)

print(len(hash_map), "groups mapped")
input("Press enter to finish")