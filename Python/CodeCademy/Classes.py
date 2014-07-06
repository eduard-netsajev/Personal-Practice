__author__ = 'Netšajev'


class Fruit(object):
    """A class that makes various tasty fruits."""

    def __init__(self, name, color, flavor, poisonous):
        self.name = name
        self.color = color
        self.flavor = flavor
        self.poisonous = poisonous

    def description(self):
        print("I'm a %s %s and I taste %s." % (self.color, self.name, self.flavor))

    def is_edible(self):
        if not self.poisonous:
            print("Yep! I'm edible.")
        else:
            print("Don't eat me! I am super poisonous.")


lemon = Fruit("lemon", "yellow", "sour", False)

lemon.description()
lemon.is_edible()


class Animal(object):
    """Makes cute animals."""
    is_alive = True
    # For initializing our instance objects
    def __init__(self, name, age, is_hungry=True):
        self.name = name
        self.age = age
        self.is_hungry = is_hungry

    def description(self):
        print('\n', self.name, str(self.age), str(self.is_hungry), str(self.is_alive))


zebra = Animal("Jeffrey", 2, True)
giraffe = Animal("Bruce", 1, False)
panda = Animal("Chad", 7)
cat = Animal("Boots", 3, False)
hippo = Animal('Krook', 7)

hippo.description()
hippo.is_alive = False
hippo.description()
cat.description()


class ShoppingCart(object):
    """Creates shopping cart objects
    for users of our fine website."""
    items_in_cart = {}

    def __init__(self, customer_name):
        self.customer_name = customer_name

    def add_item(self, product, price):
        """Add product to the cart."""
        if not product in self.items_in_cart:
            self.items_in_cart[product] = price
            print(product + " added.")
        else:
            print(product + " is already in the cart.")

    def remove_item(self, product):
        """Remove product from the cart."""
        if product in self.items_in_cart:
            del self.items_in_cart[product]
            print(product + " removed.")
        else:
            print(product + " is not in the cart.")


my_cart = ShoppingCart('Eduard')
my_cart.add_item('Banana', 3)


class MyString(str):
    def params(self):
        print(self, 'is', len(self), 'characters long')


ankj = MyString('Hello Word')
ankj.params()

nstr = MyString(ankj.lower())
nstr.params()


class Shape(object):
    """Makes shapes!"""

    def __init__(self, number_of_sides):
        self.number_of_sides = number_of_sides


class Triangle(Shape):
    def __init__(self, side1, side2, side3):
        self.number_of_sides = 3
        self.side1 = side1
        self.side2 = side2
        self.side3 = side3


kolmik = Triangle(3, 3, 4)

print(kolmik.number_of_sides)


class Employee(object):
    """Models real-life employees!"""
    def __init__(self, employee_name):
        self.employee_name = employee_name

    def calculate_wage(self, hours):
        self.hours = hours
        return hours * 20.00


class PartTimeEmployee(Employee):
    def calculate_wage(self, hours):
        self.hours = hours
        return hours * 12.00
    def full_time_wage(self, hours):
        return super(PartTimeEmployee, self).calculate_wage(hours)

milton = PartTimeEmployee('Milton')
print(milton.calculate_wage(10))
print(milton.full_time_wage(10))

class Triangle2(object):
    def __init__(self, angle1, angle2, angle3):
        self.angle1 = angle1
        self.angle2 = angle2
        self.angle3 = angle3
    number_of_sides = 3
    def check_angles(self):
        return (self.angle1 + self.angle2 + self.angle3) == 180

my_triangle = Triangle2(90, 30, 60)
print(my_triangle.number_of_sides)
print(my_triangle.check_angles())


class Car(object):
    condition = "new"
    def __init__(self, model, color, mpg):
        self.model = model
        self.color = color
        self.mpg   = mpg

    def display_car(self):
        return "This is a " + self.color + ' ' + \
        self.model + " with " + str(self.mpg) + " MPG."

    def drive_car(self):
        self.condition = 'used'

class ElectricCar(Car):
    def __init__(self, model, color, mpg, battery_type):
        self.model = model
        self.color = color
        self.mpg   = mpg
        self.battery_type = battery_type

my_car = ElectricCar("DeLorean", "silver", 88, 'molten salt')
print(my_car.condition)
my_car.drive_car()
print(my_car.condition)