__author__ = 'Netšajev'

import sympy

"""
Kirjutada Pythonis Sympy moodulile tuginev programm,
mis arvutab kujundi pindala, mis on piiratud järgmiste
funkstioonidega:
• x**3 + 4x**2 + 100,
• x=0,
• (a + 1) sin(x) + cos(x),
• x=20,
kus a on matrikli numbri viimane number.
"""
sympy.init_printing()
x = sympy.symbols("x")
a = 0
b = 20
f1 = x ** 3 + 4 * x ** 2 + 100
f2 = (4 + 1) * sympy.sin(x) + sympy.cos(x)
f = f1 - f2

z = sympy.symbols('z')
fun = sympy.integrate(f, (x, 0, z))

print(sympy.pretty(fun))
print()
print()
print("Result when z = {}:".format(b), sympy.N(sympy.integrate(f, (x, 0, b))))