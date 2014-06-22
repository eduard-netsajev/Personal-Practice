from datetime import datetime

__author__ = 'Netšajev'


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


print("Hello World!")
my_bool = input("How much is 17+4?") == str(21)

name = input("What is your name?")
print()

now = datetime.now()
if my_bool:
    greetings(name)
print("\nToday is {:0>2}/{:0>2}/{}. Time is {:0>2}:{:0>2}:{:0>2}"
      .format(now.day, now.month, now.year, now.hour, now.minute, now.second))