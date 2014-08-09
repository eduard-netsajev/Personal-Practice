__author__ = 'Netšajev'


def add(a, b):
    #Make sure the arguments are string type
    a += ""
    b += ""
    out = ""

    len_a = len(a)
    len_b = len(b)

    #if a is longer than b, swap them
    if len_a > len_b:
        temp = a
        a = b
        b = temp
        temp = len_a
        len_a = len_b
        len_b = temp

    a = "0"*(len_b-len_a) + a

    for i in range(1, len_b+1):
        if len(out) == i:
            digit_sum = int(a[-i]) + int(b[-i]) + 1
            out = str(digit_sum) + out[1:]
        else:
            digit_sum = int(a[-i]) + int(b[-i])
            out = str(digit_sum) + out

    return out


highest = 0
#TODO Make a dict of calculated fibonacci and try to look from there
#Compare calculation times after it
fibonacci = {"0": "0",
             "1": "1"}


def get_f(a):
    global highest
    global fibonacci

    if a == "0":
        return "0"
    elif a == "1":
        return "1"
    else:
        result = add(get_f(str(int(a)-1)), str(get_f(str(int(a)-2))))
        if int(a) > highest:
            print("Fibonacci", a, "= ", result)
            highest = int(a)
        return result

print(get_f('123'))