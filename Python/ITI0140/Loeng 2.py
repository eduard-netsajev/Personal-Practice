__author__ = 'Netšajev'


def tags_check(word):
    stack = []
    tags_open = ['(', '{', '[']
    tags_close = [')', '}', ']']
    for i in range(len(word)):
        if word[i] in tags_open:
            stack.append(word[i])
        elif word[i] in tags_close:
            if len(stack) > 0:
                if tags_close.index(word[i]) == tags_open.index(stack[-1]):
                    stack.pop()
                else:
                    return False
            else:
                return False
    return len(stack) == 0

while True:
    input_str = input("Sisend: ")
    result = tags_check(input_str)
    if result:
        print("Jah")
    else:
        print("Ei")