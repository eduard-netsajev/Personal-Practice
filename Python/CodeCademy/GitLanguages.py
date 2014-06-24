__author__ = 'Netšajev'

import requests
import json

r = requests.get('https://api.github.com/repos/eduard-netsajev/Personal-Practice')
if r.ok:
    repoItem = json.loads(r.text or r.content)
    print("Eduard's personal repository created: ")
    print(repoItem['created_at'])
    l = requests.get(repoItem['languages_url'])
    if l.ok:
        print("\nLanguages used:")
        totalbytes = 0
        langItem = json.loads(l.text or l.content)
        sorted_list = [x for x in langItem.items()]
        sorted_list.sort(key=lambda x: x[1])  # sort by value
        sorted_list.reverse()
        for key, value in langItem.items():
            totalbytes += value
        for key, value in sorted_list:
            print("{0} - {1:.2%}".format(key, value/totalbytes))
else:
    print("He he he. Probably request limit exceeded.")