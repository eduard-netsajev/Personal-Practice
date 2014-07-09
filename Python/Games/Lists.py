__author__ = 'Netšajev'

a = (2, 3, 4, 5)
b = [2, 3, 4, 5]

print(type(a))
print(type(b))

try:
    a[0] = 6
except TypeError:
    print("{} element can't be changed".format(type(a)))

try:
    b[0] = 6
except TypeError:
    print("{} element can't be changed".format(type(b)))

print(a, b)

for num in b:
        num = 7
print(b)

for i in range(len(b)):
        b[i] = 7
print(b)