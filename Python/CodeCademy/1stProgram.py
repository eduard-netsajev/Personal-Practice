from datetime import datetime

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

zoo_animals = {'Unicorn': 'Cotton Candy House',
               'Sloth': 'Rainforest Exhibit',
               'Bengal Tiger': 'Jungle House',
               'Atlantic Puffin': 'Arctic Exhibit',
               'Rockhopper Penguin': 'Arctic Exhibit'}

del zoo_animals['Sloth']

zoo_animals['Cat'] = 'Frosja'

print(zoo_animals)


