#!/usr/bin/python
# -*- coding: iso-8859-1 -*-
import cgi, os

formdata = cgi.FieldStorage()

print "Content-type: text/text\n\n"

if formdata.has_key("name"):
    name = formdata['name'].value
    if formdata.has_key("playerScore"):
        playerScore = formdata['playerScore'].value
        if formdata.has_key("pigScore"):
            pigScore = formdata['pigScore'].value
            if formdata.has_key("time"):
                time = formdata['time'].value

                dirname = os.path.dirname
                file = os.path.join(dirname(dirname(__file__)), os.path.join('prax3', 'pig.txt'))
                f = open(file, 'a')
                f.write(name + ',' + playerScore + ',' + pigScore + ',' + time + '\n')
                f.close()
                print 'Data saved'
else:
    print "Invalid data"