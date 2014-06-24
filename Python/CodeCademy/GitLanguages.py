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
        print("Languages used:")
        totalbytes = 0
        langItem = json.loads(l.text or l.content)

        sorted_list = [x for x in langItem.items()]
        sorted_list.sort(key=lambda x: x[1]) # sort by value

        for key, value in langItem.items():
            totalbytes += value
        for key, value in langItem.items():
            print("%s %s" % (key, str(value/totalbytes*100)+"%"))

        sorted_list.reverse()
        for key, value in sorted_list:
            percent = value/totalbytes*100
            print("%s - %.2f" % (key, percent))
