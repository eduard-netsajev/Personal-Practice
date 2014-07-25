__author__ = 'Netšajev'

'''
This file takes all the JSON data from /groups folder and glues it together
'''

import os
import json


def is_one_class(class1, class2):
    for key in class1:
        if key != 'groups':
            if class1[key] != class2[key]:
                return False
    else:
        return True


file_list = os.listdir("groups")
ClassData = []
classes_count = 0
found_flag = False

for filename in file_list:
    if len(filename) == 11 and filename[6:] == '.json':
        pass
        path = "groups/{}".format(filename)
        json_data = open(path)
        tunnid = json.load(json_data)
        json_data.close()
        for tund in tunnid:
            found_flag = False
            classes_count += 1
            for Class in ClassData:
                if is_one_class(tund, Class):
                    for group in tund['groups']:
                        Class['groups'].append(group)
                        found_flag = True
            if found_flag is False:
                ClassData.append(tund)

path = "ClassData.json"
with open(path, 'w') as f:
    json.dump(ClassData, f, ensure_ascii=False, indent=4, sort_keys=True)

print("Finished. {} classes compiled from {} original classes".format(len(ClassData), classes_count))
input()