__author__ = 'Netšajev'

import time

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


fibonacci = {"0": "0",
             "1": "1"}
fibonacci_digits = {0: "0",
                    1: "1"}


def get_f(a):
    """
    This function hits recursion limit at a > 997
    """
    global fibonacci

    if a in fibonacci:
        return fibonacci[a]
    else:
        result = add(get_f(str(int(a)-1)), str(get_f(str(int(a)-2))))
        fibonacci[a] = result
        return result


def get_fibonacci(a):
    """
    This one works fine with whichever given argument a
    Took less than 30 sec to calculate with argument 13245
    a  time
    777     0.09
    7777    10
    13245   <30
    25000   111
    """
    a = int(a)
    global fibonacci_digits

    if a not in fibonacci_digits:
        for i in range(2, a+1):
            fibonacci_digits[i] = add(fibonacci_digits[i-1], fibonacci_digits[i-2])
    print("Fibonacci", a, " =", fibonacci_digits[a])
    return fibonacci_digits[a]

fib_1 = '777'

t0 = time.time()

get_fibonacci(fib_1)
t1 = time.time()
exec_time = (t1-t0)*1000//1/1000


print("It took {} seconds to calculate the Fibonacci number {}".format(exec_time, fib_1))