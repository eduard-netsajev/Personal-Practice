#!/usr/bin/python
# -*- coding: iso-8859-1 -*-
import cgi,cgitb

cgitb.enable() # see peaks siis kusagil üpris algul olema

print "Content-type: text/html\n\n" # mõistlik panna kohe programmi algusse
print "Tere!"
formdata = cgi.FieldStorage()

if formdata.has_key("nimi"):
    nimi=formdata['nimi'].value
else:
    nimi = "Guest"
if formdata.has_key("email"):
    email=formdata['email'].value
if formdata.has_key("raha"):
    raha=formdata['raha'].value
else:
    raha = "not a single"

if formdata.has_key("nimi") and formdata.has_key("raha"):
    nimi = formdata['nimi'].value
    raha = formdata['raha'].value
    f = open("test.txt", 'a')
    f.write('\n' + nimi + ',' + raha)
    f.close()

print "<br>"
print nimi
print " omab "
print raha
print " euro"