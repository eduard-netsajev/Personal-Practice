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
        num = num ** 2
print(b)

for i in range(len(b)):
        b[i] = b[i] ** 2
print(b)

a = "Hi"
b = "There"
c = "!"
print(a + b)
print(a + b + c)
print(3 * a)
print(a * 3)
print((a * 2) + (b * 2))

months = "JanFebMarAprMayJunJulAugSepOctNovDec"

n = int(input("Enter a month number: "))
print(months[(n-1)*3:n*3])

plain_text = "This is a test. ABC abc"
encrypted_text = ""
for c in plain_text:
    x = ord(c)
    x = x + 1
    c2 = chr(x)
    encrypted_text = encrypted_text + c2
print(encrypted_text)

plain_text = ""
for c in encrypted_text:
    x = ord(c)
    x = x - 1
    c2 = chr(x)
    plain_text = plain_text + c2
print(plain_text)