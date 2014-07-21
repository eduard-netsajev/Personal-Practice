__author__ = 'Netšajev'

from bs4 import BeautifulSoup
import requests

r = requests.get('http://ois.ttu.ee/portal/page?_pageid=35,435155&_dad=portal&_schema=PORTAL&k=&a=1&b=1&c=-1&d=-1&e=-1&e_sem=141&i=1&q=neto&g=-1')

soup = BeautifulSoup(r.text)

f = open('output.html', 'w')
string = soup.prettify()
f.write(string)

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
#print(link.get('href'))
#print(soup.get_text())

for tag in soup.find_all(True):
    pass
#    print(tag.name)

print("Finished.")