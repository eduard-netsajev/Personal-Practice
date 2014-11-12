#!/usr/bin/python
# -*- coding: iso-8859-1 -*-
import cgi, os, random, time

formdata = cgi.FieldStorage()

print "Content-type: text/text\n"

dirname = os.path.dirname
file = os.path.join(dirname(dirname(__file__)), os.path.join('prax3', 'pvp.txt'))
pvpsdir = os.path.join(dirname(dirname(__file__)), os.path.join('prax3', 'pvps'))


def unreg(delname):
    delname = delname.strip()
    fil = open(file, "r")
    strngs = fil.readlines()
    fil.close()

    fil = open(file, "w")
    for st in strngs:
        if st.strip() != delname:
            fil.write(st.strip() + "\n")
    fil.close()


def get_enemy(myname):
    lst = os.listdir(pvpsdir)
    for fname in lst:
        ns = fname.split("__")
        if ns[0] == myname:
            return ns[1][0:-4]


def get_filename(myname, role):
    role = int(role)
    lst = os.listdir(pvpsdir)
    if role == 1:
        for fname in lst:
            ns = fname.split("__")
            if ns[0] == myname:
                return os.path.join(pvpsdir, fname)
    else:
        for fname in lst:
            ns = fname.split("__")
            if ns[1][0:-4] == myname:
                return os.path.join(pvpsdir, fname)


def take(tarn1, tarn2, successful, myname, role):
    fname = get_filename(myname, role)

    fi = open(fname)
    info = fi.readline().split(',')
    fi.close()

    fi = open(fname, 'w')
    pl = 1 if role == 1 else 3
    if successful:
        info[pl] = int(info[pl]) + int(info[6])
        if info[pl] >= 100:
            savefile = os.path.join(dirname(dirname(__file__)), os.path.join('prax3', 'pig.txt'))
            savef = open(savefile, 'a')
            savef.write(info[0] + ',' + str(info[1]) + ',' + info[2] + ',' + str(info[3])
                        + ',' + str(int(time.time()) - int(info[4])) + ',' + info[4] + '\n')
            savef.close()
        info[pl] = str(info[pl])
    else:
        info[7] = str(tarn1)
        info[8] = str(tarn2)
    info[6] = '0'
    info[5] = str(-1 * int(info[5]))
    fi.write(",".join(info))
    fi.close()


def add(tarn1, tarn2, myname, role):
    fname = get_filename(myname, role)
    fi = open(fname)
    info = fi.readline().split(',')
    fi.close()
    fi = open(fname, 'w')
    if tarn1 != tarn2:
        score = tarn1 + tarn2
    elif tarn1 != 1:
        score = tarn1 * 4
    else:
        score = 25

    info[6] = str(int(info[6]) + score)
    info[7] = str(tarn1)
    info[8] = str(tarn2)
    fi.write(",".join(info))
    fi.close()


if formdata.has_key("op"):
    operation = formdata["op"].value

    if operation == "getlist":
        f = open(file, 'r')
        data = f.readlines()
        f.close()
        for s in data:
            print s.strip()
    elif operation == "register" and formdata.has_key("name"):
        f = open(file, 'a')
        name = formdata["name"].value
        f.write(name.strip() + '\n')
        f.close()
    elif operation == "check" and formdata.has_key("name"):
        name = formdata["name"].value
        name = name.strip()
        f = open(file, 'r')
        data = f.readlines()
        f.close()
        result = True
        enemyname = ""
        for strng in data:
            if strng.strip() == name:
                result = False
        if result:
            print get_enemy(name)
        else:
            print -1
    elif operation == "startgame" and formdata.has_key("name1") and formdata.has_key("name2") and formdata.has_key("starttime"):
        p1 = formdata["name1"].value
        unreg(p1)
        p2 = formdata["name2"].value
        filename = p1 + "__" + p2 + '.txt'
        variants = [-1, 1]
        turn = variants[random.randint(0, 1)]
        filename = os.path.join(pvpsdir, filename)
        f = open(filename, 'w')
        f.write(str(p1) + ',0,' + str(p2) + ',0,' + str(int(time.time())) + ',' + str(turn) + ',0,0,0')
        f.close()
    elif operation == "roll" and formdata.has_key("name") and formdata.has_key("role"):
        tar1 = random.randint(1, 6)
        tar2 = random.randint(1, 2)

        if tar1 == 1:
            tar1 = random.randint(1, 6)

        if (tar1 == 1) ^ (tar2 == 1):
            take(tar1, tar2, False, str(formdata["name"].value), int(formdata["role"].value))
        else:
            add(tar1, tar2, str(formdata["name"].value), int(formdata["role"].value))

    elif operation == "take" and formdata.has_key("name") and formdata.has_key("role"):
        take(0, 0, True, str(formdata["name"].value), int(formdata["role"].value))
    elif operation == "status" and formdata.has_key("name") and formdata.has_key("role"):
        r = int(formdata["role"].value)
        sname = get_filename(formdata["name"].value, r)
        sf = open(sname)
        info = sf.readline().split(',')
        sf.close()
        pp1 = int(info[1])
        pp2 = int(info[3])
        wr = int(info[5])
        status = [info[6], info[7], info[8]]

        if r == 1:
            if pp1 >= 100:
                status.append("100")
            elif pp2 >= 100:
                status.append("-100")
            elif wr == r:
                status.append("1")
            else:
                status.append("-1")
            status.append(info[1])
            status.append(info[3])
        else:
            if pp1 >= 100:
                status.append("-100")
            elif pp2 >= 100:
                status.append("100")
            elif wr == r:
                status.append("1")
            else:
                status.append("-1")
            status.append(info[3])
            status.append(info[1])

        sstr = ",".join(status)
        print sstr
    elif operation == "delgame" and formdata.has_key("name") and formdata.has_key("role"):
        r = int(formdata["role"].value)
        sname = get_filename(formdata["name"].value, r)
        os.remove(sname)
    elif operation == "unreg" and formdata.has_key("name"):
        unreg(formdata["name"].value)