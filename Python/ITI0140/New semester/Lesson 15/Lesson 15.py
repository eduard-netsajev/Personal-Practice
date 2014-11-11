__author__ = 'Netšajev'

import matplotlib.pyplot as plt

f = open("RV030.txt")
data = f.readlines()
f.close()

dates = []
sunnid = []
surmad = []
iive = []

for strng in data:
    i = strng.split(";")
    dates.append(i[0])
    sunnid.append(i[1])
    surmad.append(i[2])
    iive.append(i[3])

fig, ax1 = plt.subplots()
ax1.plot(dates, surmad, 'r-')
ax1.plot(dates, sunnid, 'g-')
ax1.set_xlabel('year')
# Make the y-axis label and tick labels match the line color.
ax1.set_ylabel('synnid/surmad', color='black')
for tl in ax1.get_yticklabels():
    tl.set_color('black')

ax2 = ax1.twinx()

ax2.plot(dates, iive, 'b-')
ax2.set_ylabel('loomulik iive', color='b')
for tl in ax2.get_yticklabels():
    tl.set_color('b')
plt.show()

plt.show()