#!/usr/bin/python
# -*- coding: iso-8859-1 -*-
import cgi, os

formdata = cgi.FieldStorage()

print "Content-type: text/text\n\n"

if formdata.has_key("name") and formdata.has_key("playerScore") and formdata.has_key("pigScore")\
        and formdata.has_key("time") and formdata.has_key('starttime'):
    name = formdata['name'].value
    playerScore = formdata['playerScore'].value
    pigScore = formdata['pigScore'].value
    time = formdata['time'].value
    starttime = formdata['starttime'].value

    dirname = os.path.dirname
    file = os.path.join(dirname(dirname(__file__)), os.path.join('prax3', 'pig.txt'))
    f = open(file, 'a')
    f.write(name + ',' + playerScore + ',' + pigScore + ',' + starttime + ',' + time + '\n')
    f.close()
    print 'Data saved'
else:
    print "Invalid data"