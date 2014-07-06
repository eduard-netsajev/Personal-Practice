__author__ = 'Netšajev'

import requests
import json
from datetime import datetime
import os
import os.path

now = datetime.now()
PATH = "git_lang_data/{:0>2}-{:0>2}-{} langs.txt".format(now.day, now.month, now.year)

update = True

if os.path.isfile(PATH) and os.access(PATH, os.R_OK):
    print("File {} exists and is readable".format(PATH))
    if input("Enter 'y' to update it with the newest data: ") != 'y':
        update = False
if update:
    l = requests.get('https://api.github.com/repos/eduard-netsajev/Personal-Practice/languages')
    if l.ok:
        print("\nLanguages used in Eduard's repository:\n")
        totalbytes = 0
        langItem = json.loads(l.text or l.content)
        sorted_list = [x for x in langItem.items()]
        sorted_list.sort(key=lambda x: x[1])  # sort by value
        sorted_list.reverse()
        for key, value in langItem.items():
            totalbytes += value
        for key, value in sorted_list:
            print("{0} - {1:.2%}".format(key, value/totalbytes))

        with open(PATH, 'w') as f:
            json.dump(langItem, f, ensure_ascii=False)
        print("\nFile {} was created with all the information".format(PATH))
    else:
        print("\nHe he he. Probably request limit exceeded. Or some problems with internet connection.")

input()
