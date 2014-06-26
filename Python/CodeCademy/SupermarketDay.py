__author__ = 'Netšajev'


def compute_bill(food):
    total = 0
    for item in food:
        if stock[item] > 0:
            total += prices[item]
            stock[item] -= 1
        else:
            print("{} is not in the stock!".format(item))
    return total

shopping_list = ["banana", "orange", "apple"]

stock = {
    "banana": 6,
    "apple": 0,
    "orange": 32,
    "pear": 15
}

prices = {
    "banana": 4,
    "apple": 2,
    "orange": 1.5,
    "pear": 3
}

print(shopping_list)
print("The shopping list price is: {}".format(compute_bill(shopping_list)))
print(stock)