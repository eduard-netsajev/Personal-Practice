__author__ = 'Netšajev'

from bs4 import BeautifulSoup
import requests
import json

my_url = 'http://ois.ttu.ee/portal/page?_pageid=35,435155&_dad=portal&_schema=PORTAL&k=&i=1&a=1&q=1&b=1&c=-1&d=-1&e=-1&e_sem=141&g=33772'

g = requests.get(my_url)
gsoup = BeautifulSoup(g.text, 'lxml')

gsoup = gsoup.find('table')
[x.decompose() for x in gsoup.findAll('script')]
[x.decompose() for x in gsoup.findAll('span', style="font-style:italic;")]
gsoup = gsoup.find('tr', align="LEFT")

gsoup = gsoup.find('table', style="width:400px")
gsoup.find('span').decompose()
group = gsoup.find('td').text

gsoup.find('tr').decompose()
gsoup.find('tr').decompose()

for day in gsoup.find_all('td', class_='pais border_top'):
    print(day.text)


day_soup = BeautifulSoup()
for tund in gsoup.find_all(class_='ttmain_tund'):
    day_soup.append(tund)

"""
day_soup = BeautifulSoup()
record = False
for tag in gsoup.find_all(True):
    if tag.text == 'esmaspäev':
        record = True
    if record:
        day_soup.append(tag)
    if tag.text == 'teisipäev':
        record = False
"""


### WRITE DOWN THE JSON FILE
group_data = day_soup.prettify()



path = "{}.html".format(group)
with open(path, 'w') as f:
        #json.dump(group_data, f, ensure_ascii=False)
        f.write(group_data)