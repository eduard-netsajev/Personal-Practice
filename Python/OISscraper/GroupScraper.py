__author__ = 'Netšajev'

"""
This file takes 1 group program page, scrapes all the classes on it and stores them into json file
"""

from bs4 import BeautifulSoup
import requests
import json
import sys
import random

#print("This is the name of the script: ", sys.argv[0])
#print("Number of arguments: ", len(sys.argv))
#print("The arguments are: ", str(sys.argv))

if len(sys.argv) > 1:
    json_data = open('links.json')
    links = json.load(json_data)
    json_data.close()
    url = links[sys.argv[1]]
    test = False
else:
    input("No group was passed as argument. One of test urls will be used. Continue?")

    # TEST URLS:
    my_url4 = 'http://ois.ttu.ee/portal/page?_pageid=35,435155&_dad=portal&_schema=PORTAL&k=&i=1&a=1&q=1&b=1&c=-1&d=-1&e=-1&e_sem=141&g=33772'
    my_url3 = 'http://ois.ttu.ee/portal/page?_pageid=35,435155&_dad=portal&_schema=PORTAL&k=&i=1&a=1&q=1&b=1&c=-1&d=-1&e=-1&e_sem=141&g=33941'
    my_url2 = 'http://ois.ttu.ee/portal/page?_pageid=35,435155&_dad=portal&_schema=PORTAL&k=&i=1&a=1&q=1&b=1&c=-1&d=-1&e=-1&e_sem=141&g=32597'
    my_url1 = 'http://ois.ttu.ee/portal/page?_pageid=35,435155&_dad=portal&_schema=PORTAL&k=&i=1&a=1&q=1&b=1&c=-1&d=-1&e=-1&e_sem=141&g=32547'

    test_urls = [my_url1, my_url2, my_url3, my_url4]
    url = test_urls[random.randint(0, 3)]
    test = True

#print(url)

g = requests.get(url)
gsoup = BeautifulSoup(g.text, 'lxml')
#print(g.text)
gsoup = gsoup.find('table')
[x.decompose() for x in gsoup.findAll('script')]
[x.decompose() for x in gsoup.findAll('span', style="font-style:italic;")]
gsoup = gsoup.find('tr', align="LEFT")

gsoup = gsoup.find('table', style="width:400px")
gsoup.find('span').decompose()
group = gsoup.find('td').text

if not test:
    if group != sys.argv[1]:
        input("Group in the link is not the passed as argument group. Continue?")

gsoup.find('tr').decompose()
gsoup.find('tr').decompose()

all_text = gsoup.prettify()
days = all_text.split("<td class=\"pais border_top\" colspan=\"2\">")

# ##CREATE JSON FILE
json_data = []
for i in range(6):
    day_soup = BeautifulSoup(days[i + 1])
    for tund_data in day_soup.find_all(class_='ttmain_tund'):
        #print(tund_data.prettify())
        ###GET THE INFO OF EVERY SINGLE CLASS
        tund = {'day': i}

        temp_string = tund_data.find('td', style="border:0").string.strip()
        temp_list = temp_string.split('-')
        tund['start_time'] = temp_list[0].strip()
        tund['end_time'] = temp_list[1].strip()
        tund['type'] = temp_list[2].strip()

        tund['lasts'] = tund_data.b.string.strip()

        tund_data.tr.decompose()  # Delete first row after all data is extracted from it

        temp_soup = BeautifulSoup(tund_data.prettify())

        for span in temp_soup.find('span'):
            temp_list = span.string.split('-')
            if len(temp_list) > 1:
                tund['ainekood'] = temp_list[0].strip()
                tund['name'] = temp_list[1].strip()

        temp_soup = BeautifulSoup(tund_data.find('td', style="border:0;text-align:left").prettify())
        temp_string = temp_soup.find_all('span')
        tund['teacher'] = []
        if temp_string is not None and len(temp_string) > 0:
            for span in temp_soup.find_all('span'):
                teacher = span.string.strip()
                teacher = teacher.replace(u'\xa0', u' ').strip()
                if len(teacher) > 5:
                    tund['teacher'].append(teacher)

        temp_soup = BeautifulSoup(tund_data.find('td', style="text-align:right;border-left:0;").prettify())
        temp_string = temp_soup.find('b')
        if temp_string is None:
            tund['weeks'] = 0
        else:
            temp_string = temp_soup.b.string.strip()
            if temp_string == 'paaritu':
                tund['weeks'] = 1
            elif temp_string == 'paaris':
                tund['weeks'] = 2
            else:
                tund['weeks'] = 0
                print('Week not recognized!\n'*40)
                input()

        tund['groups'] = [group]

        temp_string = tund_data.find('td', style="border:0;text-align:right")
        if temp_string is None:
            tund['room'] = ''
            input('No room found at group {} tund {}'.format(group, tund['name']))
        else:
            temp_string = temp_string.string
            temp_string = temp_string.replace(u'\xa0', u' ')
            temp_string = temp_string.replace('\n', '')
            temp_string = temp_string.strip()
            tund['room'] = temp_string
        try:
            temp_soup = tund_data.find_all('b')[-1]
            if temp_soup.string.strip() == 'kommentaar:':
                temp_soup = temp_soup.next_sibling
                tund['comments'] = temp_soup.string.strip()
            else:
                tund['comments'] = ''
        except IndexError:
            tund['comments'] = ''
        entries = 0
        for key in tund:
            #print("{} - {}".format(key, tund[key]))
            entries += 1
        if entries != 12:
            input("Amount of entries is not {}. Continue? ".format(entries))
        json_data.append(tund)


### WRITE DOWN THE JSON FILE
path = "groups/{}.json".format(group)
with open(path, 'w') as f:
    json.dump(json_data, f, ensure_ascii=False, indent=4, sort_keys=True)

"""
JSON REQUIREMENTS:

GROUP_FILE:

[tund1, tund2, tund3, tund4]

TUND:

{
day : DAY,                     #int: 0-5   MON=0
weeks: WEEKS                   #int: 1-3
start_time: START_TIME         #String
end_time: END_TIME	       #String
name : TUND_NAME,              #String
room : ROOM,                   #String
groups : GROUPS,               #List (String)
comments : COMMENTS            #String

teacher : TEACHER,             #List (String)
type : TYPE,                   #String
ainekood : AINEKOOD,           #String
lasts : LASTS_WEEKS,           #String
}

"""