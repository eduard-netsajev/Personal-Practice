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
        for key, value in langItem.items():
            totalbytes += value
        for key, value in langItem.items():
            print("%s %d" % (key, value/totalbytes*100))
