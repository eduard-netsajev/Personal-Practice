
__author__ = 'Netšajev'

"""
This file is handling version checking of the Timetable data
Each time it creates a copy of ClassData.json and GroupsMap.json, then gets new files.
After that it compares GroupsMaps

If they are the same, it just deletes the old files and does nothing else, except
it updates the VersionData file, where he just moves the changed groups down the arrays,
i.e. if a group was in ['changedGroups'][3] it moves to ['changedGroups'][4]
if a group was in ['changedGroups'][8] it gets deleted
['changedGroups'][0] in this case is empty

If the are different, it moves the version up by 1 point, (+0.1)
the old ClassData.json get saved in /oldClassData
the old version files gets copied into /OldVersions
all changes get into the ['changedGroups'] arrays

version.json:

{
    'version' : version    #int
    'changedGroups' : [
    ['group1', 'group2', ...],
    [], [], [], [], [], [], [], []
    ]
"""

import os
import json

print("Started VersionChecker.py ..")


version_file_path = "version.json"
if not os.path.isfile(version_file_path):
    arrays = [[], [], [], [], [], [], [], [], []]
    version_json_data = {'version': 0,
                         'changedGroups': arrays}
    with open(version_file_path, 'w') as f:
        json.dump(version_json_data, f, ensure_ascii=False, indent=4, sort_keys=True)

else:
    json_file = open("version.json")
    version_json_data = json.load(json_file)
    json_file.close()

    for i in range(8, 0, -1):
        version_json_data['changedGroups'][i] = version_json_data['changedGroups'][i-1]
    version_json_data['changedGroups'][0] = []

    old_data_path = "OldData.json"
    old_map_path = "OldMap.json"
    new_data_path = "ClassData.json"
    new_map_path = "GroupsMap.json"

    open(old_data_path, "w").write(open(new_data_path, "r").read())
    open(old_map_path, "w").write(open(new_map_path, "r").read())

    os.remove(new_data_path)
    os.remove(new_map_path)

    os.system("LinkScraper.py")
    #input("\nPress enter to start compiling the main files..\n")
    os.system("MainFileCreator.py")
    os.system("GroupMapCreator.py")

    """
        Compare new file with the old one
    """
    old_map_data = json.load(open(old_map_path))
    new_map_data = json.load(open(new_map_path))

    for group in old_map_data:
        try:
            if sorted(old_map_data[group]) != sorted(new_map_data[group]):
                version_json_data['changedGroups'][0].append(group)
        except KeyError:
                version_json_data['changedGroups'][0].append(group)

    changed = len(version_json_data['changedGroups'][0])
    if changed > 0:
        version = version_json_data['version'] + 1
        version_json_data['version'] = version

        open("oldClassData/"+str(version)+" class_data.json", "w").write(open(old_data_path, "r").read())
        open("oldVersions/"+str(version)+" ver.json", "w").write(open(version_file_path, "r").read())

        print("The file was updated. Changes in {} groups".format(changed))
    else:
        print("No changes detected.")

    os.remove(old_data_path)
    os.remove(old_map_path)

    with open(version_file_path, 'w') as f:
        json.dump(version_json_data, f, ensure_ascii=False, indent=4, sort_keys=True)

    input("Press enter to exit..")