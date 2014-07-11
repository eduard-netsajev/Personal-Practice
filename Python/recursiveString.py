__author__ = 'Netšajev'


def reversestring(string):
    #recursive function that returns reverted string

    if len(string) == 0:
        return string
    return reversestring(string[1:len(string)]) + string[0]

def reverser():
    string = input("Type a sentence: ")
    print(reversestring(string))
    if string != "exit":
        reverser()

reverser()