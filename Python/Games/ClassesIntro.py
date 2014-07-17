__author__ = 'Netšajev'

class Cat(object):

    name = ''
    color = ''
    weight = 0

    def meow(self):
        print("Meow!")


my_cat = Cat()
my_cat.name = 'Beauty'
my_cat.color = 'Black'
my_cat.weight = 7
my_cat.meow()


class Monster(object):

    name = ''
    health = 100

    def decrease_health(self, amount):
        self.health -= amount
        if self.health < 0:
            print(self.name, "is dead.")

rat = Monster()
rat.name = "Rattatoo"
rat.health = 3

rat.decrease_health(1)
rat.decrease_health(3)


class Dog():
    name = "Rover"

    # Constructor
    # Called when creating an object of this type
    def __init__(self, name):
        # This will print "Rover"
        print(self.name)
        # This will print "Spot"
        print(name)

    def say_name(self):
        print(self.name)

# This creates the dog
myDog = Dog("Spot")
myDog.say_name()
myDog.name = 'Kvazik'
myDog.say_name()


class Person():
    name = ""

    def __init__(self):
        print("Person created")


class Employee(Person):
    job_title = ""

    def __init__(self):
        Person.__init__(self)
        print("Employee created")


class Customer(Person):
    email = ""

    def __init__(self):
        print("Customer created")

janeEmployee = Employee()
bobCustomer = Customer()