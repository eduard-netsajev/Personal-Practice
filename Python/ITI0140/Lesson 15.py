__author__ = 'Netšajev'


import numpy as np
import matplotlib.pyplot as plt

ax = plt.subplot(111)

x = np.arange(-1.0, 5.0, 0.01)
f2 = (6 + 1) * np.sin(x) + np.cos(x)
line = plt.plot(x, f2, lw=2)

f1 = x**3 + 4*x**2 + 100
line2 = plt.plot(x, f1, lw=2)

plt.axvline(x=0, ymin=0, ymax=1)
plt.axvline(x=4, ymin=0, ymax=1)

plt.ylim(-10, 240)
plt.show()