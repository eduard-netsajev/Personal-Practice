#!/usr/bin/python
# -*- coding: iso-8859-1 -*-
import cgi, os

formdata = cgi.FieldStorage()

print "Content-type: text/text\n\n"

if formdata.has_key("p1name") and formdata.has_key("p1score") and formdata.has_key("p2name")\
        and formdata.has_key("p2score") and formdata.has_key('time') and formdata.has_key('starttime'):
    p1name = formdata['p1name'].value
    p2name = formdata['p2name'].value
    p1score = formdata['p1score'].value
    p2score = formdata['p2score'].value
    time = formdata['time'].value
    starttime = formdata['starttime'].value

    dirname = os.path.dirname
    file = os.path.join(dirname(dirname(__file__)), os.path.join('prax3', 'pig.txt'))
    f = open(file, 'a')
    f.write(p1name + ',' + p1score + ',' + p2name + ',' + p2score + ',' + starttime + ',' + time + '\n')
    f.close()
    print 'Data saved'
else:
    print "Invalid data"