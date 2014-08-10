__author__ = 'Netšajev'


def celsius_to_f(grad):
    return grad*9/5.0 + 32

for kraad in range(-30, 101):
    print("{}°C".format(kraad), end='')
    if kraad % 5 == 0:
        print("*", end='')
    if kraad % 10 == 0:
        print("*", end='')
    far = celsius_to_f(kraad)*10//1/10
    print(" = {}F".format(far))