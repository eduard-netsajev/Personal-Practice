__author__ = 'Netšajev'

for i in range(10):
    print("*", end=' ')
print()
for i in range(5):
    print("*", end=' ')
print()
for i in range(12):
    print("*", end=' ')

print('\n')

for i in range(10):
    for j in range(10):
        print("*", end=' ')
    print()

print('\n')

for i in range(10):
    for j in range(5):
        print("*", end=' ')
    print()

print('\n')

for i in range(10):
    for j in range(10):
        print(j, end=' ')
    print()

print('\n')

for i in range(10):
    for j in range(10):
        print(i, end=' ')
    print()

print('\n')

for i in range(10):
    for j in range(i+1):
        print(j, end=' ')
    print()

print('\n')

for i in range(10):
    for j in range(i):
        print(" ", end=' ')
    for j in range(10-i):
        print(j, end=' ')
    print()

print('\n')

for i in range(1, 10):
    for j in range(1, 10):
        if i*j < 10:
            print(" ", end='')
        print(i*j, end=' ')
    print()

print('\n')

for i in range(1, 10):
    for j in range(10-i):
        print(" ", end=' ')
    for j in range(i):
        print(j+1, end=' ')
    for j in range(i-1, 0, -1):
        print(j, end=' ')
    print()

print('\n')

for i in range(1, 10):
    for j in range(10-i):
        print(" ", end=' ')
    for j in range(i):
        print(j+1, end=' ')
    for j in range(i-1, 0, -1):
        print(j, end=' ')
    print()
for i in range(9):
    for j in range(i+2):
        print(" ", end=' ')
    for j in range(1, 9-i):
        print(j, end=' ')
    for j in range(7-i, 0, -1):
        print(j, end=' ')
    print()

print('\n')

k = 10
for i in range(10):
        for j in range(i):
            print(k, end=' ')
            k += 1
        print()

print('\n')

n = int(input("Input box size -> "))

print('\n')

for i in range(n):
    if i == 0 or i+1 == n:
        for j in range(n*2):
            print("o", end='')
        print()
    else:
        print('o', end='')
        for j in range((n-1)*2):
            print(" ", end='')
        print('o')

print('\n')

#Challenge
n = int(input("Input box size (1-5) -> "))

print('\n')

for i in range(1, n+1):
        for j in range(i*2-1, n*2, 2):
            print(j, end=' ')
        for j in range((i-1)*2):
            print(" ", end=' ')
        for j in range(n*2-1, (i-1)*2, -2):
            print(j, end=' ')
        print()
for i in range(n, 0, -1):
        for j in range(i*2-1, n*2, 2):
            print(j, end=' ')
        for j in range((i-1)*2):
            print(" ", end=' ')
        for j in range(n*2-1, (i-1)*2, -2):
            print(j, end=' ')
        print()
input()

