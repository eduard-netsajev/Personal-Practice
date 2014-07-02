from datetime import datetime
import random

__author__ = 'Netšajev'


def answer():
    return 42


def greetings(_name):
    hour = datetime.now().hour
    if 5 < hour < 12:
        print("Good morning, %s!" % name)
    elif 12 <= hour <= 17:
        print("Good afternoon, %s!" % _name)
    elif 17 < hour < 22:
        print("Good evening, %s!" % _name)
    else:
        print("Good day, %s!" % _name)


print("Hello World!\n")
my_bool = input("How much is 17+4?") == str(21)

name = input("What is your name?")
print()

now = datetime.now()
if my_bool:
    greetings(name)
print("\nToday is {:0>2}/{:0>2}/{}. Time is {:0>2}:{:0>2}:{:0>2}"
      .format(now.day, now.month, now.year, now.hour, now.minute, now.second))

everything = dir(datetime)  # Sets everything to a list of things from math
print(everything)  # Prints 'em all!


def print_max_and_min(*args):
    print("Maximum in array is " + str(max(*args)))
    print("Minimum in array is " + str(min(*args)))


print_max_and_min(-10, -5, 5, 10, 13, 12.7, 13.1)


def is_number(s):
    try:
        float(s)
        return True
    except ValueError:
        return False


def absolute(number):
    if is_number(number):
        print(abs(float(number)))
    else:
        print("Entered not a valid number!")


absolute(input("Write a number to make it absolute: "))

suitcase = []

suitcase.append("keys")
suitcase.append("watches")
suitcase.append("wallet")
suitcase.append("condoms")

part_suitcase = suitcase[1:3]

suitcase.insert(2, 'money')
list_length = len(suitcase)

print(suitcase.index("wallet"))

print("There are {} items in the suitcase.".format(list_length))
print(suitcase)
suitcase.sort()
suitcase.remove('keys')
print(suitcase)


inventory = {
    'gold': 500,
    'junk': ['pen', 'textbook', 4, 7],
    'pouch': ['flint', 'twine', 'gemstone'],
    'backpack': ['xylophone', 'dagger', 'bedroll', 'bread loaf']
}

inventory.values()
inventory['burlap bag'] = ['apple', 'small ruby', 'three-toed sloth']

inventory['pouch'].sort()
inventory['pouch'].remove('gemstone')

del inventory['junk']

inventory['pocket'] = ['seashell', 'strange berry', 'lint']
inventory['backpack'].sort()
inventory['gold'] += 50

print('Your inventory:')
for index, item in enumerate(inventory):
    print(index + 1, item)

for i in range(1, 6, 3):
    print(i, sep='', end=' ')


def my_function(x):
    for k in range(0, len(x)):
        x[k] *= 2
    return x


print(str(my_function(list(range(3)))))

choice = input('Enjoying the course? (y/n)')

while choice != "y" and choice != "n":
    choice = input("Sorry, I didn't catch that. Enter again: ")


random_number = random.randint(1, 10)

guesses_left = 3

while guesses_left > 0:
    guess = input("Guess a number: ")
    if guess == random_number:
        print("You win!")
        break
    guesses_left -= 1
else:
    print("You lose.")


hobbies = []
for i in range(3):
    hobby = input("Write your hobby: ")
    hobbies.append(hobby)
    hobby = hobby.lower()
    if hobby == "games":
        print("Games are bad for your success!")
        break
else:
    print("Great choice of hobbies!")
print(hobbies)

phrase = "A bird in the hand..."

for char in phrase:
    if char == 'A' or char == 'a':
        print('X', end=' ')
    else:
        print(char, end=' ')
print()

list_a = [3, 9, 17, 15, 19]
list_b = [2, 4, 8, 10, 30, 40, 50, 60, 70, 80, 90]
list_c = [11, 13, 15, 12, 14, 16, 17, 21, 19, 44]

for a, b, c in zip(list_a, list_b, list_c):
    print(max(a, b, c))

#EXTERNAL. TAKES LIST OF NUMBERS AND RETURNS WHOLE STRING


def with_format(nums):
    return ('{}'*len(nums)).format(*nums)

trial = [3, 2, 4, 6, 7]

print(with_format(trial))