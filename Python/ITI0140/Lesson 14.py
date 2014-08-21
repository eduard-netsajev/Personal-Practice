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

x = sympy.symbols("x")
a = 0
b = 20
f1 = x ** 3 + 4 * x ** 2 + 100
f2 = (6 + 1) * sympy.sin(x) + sympy.cos(x)
f = f1 - f2

result = sympy.integrate(f, (x, a, b))
print(result, "<>", sympy.N(result))