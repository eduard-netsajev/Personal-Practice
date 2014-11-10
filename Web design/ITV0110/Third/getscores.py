#!/usr/bin/python
# -*- coding: iso-8859-1 -*-
import cgi, os

formdata = cgi.FieldStorage()

print "Content-type: text/text\n"

if formdata.has_key("p1") and formdata.has_key("p2"):
    p1 = formdata['p1'].value
    p2 = formdata['p2'].value
else:
    p1 = "Any"
    p2 = "Any"

dirname = os.path.dirname
file = os.path.join(dirname(dirname(__file__)), os.path.join('prax3', 'pig.txt'))
f = open(file, 'r')
data = f.readlines()
f.close()
filtereddata = []

if p1 == "Any" and p2 == "Any":
    for line in data:
        parts = line.split(',')
        filtereddata.append(parts)
elif p1 != "Any" and p2 == "Any":
    for line in data:
        parts = line.split(',')
        if parts[0] == p1 or parts[2] == p1:
            filtereddata.append(parts)
elif p1 != "Any" and p2 != "Any":
    for line in data:
        parts = line.split(',')
        if parts[0] == p1 and parts[2] == p2 or parts[0] == p2 and parts[2] == p1:
            filtereddata.append(parts)
elif p1 == "Any" and p2 != "Any":
    for line in data:
        parts = line.split(',')
        if parts[0] == p2 or parts[2] == p2:
            filtereddata.append(parts)

for datastr in filtereddata:
    datastr[1] = int(datastr[1])
    datastr[3] = int(datastr[3])
    datastr[4] = int(datastr[4])
    datastr[5] = int(datastr[5])

if formdata.has_key("sortfield") and formdata.has_key("sortorder"):
    sortfield = int(formdata['sortfield'].value)
    sortorder = int(formdata['sortorder'].value)
    revert = True if sortorder == -1 else False
    filtereddata.sort(key=lambda datapiece: datapiece[sortfield], reverse=revert)

for datastr in filtereddata:
    datastr[1] = str(datastr[1])
    datastr[3] = str(datastr[3])
    datastr[4] = str(datastr[4])
    datastr[5] = str(datastr[5])

for datastr in filtereddata:
    print ','.join(datastr)
