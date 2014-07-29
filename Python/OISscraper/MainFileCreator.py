__author__ = 'Netšajev'

'''
This file takes all the JSON data from /groups folder and glues it together
replacing N exactly the same classes with 1 group in each with 1 class but with N groups in it
'''

import os
import json
import hashlib

print("Started MainFileCreator.py ..")

def is_one_class(class1, class2):
    """
    Compares two Class objects
    """
    for key in class1:
        if key != 'groups':
            if class1[key] != class2[key]:
                return False
    else:
        return True


def get_const_hash(tund):
    """
    Returns constant hash
    """
    tund_srng = tund['ainekood'] + tund['room'] + tund['type'] + tund['end_time'] + tund['lasts'] + str(tund['day']) \
        + str(tund['teacher']) + tund['name'] + str(tund['weeks']) + tund['start_time'] + tund['comments']
    string = str(tund_srng).encode('utf-8')
    hash_id = int(hashlib.md5(string).hexdigest(), 16)
    return hash_id

#GET NAMES OF ALL THE GROUPS
#IF NEEDED CAN BE REPLACED BY GETTING GROUPS FROM links.json
file_list = os.listdir("groups")
ClassData = {}
classes_count = 0
found_flag = False

final_classes_list = []

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
            for Class_key in ClassData:
                Class = ClassData[Class_key]
                if is_one_class(tund, Class):
                    for group in tund['groups']:
                        Class['groups'].append(group)
                        found_flag = True
            if found_flag is False:
                if get_const_hash(tund) in ClassData:
                    print(str(tund))
                    print(str(ClassData[get_const_hash(tund)]))
                    input("Two classes have the same HASH!")
                else:
                    ClassData[get_const_hash(tund)] = tund
path = "ClassData.json"
with open(path, 'w') as f:
    json.dump(ClassData, f, ensure_ascii=False, indent=4, sort_keys=True)

print("Finished. {} classes compiled from {} original classes\n".format(len(ClassData), classes_count))