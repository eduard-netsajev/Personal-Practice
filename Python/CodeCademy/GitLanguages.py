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
        print("Used languages are:")
        langItem = json.loads(l.text or l.content)
        print(langItem)
        pyth = int(langItem['Python'])
        php = int(langItem['PHP'])
        css = int(langItem['CSS'])
        java = int(langItem['Java'])
        csharp = int(langItem['C#'])
        codeTotal = pyth + php + css + java + csharp
        python_part = pyth/codeTotal
        print('%.2f percent is python' % (python_part*100))
        java_part = java/codeTotal
        print('%.2f percent is Java' % (java_part*100))
        csharp_part = csharp/codeTotal
        print('%.2f percent is C#' % (csharp_part*100))