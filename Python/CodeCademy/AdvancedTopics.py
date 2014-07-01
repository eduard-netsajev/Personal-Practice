__author__ = 'Netšajev'

my_dict = {
    "Name": "Guido",
    "Age": 21,
    "Married": False
}
print(my_dict.items())
print(my_dict.keys())
print(my_dict.values())

for key in my_dict:
    print(key, my_dict[key])

