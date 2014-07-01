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

evens_to_50 = [i for i in range(51) if i % 2 == 0]
print(evens_to_50)

doubles_by_3 = [x*2 for x in range(1, 20) if (x*2) % 3 == 0]
print(doubles_by_3)


print(evens_to_50[3:10:2])
print(evens_to_50[::-3])

my_list = range(16)
print(list(filter(lambda x: x % 3 == 0, my_list)))

squares = [x**2 for x in range(1, 11)]
print(list(filter(lambda x: 30 <= x <= 70, squares)))

garbled = "IXXX aXXmX aXXXnXoXXXXXtXhXeXXXXrX sXXXXeXcXXXrXeXt mXXeXsXXXsXaXXXXXXgXeX!XX"
message = (list(filter(lambda x: x != 'X', garbled)))
message = "".join(message)
print(message)
