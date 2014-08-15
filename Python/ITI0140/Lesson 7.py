__author__ = 'Netšajev'

import math


class Square(object):

    def __init__(self, a):
        self.a = a

    def area(self):
        return self.a ** 2


class Rectangle(object):

    def __init__(self, a, b):
        self.a, self.b = a, b

    def area(self):
        return self.a * self.b


class Circle(object):

    def __init__(self, r):
        self.r = r

    def area(self):
        return math.pi * pow(self.r, 2)


class Triangle(object):

    def __init__(self, a, b, c=None):
        self.a, self.b, self.c = a, b, c

    def area(self):
        if self.c is None:
            return self.a * self.b / 2.0
        else:
            #Heron's formula
            s = (self.a + self.b + self.c)/2.0
            return math.sqrt(s*(s-self.a)*(s-self.b)*(s-self.c))


class Ellipse(object):

    def __init__(self, a, b):
        self.a, self.b = a, b

    def area(self):
        return math.pi * self.a * self.b


def surface_area(shapes):
    """
    funktsioon kujundite pindalade summa
    leidmiseks. Antud funktsioonile antakse
    argumendiks järjend (list) kujunditega, milleks
    võivad olla kolmnurk, ruut, ristkülik, ring ja ellips.
    Funktsioon peab tagastama ujukomaarvuna
    kõikide kujundite kogupindala.
    """
    result = 0.0
    for shape in shapes:
        result += shape.area()
    return result


def main():
    shapes = [Square(3.0), Rectangle(2, 4)]  # ...
    sa = surface_area(shapes)
    print(sa)
    # result is 17.0

if __name__ == '__main__':
    main()