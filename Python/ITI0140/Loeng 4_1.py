__author__ = 'Netšajev'

a = "12345"
b = "234"


def reverse_string(s):
    k = len(s)
    for i in range(k):
        s = s[1:k-i] + s[0:1] + s[k-i:k]
    return s


def string_to_list(s):
    list = []
    for char in s:
        list.append(int(char))
    return list


def sum_lists(al, bl):
    """
    both parameters al and bl are represented in reversed order:
    12345 is passed as [5, 4, 3, 2, 1]
    So, the example is 12345 + 999999
    The result is also in the reversed order.
    In the case of the example: 1012344: [4, 4, 3, 2, 1, 0, 1]
    """
    if len(al) < len(bl):
        return sum_lists(bl, al)

    print(al)
    print(bl)

    result = []
    carry = 0

    for i in range(len(bl)):
        t = al[i] + bl[i] + carry
        if t >= 10:
            t = t - 10
            carry = 1
        else:
            carry = 0

        result.append(t)

    for i in range(len(bl), len(al)):
        t = al[i] + carry
        if t >= 10:
            t = t - 10
            carry = 1
        else:
            carry = 0

        result.append(t)

    if carry > 0:
        result.append(carry)

    return result

at = reverse_string(a)
al = string_to_list(at)

bt = reverse_string(b)
bl = string_to_list(bt)

print(al)
print(bl)

cl = sum_lists(al, [9, 9, 9, 9, 9, 9])

# cl ümber pöörata
# teha string

print(cl)

print(reverse_string(a))