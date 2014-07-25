__author__ = 'Netšajev'

from bs4 import BeautifulSoup
import requests
import json
import os

r = requests.get('http://ois.ttu.ee/portal/page?_pageid=35,435155&_dad=portal&_schema=PORTAL&k=&a=1&b=1&c=-1&d=-1&e=-1&e_sem=141&i=1&q=neto&g=-1')

soup = BeautifulSoup(r.text, 'lxml')

soup = soup.find_all('table')[-1]

links = {}

for row in soup.find_all('span'):
    link = row.get('onclick')
    if len(link) > 50:
        group = row.getText()
        group = group.strip()
        link = link[22:-15]
        link = 'http' + link
        print(group, link)
        links[group] = link

string = soup.prettify()
f = open('table.html', 'w')
f.write(string)
f.close()

f = open('links.json', 'w')
json.dump(links, f, ensure_ascii=False, indent=4, sort_keys=True)
f.close()

print("List of all programms: ")
kavad = []
for link in soup.find_all('span'):
    kava = link.get('kava')
    if kava is not None and kava not in kavad:
        kavad.append(kava)

for i in range(len(kavad)):
    if i == len(kavad)-1:
        print(kavad[i])
    elif kavad[i][0] == kavad[i+1][0]:
        print(kavad[i], end=' ')
    else:
        print(kavad[i])


print("Starting scraping groups..")

counter = 0

for group in links:
    counter += 1
print("Total groups to scrape: {}".format(counter))

test = False
for group in links:
    if test:
        break
    os_stirng = "start GroupScraper.py {}".format(group)
    print(os_stirng)
    os.system(os_stirng)

input("Press enter to finish..")